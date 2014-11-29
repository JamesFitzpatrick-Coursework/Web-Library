package uk.co.thefishlive.http.meteor;

import uk.co.thefishlive.http.HttpRequest;
import uk.co.thefishlive.http.RequestType;

import com.google.gson.JsonObject;

public class MeteorHttpRequest implements HttpRequest {

    private RequestType type;
    private JsonObject payload;

    public MeteorHttpRequest(RequestType type, JsonObject payload) {
        this.type = type;
        this.payload = payload;
    }

    @Override
    public RequestType getRequestType() {
        return this.type;
    }

    @Override
    public JsonObject getRequestBody() {
        return this.payload;
    }

}
