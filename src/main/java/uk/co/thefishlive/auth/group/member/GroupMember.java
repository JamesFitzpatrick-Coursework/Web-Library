package uk.co.thefishlive.auth.group.member;

import uk.co.thefishlive.auth.group.GroupProfile;

import java.util.List;

/**
 * Represents an class that can become a member of groups.
 */
public interface GroupMember {

    /**
     * Get all the groups that this object is a member of.
     *
     * @return a list of group profiles for objects that this user is a member of
     */
    public List<GroupProfile> getGroups();

    /**
     * Add this object to a specific group.
     *
     * @param group
     *     the group to add this object to
     */
    public void addGroup(GroupProfile group);

}
