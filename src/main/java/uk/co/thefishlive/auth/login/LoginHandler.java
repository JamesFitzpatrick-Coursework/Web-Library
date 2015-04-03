package uk.co.thefishlive.auth.login;

import uk.co.thefishlive.auth.login.exception.LoginException;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.auth.user.UserProfile;

import java.io.IOException;

/**
 * Represents a class that handles login requests to a remote server.
 */
public interface LoginHandler {

    /**
     * Send a login request to the remote server.
     *
     * @param profile
     *     the user profile to try and login as
     * @param password
     *     the password for the user profile to send
     *
     * @return the new session for the specified user if successful
     * @throws LoginException
     *     signals that the login request was unsuccessful
     * @throws IOException
     *     signals that there has been an error sending the request
     */
    public Session login(UserProfile profile, char[] password) throws IOException, LoginException;

}
