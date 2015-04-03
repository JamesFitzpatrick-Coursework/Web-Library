package uk.co.thefishlive.auth.session;

import uk.co.thefishlive.auth.session.exception.SessionException;

import java.io.IOException;

/**
 *
 */
public interface SessionHandler {

    /**
     * Check if a login session is valid.
     *
     * @param session
     *     the session to check
     *
     * @return true if its valid, false otherwise
     * @throws SessionException
     *     signals that the session request was unsuccessful
     * @throws IOException
     *     signals that there has been an error sending the request
     */
    public boolean isValid(Session session) throws IOException, SessionException;

    /**
     * Invalidate a given login session.
     *
     * @param session
     *     the session to invalidate
     *
     * @return true if the session was invalidated successfully, false otherwise
     * @throws SessionException
     *     signals that the session request was unsuccessful
     * @throws IOException
     *     signals that there has been an error sending the request
     */
    public boolean invalidate(Session session) throws IOException, SessionException;

    /**
     * Refresh a session.
     *
     * @param session
     *     the session to refresh
     *
     * @return the new session
     * @throws SessionException
     *     signals that the session request was unsuccessful
     * @throws IOException
     *     signals that there has been an error sending the request
     */
    public Session refresh(Session session) throws IOException, SessionException;

}
