package uk.co.thefishlive.meteor.group;

import java.net.URISyntaxException;
import java.util.Random;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import uk.co.thefishlive.auth.data.Token;
import uk.co.thefishlive.auth.group.Group;
import uk.co.thefishlive.auth.group.GroupProfile;
import uk.co.thefishlive.auth.permission.Permission;
import uk.co.thefishlive.auth.permission.PermissionRegistry;
import uk.co.thefishlive.auth.permission.SimplePermission;
import uk.co.thefishlive.auth.settings.Setting;
import uk.co.thefishlive.http.exception.HttpException;
import uk.co.thefishlive.meteor.MeteorAuthHandler;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.session.MeteorSession;
import uk.co.thefishlive.meteor.settings.StringSetting;
import uk.co.thefishlive.meteor.user.MeteorUserProfile;
import uk.co.thefishlive.meteor.utils.ProxyUtils;

import static org.junit.Assert.*;

// We have to run these tests in this exact order or they will fail
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MeteorGroupCrateTest {

    static {
        PermissionRegistry.registerPermission(new SimplePermission("test-permission-group", "Test Permission"));
    }

    public static final Token TEST_GROUP_ID = AuthToken.decode("AF-6C16A21B-5D0CB09D29009B-54B9129F");
    public static final Token TEST_USER_ID = AuthToken.decode("AE-5CC7BBA3-D631FEB34020F5-18EA0279");
    public static final String TEST_GROUP_NAME = "admin";

    public static final Setting<String, String> TEST_SETTING = new StringSetting("test-setting", "test-setting");
    public static final Permission TEST_PERMISSION = PermissionRegistry.getPermission("test-permission-group");

    private static GroupProfile testProfile;
    private static Group testGroup;
    private static MeteorAuthHandler authHandler;

    @BeforeClass
    public static void setup() throws URISyntaxException {
        authHandler = new MeteorAuthHandler(ProxyUtils.getSystemProxy());
        authHandler.setActiveSession(MeteorSession.generateRandomSession(authHandler, new MeteorUserProfile(TEST_USER_ID)));

        testProfile = new MeteorGroupProfile("test-group-" + Math.abs(new Random().nextInt()), "test-group");
    }

    @Test
    public void test01_CreateUser() throws Exception {
        testProfile = authHandler.getGroupManager().createGroup(testProfile);
        assertNotNull(testProfile);
    }

    @Test
    public void test02_LookupUser() throws Exception {
        testGroup = authHandler.getGroupManager().getGroupProfile(testProfile);
        assertEquals(testProfile, testGroup.getProfile());
    }

    @Test
    public void test03_SettingEdit() throws Exception {
        testGroup.setSetting(TEST_SETTING);
    }

    @Test
    public void test04_SettingLookup() throws Exception {
        Setting setting = testGroup.getSetting(TEST_SETTING.getKey());
        assertEquals(TEST_SETTING, setting);
    }

    @Test
    public void test05_SettingDelete() throws Exception {
        testGroup.deleteSetting(TEST_SETTING.getKey());
    }

    @Test
    public void test06_PermissionAdd() throws Exception {
        testGroup.addPermission(TEST_PERMISSION);
    }

    @Test
    public void test07_PermissionCheck() throws Exception {
        assertTrue(testGroup.hasPermission(TEST_PERMISSION));
    }

    @Test
    public void test08_PermissionDelete() throws Exception {
        testGroup.removePermission(TEST_PERMISSION);
    }

    @Test
    public void test09_DeleteUser() throws Exception {
        assertTrue(authHandler.getGroupManager().deleteGroup(testProfile));
    }

    @Test(expected = HttpException.class) // User shouldn't exist any more
    public void test10_LookupUser()  throws Exception {
        authHandler.getGroupManager().getGroupProfile(testProfile);
    }

}
