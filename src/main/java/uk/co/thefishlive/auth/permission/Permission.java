package uk.co.thefishlive.auth.permission;

/**
 * Represents a permission
 */
public interface Permission {

    /**
     * Get this permission's key.
     *
     * @return this permission's key
     */
    public String getKey();

    /**
     * Get this permissions description as defined in the
     * {@link PermissionRegistry}.
     *
     * @return a description of what this permission allows a object to do
     * @see PermissionRegistry
     */
    public String getDescription();

}
