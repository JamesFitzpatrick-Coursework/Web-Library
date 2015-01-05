package uk.co.thefishlive.meteor.session;

import com.google.common.base.Throwables;
import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.meteor.MeteorAuthHandler;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.data.LoginProfile;

import org.junit.Before;
import org.junit.Test;
import uk.co.thefishlive.meteor.utils.ProxyUtils;

import java.io.IOException;
import java.net.*;

import static org.junit.Assert.*;

public class MeteorSessionHandlerTest {

    public static final String TEST_USER_ID = "AE-5CC7BBA3-D631FEB34020F5-18EA0279"; // TODO change to a dynamic lookup

    private MeteorAuthHandler authHandler;

    @Before
    public void setup() throws URISyntaxException {
        Proxy proxy = ProxyUtils.getSystemProxy();

        if (System.getProperty("uk.co.thefishlive.proxy") != null) {
            try {
                String proxyString = System.getProperty("uk.co.thefishlive.proxy");
                String host = proxyString.substring(0, proxyString.indexOf(':'));
                int port = Integer.parseInt(proxyString.substring(proxyString.indexOf(':') + 1));
                proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(InetAddress.getByName(host), port));
            } catch (IOException e) {
                Throwables.propagate(e);
            }
        }

        authHandler = new MeteorAuthHandler(proxy);
    }

    @Test
    public void testRefresh() throws Exception {
        UserProfile profile = new LoginProfile(AuthToken.decode(TEST_USER_ID));
        Session session = authHandler.getLoginHandler().login(profile, "password".toCharArray());
        assertNotNull(session);
        Session refreshedSession = session.refreshSession();
        assertNotNull(refreshedSession);
        assertNotEquals(session, refreshedSession);
    }

    @Test
    public void testValidate() throws Exception {
        UserProfile profile = new LoginProfile(AuthToken.decode(TEST_USER_ID));
        Session session = authHandler.getLoginHandler().login(profile, "password".toCharArray());
        assertTrue(session.isValid());
    }

    @Test
    public void testInvalidate() throws Exception {
        UserProfile profile = new LoginProfile(AuthToken.decode(TEST_USER_ID));
        Session session = authHandler.getLoginHandler().login(profile, "password".toCharArray());
        assertTrue(session.invalidate());
        assertFalse(session.isValid());
    }
}