package uk.co.thefishlive.meteor.login;

import uk.co.thefishlive.auth.data.Profile;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.meteor.MeteorAuthHandler;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.data.LoginProfile;
import uk.co.thefishlive.meteor.login.exception.LoginException;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

public class MeteorLoginHandlerTest {

    private MeteorAuthHandler authHandler;

    @Before
    public void setup() {
        authHandler = new MeteorAuthHandler();
    }

    @Test
    public void testLogin() throws Exception {
        Profile profile = new LoginProfile(AuthToken.decode("AE-F305E6DC-FA91BBBEEAB60D-4FA851DB"));
        Session session = authHandler.getLoginHandler().login(profile, "password".toCharArray());
        System.out.println(session);
        assertNotNull(session);
}
}
