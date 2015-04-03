package uk.co.thefishlive.http.meteor;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import uk.co.thefishlive.http.HttpClient;
import uk.co.thefishlive.http.HttpHeader;
import uk.co.thefishlive.http.HttpRequest;
import uk.co.thefishlive.http.HttpResponse;
import uk.co.thefishlive.http.exception.HttpException;
import uk.co.thefishlive.meteor.utils.ProxyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MeteorHttpClient implements HttpClient {

    private static final Gson GSON = new GsonBuilder().create();
    private final Proxy proxy;
    private static HttpClient instance;

    public MeteorHttpClient(Proxy proxy) {
        this.proxy = proxy;
    }

    public static HttpClient getInstance() {
        if (instance == null) {
            try {
                instance = new MeteorHttpClient(ProxyUtils.getSystemProxy());
            } catch (URISyntaxException e) {
                Throwables.propagate(e);
            }
        }
        return instance;
    }

    @Override
    public HttpResponse sendRequest(URL url, HttpRequest request) throws IOException {
        Preconditions.checkNotNull(url, "URL cannot be null");
        Preconditions.checkNotNull(request, "Request cannot be null");

        HttpURLConnection connection = null;
        String responseBuffer = "";

        try {
            // Setup the connection, proxied if necessary
            connection = (HttpURLConnection) url.openConnection(proxy);

            connection.setRequestMethod(request.getRequestType().name());
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            // Write headers to request
            for (HttpHeader header : request.getHeaders()) {
                connection.setRequestProperty(header.getName(), header.getValue());
            }

            // If exists, write the request body, this is only needed for POST requests
            if (request.getRequestBody() != null) {
                connection.setDoInput(true);
                try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
                    GSON.toJson(request.getRequestBody(), writer);
                }
            }

            // Perform the request
            try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
                // Parse the result of the request
                JsonParser parser = new JsonParser();
                Scanner s = new Scanner(reader).useDelimiter("\\A");
                responseBuffer = s.hasNext() ? s.next() : "";
                JsonObject payload = parser.parse(responseBuffer).getAsJsonObject();

                boolean success = payload.get("success").getAsBoolean();

                // If the call is not successful and not picked up yet, throw an exception
                /* This doesn't work, need a better way to do this
                if (!success) {
                    throw new HttpException(payload.getAsJsonObject("payload").get("error").getAsString());
                }*/

                // Collect the headers from this request
                List<HttpHeader> headers = new ArrayList<>();

                for (Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {
                    headers.addAll(entry.getValue().stream()
                                       .map(value -> new BasicHttpHeader(entry.getKey(), value))
                                       .collect(Collectors.toList()));
                }

                // Return the response of the request
                return new MeteorHttpResponse(success, connection.getResponseCode(), payload.getAsJsonObject("payload"), headers);
            }

        } catch (JsonSyntaxException ex) {
            System.err.println(responseBuffer);
            throw ex;
        } catch (IOException ex) {
            if (connection != null && connection.getErrorStream() != null) {
                try (InputStreamReader reader = new InputStreamReader(connection.getErrorStream())) {
                    JsonParser parser = new JsonParser();
                    JsonObject payload = parser.parse(reader).getAsJsonObject();
                    System.err.println(payload);
                    throw new HttpException(payload.getAsJsonObject("payload").get("error").getAsString());
                }
            }

            throw ex;
        }
    }
}
