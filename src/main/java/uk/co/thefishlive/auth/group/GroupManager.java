package uk.co.thefishlive.auth.group;

import java.util.List;

/**
 * Represents a class that handles user management with a remote server.
 */
public interface GroupManager {

    /**
     * Fill out a group instance for the specified group profile.
     *
     * @param group the group to fetch the information about
     * @return the filled out {@link Group} instance
     */
    public Group getGroupProfile(GroupProfile group);

    /**
     * Create a new group, based off a skeletal profile provided.
     *
     * @param profile the profile to base the new group off
     * @return the new user's {@link Group} instance
     */
    public Group createGroup(GroupProfile profile);

    /**
     * Fetch group profiles for all the groups held in the remote servers group
     * database.
     *
     * @return a immutable list of all the groups held in the group database
     */
    public List<GroupProfile> getGroups();

}