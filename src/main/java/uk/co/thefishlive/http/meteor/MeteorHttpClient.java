package uk.co.thefishlive.http.meteor;

import uk.co.thefishlive.http.HttpClient;
import uk.co.thefishlive.http.HttpRequest;
import uk.co.thefishlive.http.HttpResponse;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import uk.co.thefishlive.http.exception.HttpException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;

/**
 * Created by James on 20/11/2014.
 */
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

        try {
            connection = (HttpURLConnection) url.openConnection(proxy);

            connection.setRequestMethod(request.getRequestType().name());
            connection.setDoInput(true);
            connection.setDoOutput(true);

            try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
                GSON.toJson(request.getRequestBody(), writer);
            }

            try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
                JsonParser parser = new JsonParser();
                JsonObject payload = parser.parse(reader).getAsJsonObject();
                return new MeteorHttpResponse(payload.get("success").getAsBoolean(), connection.getResponseCode(), payload.getAsJsonObject("payload"));
            }

        } catch (IOException e) {
            if (connection != null) {
                try (InputStreamReader reader = new InputStreamReader(connection.getErrorStream())) {
                    JsonParser parser = new JsonParser();
                    JsonObject payload = parser.parse(reader).getAsJsonObject();
                    throw new HttpException(payload.getAsJsonObject("payload").get("error").getAsString());
                }
            }

            throw e;
        }
    }
}
