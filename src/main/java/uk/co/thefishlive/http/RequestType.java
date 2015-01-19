package uk.co.thefishlive.http;

/**
 * Represents a HTTP request type
 */
public enum RequestType {

    /**
     * Sends a POST HTTP request to send specified information to the specified
     * URI.
     * <p />
     * POST requests update information on the remote server.
     */
    POST,

    /**
     * Sends a GET HTTP request to retrieve the information from the specified
     * URI.
     * <p />
     * GET requests should not edit any information on the site and should be
     * cachible by the clients.
     */
    GET, DELETE;

}
