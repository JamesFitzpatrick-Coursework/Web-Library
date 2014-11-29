package uk.co.thefishlive.http.meteor;

import uk.co.thefishlive.http.HttpClient;
import uk.co.thefishlive.http.HttpRequest;
import uk.co.thefishlive.http.HttpResponse;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by James on 20/11/2014.
 */
public class MeteorHttpClient implements HttpClient {

    private Gson GSON = new GsonBuilder().create();

    @Override
    public HttpResponse sendRequest(URL url, HttpRequest request) throws IOException {
        Preconditions.checkNotNull(url, "URL cannot be null");
        Preconditions.checkNotNull(request, "Request cannot be null");

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // TODO proxy settings?

            connection.setRequestMethod(request.getRequestType().name());
            connection.setDoInput(true);
            connection.setDoOutput(true);

            try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
                GSON.toJson(request.getRequestBody(), writer);
            }

            try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
                JsonParser parser = new JsonParser();
                JsonObject payload = parser.parse(reader).getAsJsonObject().getAsJsonObject("payload");
                return new MeteorHttpResponse(connection.getResponseCode(), payload);
            }

        } catch (IOException e) {
            throw e;
        }
    }
}
