package uk.co.thefishlive.auth.session;

import uk.co.thefishlive.auth.data.Identifiable;
import uk.co.thefishlive.auth.session.exception.SessionException;
import uk.co.thefishlive.auth.user.UserProfile;

import java.io.IOException;

/**
 * Represents an active login session, used to authenticated with the remote server.
 */
public interface Session extends Identifiable<UserProfile> {

    /**
     * Refresh this session if it has expired.
     *
     * @return the new session
     * @throws IOException
     *     if the request was network error causing the request to not be carried out
     * @throws SessionException
     *     if the remote server refused the authentication details provided
     */
    public Session refreshSession() throws IOException, SessionException;

    /**
     * Invalidate this session and all tokens associated with it.
     *
     * @return true if the request was successful, false otherwise
     * @throws IOException
     *     if the request was network error causing the request to not be carried out
     * @throws SessionException
     *     if the remote server refused the authentication details provided
     */
    public boolean invalidate() throws IOException, SessionException;

    /**
     * Check to see if this session is still valid.
     *
     * @return true if the session is valid, false otherwise
     * @throws IOException
     *     if the request was network error causing the request to not be carried out
     * @throws SessionException
     *     if the remote server refused the authentication details provided
     */
    public boolean isValid() throws IOException, SessionException;

}
