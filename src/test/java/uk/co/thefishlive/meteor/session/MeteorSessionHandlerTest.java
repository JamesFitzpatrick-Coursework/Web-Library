package uk.co.thefishlive.meteor.session;

import uk.co.thefishlive.auth.data.Profile;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.meteor.MeteorAuthHandler;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.data.LoginProfile;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MeteorSessionHandlerTest {

    public static final String TEST_USER_ID = "AE-2806CE20-C4B7FC614BD01F-ADE203AB";

    private MeteorAuthHandler authHandler;

    @Before
    public void setup() {
        authHandler = new MeteorAuthHandler();
    }

    @Test
    public void testRefresh() throws Exception {
        Profile profile = new LoginProfile(AuthToken.decode(TEST_USER_ID));
        Session session = authHandler.getLoginHandler().login(profile, "password".toCharArray());
        assertNotNull(session);
        Session refreshedSession = session.refreshSession();
        assertNotNull(refreshedSession);
        assertNotEquals(session, refreshedSession);
    }

    @Test
    public void testValidate() throws Exception {
        Profile profile = new LoginProfile(AuthToken.decode(TEST_USER_ID));
        Session session = authHandler.getLoginHandler().login(profile, "password".toCharArray());
        assertTrue(session.isValid());
    }

    @Test
    public void testInvalidate() throws Exception {
        Profile profile = new LoginProfile(AuthToken.decode(TEST_USER_ID));
        Session session = authHandler.getLoginHandler().login(profile, "password".toCharArray());
        assertTrue(session.invalidate());
        assertFalse(session.isValid());
    }
}