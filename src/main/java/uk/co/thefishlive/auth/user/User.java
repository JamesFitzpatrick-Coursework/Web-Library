package uk.co.thefishlive.auth.user;

import java.util.List;
import uk.co.thefishlive.auth.data.Identifiable;
import uk.co.thefishlive.auth.group.GroupProfile;
import uk.co.thefishlive.auth.permission.Permissible;
import uk.co.thefishlive.auth.settings.SettingStore;

/**
 * Represents a user stored on the remote user database.
 */
public interface User extends Identifiable<UserProfile>, SettingStore, Permissible {

    /**
     * Get all the groups that this user is a member of.
     *
     * @return a list of group profiles for groups that this user is a member
     *      of
     */
    public List<GroupProfile> getGroups();

    /**
     * Add this user to a specific group.
     *
     * @param group the group to add this user to
     */
    public void addGroup(GroupProfile group);
}
