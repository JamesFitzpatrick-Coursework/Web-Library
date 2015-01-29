package uk.co.thefishlive.http.meteor;

import com.google.common.base.Throwables;
import com.google.gson.*;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.util.EntityUtils;
import uk.co.thefishlive.http.*;

import com.google.common.base.Preconditions;
import uk.co.thefishlive.http.HttpClient;
import uk.co.thefishlive.http.HttpRequest;
import uk.co.thefishlive.http.HttpResponse;
import uk.co.thefishlive.http.exception.HttpException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import uk.co.thefishlive.meteor.utils.ProxyUtils;

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

        org.apache.http.client.HttpClient client = HttpClients.createDefault();

        HttpUriRequest httpRequest = null;
        String responseBuffer = "";

        try {
            httpRequest = buildRequest(request.getRequestType(), url.toExternalForm());

            for (HttpHeader header : request.getHeaders()) {
                if (header == null) {
                    continue;
                }

                httpRequest.setHeader(header.getName(), header.getValue());
            }

            if (request.getRequestBody() != null) {
                StringEntity json = new StringEntity(request.getRequestBody().toString());

                if (httpRequest instanceof HttpEntityEnclosingRequest) {
                    ((HttpEntityEnclosingRequest) httpRequest).setEntity(json);
                }
            }

            org.apache.http.HttpResponse response = client.execute(httpRequest);
            JsonParser parser = new JsonParser();
            responseBuffer = EntityUtils.toString(response.getEntity());
            System.out.println(responseBuffer);
            JsonObject payload = parser.parse(responseBuffer).getAsJsonObject();

            List<HttpHeader> headers = new ArrayList<>();

            HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator());

            while (it.hasNext()) {
                HeaderElement elem = it.nextElement();
                NameValuePair[] params = elem.getParameters();

                for (NameValuePair param : params) {
                    headers.add(new BasicHttpHeader(param.getName(), param.getValue()));
                }
            }

            return new MeteorHttpResponse(payload.get("success").getAsBoolean(), response.getStatusLine().getStatusCode(), payload.getAsJsonObject("payload"), headers);
        } catch (JsonSyntaxException ex) {
            System.err.println(responseBuffer);
            throw ex;
        } catch (IOException ex) {
            /*try (InputStreamReader reader = new InputStreamReader(connection.getErrorStream())) {
                JsonParser parser = new JsonParser();
                JsonObject payload = parser.parse(reader).getAsJsonObject();
                System.err.println(payload);
                throw new HttpException(payload.getAsJsonObject("payload").get("error").getAsString());
            }*/

            throw ex;
        }
    }

    public HttpUriRequest buildRequest(RequestType type, String url) {
        switch (type) {
            case POST:
                return new HttpPost(url);
            case PATCH:
                return new HttpPatch(url);
            case GET:
                return new HttpGet(url);
            case DELETE:
                return new HttpDelete(url);
        }

        return null;
    }
}
