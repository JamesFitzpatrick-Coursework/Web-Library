package uk.co.thefishlive.http;

/**
 * Represents a header to be sent with a
 * {@link uk.co.thefishlive.http.HttpRequest}.
 */
public interface HttpHeader {

    /**
     * Get this headers name.
     *
     * @return this headers name
     */
    public String getName();

    /**
     * Get this headers value.
     *
     * @return this headers value
     */
    public String getValue();

}
