package uk.co.thefishlive.http.exception;

import java.io.IOException;

/**
 * Exception to signal that something has gone wrong with a http request.
 */
public class HttpException extends IOException {

    public HttpException(String message) {
        super(message);
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpException(Throwable cause) {
        super(cause);
    }
}
