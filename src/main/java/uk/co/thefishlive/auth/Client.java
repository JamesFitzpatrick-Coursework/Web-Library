package uk.co.thefishlive.auth;

import uk.co.thefishlive.auth.data.Token;

/**
 * Represents a client used to connect to a remote authentication server.
 */
public interface Client {

    /**
     * Get the id for this client.
     *
     * @return the id for this client
     */
    public Token getClientId();

}
