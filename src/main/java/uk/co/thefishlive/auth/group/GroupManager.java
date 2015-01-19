package uk.co.thefishlive.auth.group;

import java.io.IOException;
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
    public Group getGroupProfile(GroupProfile group) throws IOException;

    /**
     * Create a new group, based off a skeletal profile provided.
     *
     * @param profile the profile to base the new group off
     * @return the new user's {@link Group} instance
     */
    public GroupProfile createGroup(GroupProfile profile) throws IOException;

    /**
     * Delete a specified group.
     *
     * @param profile the profile of the group to delete
     * @return true if successful, false otherwise
     */
    boolean deleteGroup(GroupProfile profile) throws IOException;

    /**
     * Fetch group profiles for all the groups held in the remote servers group
     * database.
     *
     * @return a immutable list of all the groups held in the group database
     */
    public List<GroupProfile> getGroups() throws IOException;

}