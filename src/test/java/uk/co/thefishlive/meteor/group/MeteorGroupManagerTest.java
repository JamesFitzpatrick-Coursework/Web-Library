package uk.co.thefishlive.meteor.group;

import org.junit.Test;

import uk.co.thefishlive.auth.group.Group;
import uk.co.thefishlive.auth.group.GroupProfile;
import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.meteor.TestBase;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.user.MeteorUserProfile;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MeteorGroupManagerTest extends TestBase {

    public static final GroupProfile TEST_GROUP_PROFILE = new MeteorGroupProfile(AuthToken.decode("AF-6C16A21B-5D0CB09D29009B-54B9129F"), "admin", "admin");
    public static final UserProfile TEST_USER_PROFILE = new MeteorUserProfile(AuthToken.decode("AE-5CC7BBA3-D631FEB34020F5-18EA0279"), "admin", "admin");

    @Test
    public void testGetUserProfile() throws Exception {
        Group group = authHandler.getGroupManager().getGroupProfile(TEST_GROUP_PROFILE);
        assertNotNull(group);
        assertEquals(TEST_GROUP_PROFILE, group.getProfile());
    }

    @Test
    public void testGetUsers() throws Exception {
        List<GroupProfile> profiles = authHandler.getGroupManager().getGroups();
        System.out.println(profiles);
        assertTrue(profiles.size() > 0);
    }
}