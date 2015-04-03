package uk.co.thefishlive.http;

import com.google.gson.JsonObject;

import java.util.List;

/**
 * Represents a HTTP request sent by a {@link uk.co.thefishlive.http.HttpClient}.
 *
 * @author James
 * @see uk.co.thefishlive.http.HttpClient
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

    /**
     * Get all the headers attached to this request.
     *
     * @return a immutable list of the headers attached to this request
     */
    public List<HttpHeader> getHeaders();

}
