package uk.co.thefishlive.http.meteor;

import com.google.gson.*;
import uk.co.thefishlive.http.HttpClient;
import uk.co.thefishlive.http.HttpHeader;
import uk.co.thefishlive.http.HttpRequest;
import uk.co.thefishlive.http.HttpResponse;

import com.google.common.base.Preconditions;
import uk.co.thefishlive.http.exception.HttpException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MeteorHttpClient implements HttpClient {

    private static final Gson GSON = new GsonBuilder().create();
    private final Proxy proxy;

    public MeteorHttpClient(Proxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public HttpResponse sendRequest(URL url, HttpRequest request) throws IOException {
        Preconditions.checkNotNull(url, "URL cannot be null");
        Preconditions.checkNotNull(request, "Request cannot be null");

        HttpURLConnection connection = null;
        String responseBuffer = "";

        try {
            connection = (HttpURLConnection) url.openConnection(proxy);

            connection.setRequestMethod(request.getRequestType().name());
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            for (HttpHeader header : request.getHeaders()) {
                connection.setRequestProperty(header.getName(), header.getValue());
            }

            try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
                GSON.toJson(request.getRequestBody(), writer);
            }

            try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
                JsonParser parser = new JsonParser();
                Scanner s = new Scanner(reader).useDelimiter("\\A");
                responseBuffer = s.hasNext() ? s.next() : "";
                JsonObject payload = parser.parse(responseBuffer).getAsJsonObject();

                List<HttpHeader> headers = new ArrayList<>();

                for (Map.Entry<String,List<String>> entry : connection.getHeaderFields().entrySet()) {
                    for (String value : entry.getValue()) {
                        headers.add(new BasicHttpHeader(entry.getKey(), value));
                    }
                }

                return new MeteorHttpResponse(payload.get("success").getAsBoolean(), connection.getResponseCode(), payload.getAsJsonObject("payload"), headers);
            }

        } catch (JsonSyntaxException ex) {
            System.err.println(responseBuffer);
            throw ex;
        } catch (IOException ex) {
            if (connection != null) {
                try (InputStreamReader reader = new InputStreamReader(connection.getErrorStream())) {
                    JsonParser parser = new JsonParser();
                    JsonObject payload = parser.parse(reader).getAsJsonObject();
                    throw new HttpException(payload.getAsJsonObject("payload").get("error").getAsString());
                }
            }

            throw ex;
        }
    }
}
