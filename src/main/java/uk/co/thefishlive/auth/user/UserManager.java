package uk.co.thefishlive.auth.user;

import java.io.IOException;
import java.util.List;

/**
 * Represents a class that handles user management with a remote server.
 */
public interface UserManager {

    /**
     * Fill out a user instance for the specified user profile.
     *
     * @param user the user to fetch the information about
     * @return the filled out {@link User} instance
     */
    public User getUserProfile(UserProfile user) throws IOException;

    /**
     * Create a new user, based off a skeletal profile provided.
     *
     * @param profile the profile to base the new user off
     * @param password the password for the new user
     * @return the new user's {@link User} instance
     */
    public UserProfile createUser(UserProfile profile, char[] password) throws IOException;

    /**
     * Delete a specified user.
     *
     * @param profile the profile of the user to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteUser(UserProfile profile) throws IOException;

    /**
     * Fetch user profiles for all the users held in the remote servers user
     * database.
     *
     * @return a immutable list of all the users held in the user database
     */
    public List<UserProfile> getUsers() throws IOException;

}
