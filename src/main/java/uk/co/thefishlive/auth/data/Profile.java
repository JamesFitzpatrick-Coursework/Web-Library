package uk.co.thefishlive.auth.data;

public interface Profile {

    /**
     * Get a unique string to represent this profile.
     * <p />
     * If {@link #hasId} is true
     * then this will return the profile's id (from {@link #getId}) if not it
     * will return this profile's name (from {@link #getName}).
     *
     * @return a unique string to represent this profile
     */
    public String getIdentifier();

    /**
     * Get this profile's unique identifying id.
     * <p />
     * This should be used to compare profile's against each other or store
     * user information as this id is static for the life of the user.
     *
     * @return this profiles id
     */
    public Token getId();

    /**
     * Get this profile's unique identifying name.
     *
     * @return this profiles name
     */
    public String getName();

    /**
     * Get this profile's display name.
     * <p />
     * This should not be used to compare profiles as it is not necessarily
     * unique. This should only be used to display to the client.
     *
     * @return this profile's display name
     */
    public String getDisplayName();

    /**
     * Checks to see if this profile has a identifiable id.
     *
     * @return true if it does, false otherwise
     */
    public boolean hasId();

    /**
     * Checks to see if this profile has a name.
     *
     * @return true if it does, false otherwise
     */
    public boolean hasName();

    /**
     * Checks to see if this profile has a displayname.
     *
     * @return true if it does, false otherwise
     */
    public boolean hasDisplayName();

    /**
     * Checks to see if this profile is complete with information.
     *
     * @return true if this profile is complete, false otherwise
     */
    public boolean isComplete();

}
