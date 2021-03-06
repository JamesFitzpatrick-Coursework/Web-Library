package uk.co.thefishlive.meteor;

import org.junit.BeforeClass;

import uk.co.thefishlive.auth.login.exception.LoginException;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.meteor.user.MeteorUserProfile;
import uk.co.thefishlive.meteor.utils.ProxyUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class TestBase {

    protected static MeteorAuthHandler authHandler;

    @BeforeClass
    public static void setupClass() throws IOException, LoginException, URISyntaxException {
        authHandler = new MeteorAuthHandler(ProxyUtils.getSystemProxy());
        Session session = authHandler.getLoginHandler().login(new MeteorUserProfile("admin"), "password".toCharArray());
        authHandler.setActiveSession(session);
    }
}
