package uk.co.thefishlive.meteor.user;

import java.net.URISyntaxException;
import java.util.Random;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import uk.co.thefishlive.auth.data.Token;
import uk.co.thefishlive.auth.permission.Permission;
import uk.co.thefishlive.auth.permission.PermissionRegistry;
import uk.co.thefishlive.auth.permission.SimplePermission;
import uk.co.thefishlive.auth.settings.Setting;
import uk.co.thefishlive.auth.user.User;
import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.http.exception.HttpException;
import uk.co.thefishlive.meteor.MeteorAuthHandler;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.session.MeteorSession;
import uk.co.thefishlive.meteor.settings.StringSetting;
import uk.co.thefishlive.meteor.utils.ProxyUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

// We have to run these tests in this exact order or they will fail
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MeteorUserCreateTest {

    static {
        PermissionRegistry.registerPermission(new SimplePermission("test-permission-user", "Test Permission"));
    }

    public static final Token TEST_USER_ID = AuthToken.decode("AE-5CC7BBA3-D631FEB34020F5-18EA0279");
    public static final String TEST_USER_NAME = "admin";

    public static final Setting<String, String> TEST_SETTING = new StringSetting("test-setting", "test-setting");
    public static final Permission TEST_PERMISSION = PermissionRegistry.getPermission("test-permission-user");

    private static UserProfile testProfile;
    private static User testUser;
    private static MeteorAuthHandler authHandler;

    @BeforeClass
    public static void setup() throws URISyntaxException {
        authHandler = new MeteorAuthHandler(ProxyUtils.getSystemProxy());
        authHandler.setActiveSession(MeteorSession.generateRandomSession(authHandler, new MeteorUserProfile(TEST_USER_ID)));

        testProfile = new MeteorUserProfile("test-user-" + Math.abs(new Random().nextInt()), "test-user");
    }

    @Test
    public void test01_CreateUser() throws Exception {
        testProfile = authHandler.getUserManager().createUser(testProfile, "password".toCharArray());
        assertNotNull(testProfile);
    }

    @Test
    public void test02_LookupUser() throws Exception {
        testUser = authHandler.getUserManager().getUserProfile(testProfile);
        assertEquals(testProfile, testUser.getProfile());
    }

    @Test
    public void test03_SettingEdit() throws Exception {
        testUser.setSetting(TEST_SETTING);
    }

    @Test
    public void test04_SettingLookup() throws Exception {
        Setting setting = testUser.getSetting(TEST_SETTING.getKey());
        assertEquals(TEST_SETTING, setting);
    }

    @Test
    public void test05_SettingDelete() throws Exception {
        testUser.deleteSetting(TEST_SETTING.getKey());
    }

    @Test
    public void test06_PermissionAdd() throws Exception {
        testUser.addPermission(TEST_PERMISSION);
    }

    @Test
    public void test07_PermissionCheck() throws Exception {
        assertTrue(testUser.hasPermission(TEST_PERMISSION));
    }

    @Test
    public void test08_PermissionDelete() throws Exception {
        testUser.removePermission(TEST_PERMISSION);
    }

    @Test
    public void test09_DeleteUser() throws Exception {
        assertTrue(authHandler.getUserManager().deleteUser(testProfile));
    }

    @Test(expected = HttpException.class) // User shouldn't exist any more
    public void test10_LookupUser()  throws Exception {
        authHandler.getUserManager().getUserProfile(testProfile);
    }

}
