package uk.co.thefishlive.meteor.login;

import uk.co.thefishlive.auth.data.Profile;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.meteor.MeteorAuthHandler;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.data.LoginProfile;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MeteorLoginHandlerTest {

    public static final String TEST_USER_ID = "AE-2806CE20-C4B7FC614BD01F-ADE203AB";

    private MeteorAuthHandler authHandler;

    @Before
    public void setup() {
        authHandler = new MeteorAuthHandler();
    }

    @Test
    public void testLoginById() throws Exception {
        Profile profile = new LoginProfile(AuthToken.decode(TEST_USER_ID));
        Session session = authHandler.getLoginHandler().login(profile, "password".toCharArray());
        System.out.println(session);
        assertNotNull(session);
    }

    @Test
    public void testLoginByName() throws Exception {
        Profile profile = new LoginProfile("jfitzpatrick");
        Session session = authHandler.getLoginHandler().login(profile, "password".toCharArray());
        System.out.println(session);
        assertNotNull(session);
    }
}
