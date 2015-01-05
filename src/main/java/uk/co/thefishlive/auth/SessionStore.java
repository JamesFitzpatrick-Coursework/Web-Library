package uk.co.thefishlive.auth;

import uk.co.thefishlive.auth.session.Session;

/**
 * Represents an object that stores an active session
 */
public interface SessionStore {

    /**
     * Sets the currently active auth session.
     *
     * @param session the currently logged in session
     */
    public void setActiveSession(Session session);

    /**
     * Gets the currently active auth session.
     *
     * @return the current login session
     */
    public Session getActiveSession();

}
