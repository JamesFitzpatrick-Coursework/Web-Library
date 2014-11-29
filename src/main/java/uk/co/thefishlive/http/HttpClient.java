package uk.co.thefishlive.http;

import java.io.IOException;
import java.net.URL;

/**
 * A client to send HTTP request to a web server.
 *
 * @author James
 */
public interface HttpClient {

    /**
     * Send a http request to a web site.
     *
     * @param request the request to send
     * @return the http response from the web server
     */
    public HttpResponse sendRequest(URL url, HttpRequest request) throws IOException;

}
