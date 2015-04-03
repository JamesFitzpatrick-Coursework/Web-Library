package uk.co.thefishlive.meteor.session;

import org.junit.Test;

import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.meteor.TestBase;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MeteorSessionHandlerTest extends TestBase {

    public static final String TEST_USER_ID = "AE-5CC7BBA3-D631FEB34020F5-18EA0279"; // TODO change to a dynamic lookup

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