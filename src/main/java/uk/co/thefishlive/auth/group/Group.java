package uk.co.thefishlive.auth.group;

import uk.co.thefishlive.auth.assessments.assignments.AssignmentTarget;
import uk.co.thefishlive.auth.data.Identifiable;
import uk.co.thefishlive.auth.group.member.GroupMemberProfile;
import uk.co.thefishlive.auth.permission.Permissible;
import uk.co.thefishlive.auth.settings.SettingStore;

import java.util.List;

/**
 * Represents a group stored on the remote group database.
 */
public interface Group extends Identifiable<GroupProfile>, SettingStore, Permissible, AssignmentTarget {

    /**
     * Get all the user that are members of this group.
     *
     * @return a list of user profiles for the users that are members of this group
     */
    public List<GroupMemberProfile> getUsers();

}
