package uk.co.thefishlive.http.meteor;

import com.google.common.collect.ImmutableList;
import uk.co.thefishlive.http.HttpHeader;
import uk.co.thefishlive.http.HttpRequest;
import uk.co.thefishlive.http.RequestType;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class MeteorHttpRequest implements HttpRequest {

    private List<HttpHeader> headers;
    private RequestType type;
    private JsonObject payload;

    public MeteorHttpRequest(RequestType type, JsonObject payload) {
        this(type, payload, new ArrayList<HttpHeader>());
    }

    public MeteorHttpRequest(RequestType type, JsonObject payload, List<HttpHeader> headers) {
        this.type = type;
        this.payload = payload;
        this.headers = headers;
    }

    @Override
    public RequestType getRequestType() {
        return this.type;
    }

    @Override
    public JsonObject getRequestBody() {
        return this.payload;
    }

    @Override
    public List<HttpHeader> getHeaders() {
        return ImmutableList.copyOf(headers);
    }

}
