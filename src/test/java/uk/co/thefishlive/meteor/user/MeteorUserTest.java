package uk.co.thefishlive.meteor.user;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import uk.co.thefishlive.auth.group.GroupProfile;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.auth.user.User;
import uk.co.thefishlive.meteor.MeteorAuthHandler;
import uk.co.thefishlive.meteor.TestBase;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.session.MeteorSession;
import uk.co.thefishlive.meteor.utils.ProxyUtils;

import static org.junit.Assert.*;

public class MeteorUserTest extends TestBase {

    private User user;

    @Before
    public void setup() throws IOException {
        user = authHandler.getUserManager().getUserProfile(new MeteorUserProfile("admin"));
        System.out.println(user.getProfile());
    }

    @Test
    public void testGetGroups() throws Exception {
        List<GroupProfile> groups = user.getGroups();
        assertTrue(groups.size() > 0);
        System.out.println(groups);
    }
}