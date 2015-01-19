package uk.co.thefishlive.auth.group;

import java.util.List;
import uk.co.thefishlive.auth.data.Identifiable;
import uk.co.thefishlive.auth.permission.Permissible;
import uk.co.thefishlive.auth.settings.SettingStore;
import uk.co.thefishlive.auth.user.UserProfile;

/**
 * Represents a group stored on the remote group database.
 */
public interface Group extends Identifiable<GroupProfile>, SettingStore, Permissible {

    /**
     * Get all the user that are members of this group.
     *
     * @return a list of user profiles for the users that are members of this
     *      group
     */
    public List<UserProfile> getUsers();

}
