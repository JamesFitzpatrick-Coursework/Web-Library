package uk.co.thefishlive.auth.session;

import java.util.List;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.http.HttpHeader;

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

    /**
     * Get the authentication headers for this session store
     *
     * @return a list of authentication headers
     */
    public List<HttpHeader> getAuthHeaders();

}
