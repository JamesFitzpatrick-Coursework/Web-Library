package uk.co.thefishlive.auth.session;

import uk.co.thefishlive.http.HttpHeader;

import java.util.List;

/**
 * Represents an object that stores an active session
 */
public interface SessionStore {

    /**
     * Sets the currently active auth session.
     *
     * @param session
     *     the currently logged in session
     */
    public void setActiveSession(Session session);

    /**
     * Gets the currently active auth session.
     *
     * @return the current login session
     */
    public Session getActiveSession();

    /**
     * Get the authentication headers for this session store
     *
     * @return a list of authentication headers
     */
    public List<HttpHeader> getAuthHeaders();

    /**
     * Add a listener to listen for active session updates.
     *
     * @param listener
     *     the listener to register
     */
    public void addSessionListener(SessionListener listener);

}
