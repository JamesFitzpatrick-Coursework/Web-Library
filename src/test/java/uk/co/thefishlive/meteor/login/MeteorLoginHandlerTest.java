package uk.co.thefishlive.meteor.login;

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
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class MeteorLoginHandlerTest {

    public static final String TEST_USER_ID = "AE-5CC7BBA3-D631FEB34020F5-18EA0279"; // TODO change to a dynamic lookup
    public static final String TEST_USER_NAME = "admin";

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
    public void testLoginById() throws Exception {
        UserProfile profile = new LoginProfile(AuthToken.decode(TEST_USER_ID));
        Session session = authHandler.getLoginHandler().login(profile, "password".toCharArray());
        System.out.println(session);
        assertNotNull(session);
    }

    @Test
    public void testLoginByName() throws Exception {
        UserProfile profile = new LoginProfile(TEST_USER_NAME);
        Session session = authHandler.getLoginHandler().login(profile, "password".toCharArray());
        System.out.println(session);
        assertNotNull(session);
    }
}
