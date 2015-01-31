package uk.co.thefishlive.meteor.group;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import uk.co.thefishlive.auth.group.Group;
import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.meteor.TestBase;

import static org.junit.Assert.assertTrue;

public class MeteorGroupTest extends TestBase {

    private Group group;

    @Before
    public void setup() throws Exception {
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