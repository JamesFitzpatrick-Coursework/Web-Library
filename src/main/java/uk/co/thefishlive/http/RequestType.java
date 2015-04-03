package uk.co.thefishlive.http;

/**
 * Represents a HTTP request type
 */
public enum RequestType {

    /**
     * Sends a POST HTTP request to send specified information to the specified URI.
     */
    POST,

    /**
     * Sends a GET HTTP request to retrieve the information from the specified URI.
     */
    GET,

    /**
     * Sends a POST HTTP request to delete information from the specified URI.
     */
    DELETE;

}
