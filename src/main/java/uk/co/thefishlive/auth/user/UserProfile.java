package uk.co.thefishlive.auth.user;

import uk.co.thefishlive.auth.data.Token;

/**
 * Represents a class that holds the basic identifying information about a
 * user.
 */
public interface UserProfile {

    /**
     * Get this users user id, a unique identifying token for this user.
     *
     * @return this users user id
     */
    public Token getUserId();

    /**
     * Get this users username, the unique identifying name for this user.
     *
     * @return this users username
     */
    public String getUsername();

    /**
     * Get this users displayname, a prettily fomatted version of the username
     * to be output to the client.
     *
     * @return this users displayname
     */
    public String getDisplayName();

    /**
     * Checks to see if this profile has a user id.
     *
     * @return true if it does, false otherwise
     */
    public boolean hasUserId();

    /**
     * Checks to see if this profile has a user name.
     *
     * @return true if it does, false otherwise
     */
    public boolean hasUsername();

    /**
     * Checks to see if this profile has a displayname.
     *
     * @return true if it does, false otherwise
     */
    public boolean hasDisplayName();

    /**
     * Checks to see if this profile is complete, this will return {@code true}
     * if all of {@link #hasUserId()}, {@link #hasUsername()} and
     * {@link #hasDisplayName()} return {@code true}.
     *
     * @return true if this profile is complete, false otherwise
     */
    public boolean isComplete();
}
