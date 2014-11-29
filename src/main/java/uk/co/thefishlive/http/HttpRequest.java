package uk.co.thefishlive.http;

import com.google.gson.JsonObject;

/**
 * Represents a HTTP request sent by a {@link uk.co.thefishlive.http.HttpClient}.
 *
 * @see uk.co.thefishlive.http.HttpClient
 * @author James
 */
public interface HttpRequest {

    /**
     * Get the HTTP request type for this request.
     *
     * @return the HTTP request type for this request
     */
    public RequestType getRequestType();

    /**
     * Get the json encoded body for this request.
     *
     * @return the body for this request
     */
    public JsonObject getRequestBody();

}
