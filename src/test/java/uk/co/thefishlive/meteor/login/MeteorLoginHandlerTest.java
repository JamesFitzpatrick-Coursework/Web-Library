package uk.co.thefishlive.meteor.login;

import com.google.common.base.Throwables;
import uk.co.thefishlive.auth.data.Profile;
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

    public static final String TEST_USER_ID = "AE-2806CE20-C4B7FC614BD01F-ADE203AB";

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
