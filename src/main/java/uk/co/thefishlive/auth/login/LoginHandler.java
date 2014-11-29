package uk.co.thefishlive.auth.login;

import uk.co.thefishlive.auth.data.Profile;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.meteor.login.exception.LoginException;

import java.io.IOException;

/**
 * Created by James on 14/10/2014.
 */
public interface LoginHandler {

    public Session login(Profile profile, char[] password) throws IOException, LoginException;

}
