package uk.co.thefishlive.http.meteor;

import uk.co.thefishlive.http.HttpResponse;

import com.google.gson.JsonObject;

/**
 * Created by James on 20/11/2014.
 */
public class MeteorHttpResponse implements HttpResponse {

    private boolean success;
    private int responseCode;
    private JsonObject payload;

    public MeteorHttpResponse(boolean success, int responseCode, JsonObject payload) {
        this.success = success;
        this.responseCode = responseCode;
        this.payload = payload;
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
}
