package uk.co.thefishlive.auth;

import uk.co.thefishlive.auth.login.LoginHandler;
import uk.co.thefishlive.auth.session.SessionHandler;

/**
 * Created by James on 14/10/2014.
 */
public interface AuthHandler {

    public LoginHandler getLoginHandler();

    public SessionHandler getSessionHandler();

}
