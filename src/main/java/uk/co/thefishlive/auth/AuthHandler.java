package uk.co.thefishlive.auth;

import uk.co.thefishlive.auth.login.LoginHandler;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.auth.session.SessionHandler;

/**
 * Represents the handler controlling all authentication, contains methods to
 * authenticate users.
 */
public interface AuthHandler {

    /**
     *
     * @return
     */
    public LoginHandler getLoginHandler();

    /**
     *
     * @return
     */
    public SessionHandler getSessionHandler();

    /**
     *
     * @param session
     */
    public void setActiveSession(Session session);

    /**
     *
     * @return
     */
    public Session getActiveSession();

}
