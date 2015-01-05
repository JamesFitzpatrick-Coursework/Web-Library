package uk.co.thefishlive.auth.group;

import uk.co.thefishlive.auth.data.Token;

/**
 * Represents a class that holds the basic identifying information about a
 * group.
 */
public interface GroupProfile {

    /**
     * Get this group's group id, a unique identifying token for this group.
     *
     * @return this group's group id
     */
    public Token getGroupId();

    /**
     * Get this group's groupnane, the unique identifying name for this group.
     *
     * @return this group's groupnane
     */
    public String getGroupname();

    /**
     * Get this group's displayname, a prettily formatted version of the groupname
     * to be output to the client.
     *
     * @return this group's displayname
     */
    public String getDisplayName();

    /**
     * Checks to see if this profile has a group id.
     *
     * @return true if it does, false otherwise
     */
    public boolean hasGroupId();

    /**
     * Checks to see if this profile has a group name.
     *
     * @return true if it does, false otherwise
     */
    public boolean hasGroupname();

    /**
     * Checks to see if this profile has a displayname.
     *
     * @return true if it does, false otherwise
     */
    public boolean hasDisplayName();

    /**
     * Checks to see if this profile is complete, this will return {@code true}
     * if all of {@link #hasGroupId()}, {@link #hasGroupname()} and
     * {@link #hasDisplayName()} return {@code true}.
     *
     * @return true if this profile is complete, false otherwise
     */
    public boolean isComplete();
}
