package uk.co.thefishlive.http;

import com.google.gson.JsonObject;

import java.util.List;

/**
 * A response to a {@link uk.co.thefishlive.http.HttpRequest} sent by the
 * {@link uk.co.thefishlive.http.HttpClient}.
 *
 * @see uk.co.thefishlive.http.HttpClient
 * @author James
 */
public interface HttpResponse {

    /**
     * If this request was successful.
     *
     * @return true if it was, false otherwise
     */
    public boolean isSuccessful();

    /**
     * Get the HTTP response code for this request.
     *
     * @return the HTTP response code for the request
     */
    public int getResponseCode();

    /**
     * Get the response payload for this request.
     *
     * @return the response body for this request
     */
    public JsonObject getResponseBody();

    /**
     * Get the headers sent with within this response.
     *
     * @return a list of the headers sent with this response
     */
    public List<HttpHeader> getHeaders();
}
