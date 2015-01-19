package uk.co.thefishlive.meteor.group;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import uk.co.thefishlive.auth.group.Group;
import uk.co.thefishlive.auth.group.GroupProfile;
import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.meteor.MeteorAuthHandler;
import uk.co.thefishlive.meteor.session.MeteorSession;
import uk.co.thefishlive.meteor.user.MeteorUserProfile;
import uk.co.thefishlive.meteor.utils.ProxyUtils;

import static org.junit.Assert.assertTrue;

public class MeteorGroupTest {

    private MeteorAuthHandler authHandler;
    private Group group;

    @Before
    public void setup() throws Exception {
        authHandler = new MeteorAuthHandler(ProxyUtils.getSystemProxy());
        authHandler.setActiveSession(MeteorSession.generateRandomSession(authHandler, new MeteorUserProfile("admin")));
        group = authHandler.getGroupManager().getGroupProfile(new MeteorGroupProfile("admin"));
        System.out.println(group.getProfile());
    }

    @Test
    public void testGetGroups() throws Exception {
        List<UserProfile> users = group.getUsers();
        assertTrue(users.size() > 0);
        System.out.println(users);
    }
}