package uk.co.thefishlive.meteor.login;

import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.meteor.MeteorAuthHandler;
import uk.co.thefishlive.meteor.TestBase;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.user.MeteorUserProfile;

import org.junit.Before;
import org.junit.Test;
import uk.co.thefishlive.meteor.utils.ProxyUtils;

import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class MeteorLoginHandlerTest extends TestBase {

    public static final String TEST_USER_ID = "AE-5CC7BBA3-D631FEB34020F5-18EA0279";
    public static final String TEST_USER_NAME = "admin";

    @Test
    public void testLoginById() throws Exception {
        UserProfile profile = new MeteorUserProfile(AuthToken.decode(TEST_USER_ID));
        Session session = authHandler.getLoginHandler().login(profile, "password".toCharArray());
        System.out.println(session);
        assertNotNull(session);
    }

    @Test
    public void testLoginByName() throws Exception {
        UserProfile profile = new MeteorUserProfile(TEST_USER_NAME);
        Session session = authHandler.getLoginHandler().login(profile, "password".toCharArray());
        System.out.println(session);
        assertNotNull(session);
    }
}
