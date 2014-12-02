package uk.co.thefishlive.meteor.session;

import com.google.common.base.Splitter;
import com.google.common.base.Throwables;
import uk.co.thefishlive.auth.data.Profile;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.meteor.MeteorAuthHandler;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.data.LoginProfile;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.UnknownHostException;

import static org.junit.Assert.*;

public class MeteorSessionHandlerTest {

    public static final String TEST_USER_ID = "AE-2806CE20-C4B7FC614BD01F-ADE203AB";

    private MeteorAuthHandler authHandler;

    @Before
    public void setup() {
        Proxy proxy = Proxy.NO_PROXY;

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