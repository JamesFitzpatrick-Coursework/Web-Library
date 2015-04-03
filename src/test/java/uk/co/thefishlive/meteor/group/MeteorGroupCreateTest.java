package uk.co.thefishlive.meteor.group;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import uk.co.thefishlive.auth.group.Group;
import uk.co.thefishlive.auth.group.GroupProfile;
import uk.co.thefishlive.auth.permission.Permission;
import uk.co.thefishlive.auth.permission.PermissionRegistry;
import uk.co.thefishlive.auth.permission.SimplePermission;
import uk.co.thefishlive.auth.settings.Setting;
import uk.co.thefishlive.http.exception.HttpException;
import uk.co.thefishlive.meteor.TestBase;
import uk.co.thefishlive.meteor.settings.StringSetting;

import java.net.URISyntaxException;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

// We have to run these tests in this exact order or they will fail
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MeteorGroupCreateTest extends TestBase {

    static {
        PermissionRegistry.registerPermission(new SimplePermission("test-permission-group", "Test Permission"));
    }

    public static final Setting<String, String> TEST_SETTING = new StringSetting("test-setting", "test-setting");
    public static final Permission TEST_PERMISSION = PermissionRegistry.getPermission("test-permission-group");

    private static GroupProfile testProfile;
    private static Group testGroup;
    private static MeteorGroupProfile updatedProfile;

    @BeforeClass
    public static void setup() throws URISyntaxException {
        testProfile = new MeteorGroupProfile("test-group-" + Math.abs(new Random().nextInt()), "test-group");
        updatedProfile = new MeteorGroupProfile((String) null, "test-group-updated");
    }

    @Test
    public void test01_CreateGroup() throws Exception {
        testProfile = authHandler.getGroupManager().createGroup(testProfile);
        assertNotNull(testProfile);
    }

    @Test
    public void test02_LookupGroup() throws Exception {
        testGroup = authHandler.getGroupManager().getGroupProfile(testProfile);
        assertEquals(testProfile, testGroup.getProfile());
    }

    @Test
    public void test03_UpdateName() throws Exception {
        GroupProfile updated = testGroup.updateProfile(updatedProfile);

        assertNotEquals(updated, updatedProfile);
        assertNotEquals(updated, testProfile);

        testProfile = updated;
    }


    @Test
    public void test04_LookupGroup() throws Exception {
        testGroup = authHandler.getGroupManager().getGroupProfile(testProfile);
        assertEquals(updatedProfile.getDisplayName(), testProfile.getDisplayName());
    }

    @Test
    public void test05_SettingEdit() throws Exception {
        testGroup.setSetting(TEST_SETTING);
    }

    @Test
    public void test06_SettingLookup() throws Exception {
        Setting setting = testGroup.getSetting(TEST_SETTING.getKey());
        assertEquals(TEST_SETTING, setting);
    }

    @Test
    public void test07_SettingDelete() throws Exception {
        testGroup.deleteSetting(TEST_SETTING.getKey());
    }

    @Test
    public void test08_PermissionAdd() throws Exception {
        testGroup.addPermission(TEST_PERMISSION);
    }

    @Test
    public void test09_PermissionCheck() throws Exception {
        assertTrue(testGroup.hasPermission(TEST_PERMISSION));
    }

    @Test
    public void test10_PermissionDelete() throws Exception {
        testGroup.removePermission(TEST_PERMISSION);
    }

    @Test
    public void test11_DeleteGroup() throws Exception {
        assertTrue(authHandler.getGroupManager().deleteGroup(testProfile));
    }

    @Test(expected = HttpException.class) // User shouldn't exist any more
    public void test12_LookupGroup() throws Exception {
        authHandler.getGroupManager().getGroupProfile(testProfile);
    }

}
