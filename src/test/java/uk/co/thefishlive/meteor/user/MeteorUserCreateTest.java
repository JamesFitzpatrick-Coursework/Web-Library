package uk.co.thefishlive.meteor.user;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import uk.co.thefishlive.auth.data.Token;
import uk.co.thefishlive.auth.login.exception.LoginException;
import uk.co.thefishlive.auth.permission.Permission;
import uk.co.thefishlive.auth.permission.PermissionRegistry;
import uk.co.thefishlive.auth.permission.SimplePermission;
import uk.co.thefishlive.auth.settings.Setting;
import uk.co.thefishlive.auth.user.User;
import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.http.exception.HttpException;
import uk.co.thefishlive.meteor.TestBase;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.settings.StringSetting;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

// We have to run these tests in this exact order or they will fail
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MeteorUserCreateTest extends TestBase {

    static {
        PermissionRegistry.registerPermission(new SimplePermission("test-permission-user", "Test Permission"));
    }

    public static final Token TEST_USER_ID = AuthToken.decode("AE-5CC7BBA3-D631FEB34020F5-18EA0279");
    public static final String TEST_USER_NAME = "admin";

    public static final Setting<String, String> TEST_SETTING = new StringSetting("test-setting", "test-setting");
    public static final Permission TEST_PERMISSION = PermissionRegistry.getPermission("test-permission-user");

    private static UserProfile updatedProfile;
    private static UserProfile testProfile;
    private static User testUser;

    @BeforeClass
    public static void setup() throws IOException, LoginException, URISyntaxException {
        int random = Math.abs(new Random().nextInt());
        testProfile = new MeteorUserProfile("test-user-" + random, "test-user");
        updatedProfile = new MeteorUserProfile((String) null, "test-user-updated");
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
    public void test03_UpdateName() throws Exception {
        UserProfile updated = testUser.updateProfile(updatedProfile);

        assertNotEquals(updated, updatedProfile);
        assertNotEquals(updated, testProfile);

        testProfile = updated;
    }


    @Test
    public void test04_LookupUser() throws Exception {
        testUser = authHandler.getUserManager().getUserProfile(testProfile);
        assertEquals(updatedProfile.getDisplayName(), testProfile.getDisplayName());
    }

    @Test
    public void test05_SettingEdit() throws Exception {
        testUser.setSetting(TEST_SETTING);
    }

    @Test
    public void test06_SettingLookup() throws Exception {
        Setting setting = testUser.getSetting(TEST_SETTING.getKey());
        assertEquals(TEST_SETTING, setting);
    }

    @Test
    public void test07_SettingDelete() throws Exception {
        testUser.deleteSetting(TEST_SETTING.getKey());
    }

    @Test
    public void test08_PermissionAdd() throws Exception {
        testUser.addPermission(TEST_PERMISSION);
    }

    @Test
    public void test09_PermissionCheck() throws Exception {
        assertTrue(testUser.hasPermission(TEST_PERMISSION));
    }

    @Test
    public void test10_PermissionDelete() throws Exception {
        testUser.removePermission(TEST_PERMISSION);
    }

    @Test
    public void test11_DeleteUser() throws Exception {
        assertTrue(authHandler.getUserManager().deleteUser(testProfile));
    }

    @Test(expected = HttpException.class) // User shouldn't exist any more
    public void test12_LookupUser() throws Exception {
        authHandler.getUserManager().getUserProfile(testProfile);
    }

}
