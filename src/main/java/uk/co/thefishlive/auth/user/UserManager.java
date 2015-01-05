package uk.co.thefishlive.auth.user;

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
    public User getUserProfile(UserProfile user);

    /**
     * Create a new user, based off a skeletal profile provided.
     *
     * @param profile the profile to base the new user off
     * @return the new user's {@link User} instance
     */
    public User createUser(UserProfile profile);

    /**
     * Fetch user profiles for all the users held in the remote servers user
     * database.
     *
     * @return a immutable list of all the users held in the user database
     */
    public List<UserProfile> getUsers();

}
