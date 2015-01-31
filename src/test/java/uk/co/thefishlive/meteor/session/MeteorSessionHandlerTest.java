package uk.co.thefishlive.meteor.session;

import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.meteor.MeteorAuthHandler;
import uk.co.thefishlive.meteor.TestBase;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.user.MeteorUserProfile;

import org.junit.Before;
import org.junit.Test;
import uk.co.thefishlive.meteor.utils.ProxyUtils;

import java.net.*;

import static org.junit.Assert.*;

public class MeteorSessionHandlerTest extends TestBase {

    public static final String TEST_USER_ID = "AE-5CC7BBA3-D631FEB34020F5-18EA0279"; // TODO change to a dynamic lookup

    @Before
    public void setup() throws URISyntaxException {
        authHandler = new MeteorAuthHandler(ProxyUtils.getSystemProxy());
    }

    @Test
    public void testRefresh() throws Exception {
        Session session = authHandler.getActiveSession();
        Session refreshedSession = session.refreshSession();

        assertNotNull(refreshedSession);
        assertNotEquals(session, refreshedSession);
    }

    @Test
    public void testValidate() throws Exception {
        Session session = authHandler.getActiveSession();

        assertTrue(session.isValid());
    }

    @Test
    public void testInvalidate() throws Exception {
        Session session = authHandler.getActiveSession();

        assertTrue(session.invalidate());
        assertFalse(session.isValid());
    }
}