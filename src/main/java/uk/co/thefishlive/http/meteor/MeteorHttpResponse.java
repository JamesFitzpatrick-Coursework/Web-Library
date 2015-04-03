package uk.co.thefishlive.http.meteor;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;

import uk.co.thefishlive.http.HttpHeader;
import uk.co.thefishlive.http.HttpResponse;

import java.util.List;

public class MeteorHttpResponse implements HttpResponse {

    private boolean success;
    private int responseCode;
    private JsonObject payload;
    private List<HttpHeader> headers;

    public MeteorHttpResponse(boolean success, int responseCode, JsonObject payload, List<HttpHeader> headers) {
        this.success = success;
        this.responseCode = responseCode;
        this.payload = payload;
        this.headers = headers;
    }

    @Override
    public boolean isSuccessful() {
        return success;
    }

    @Override
    public int getResponseCode() {
        return responseCode;
    }

    @Override
    public JsonObject getResponseBody() {
        return this.payload;
    }

    @Override
    public List<HttpHeader> getHeaders() {
        return ImmutableList.copyOf(this.headers);
    }
}
