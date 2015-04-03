package uk.co.thefishlive.meteor.user;

import org.junit.Test;

import uk.co.thefishlive.auth.user.User;
import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.meteor.TestBase;
import uk.co.thefishlive.meteor.data.AuthToken;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MeteorUserManagerTest extends TestBase {

    public static final UserProfile TEST_USER_PROFILE = new MeteorUserProfile(AuthToken.decode("AE-5CC7BBA3-D631FEB34020F5-18EA0279"), "admin", "admin");

    @Test
    public void testGetUserProfile() throws Exception {
        User user = authHandler.getUserManager().getUserProfile(TEST_USER_PROFILE);
        assertNotNull(user);
        assertEquals(TEST_USER_PROFILE, user.getProfile());
    }

    @Test
    public void testGetUsers() throws Exception {
        List<UserProfile> profiles = authHandler.getUserManager().getUsers();
        System.out.println(profiles);
        assertTrue(profiles.size() > 0);
    }
}