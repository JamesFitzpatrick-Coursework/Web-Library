package uk.co.thefishlive.auth.permission;

import java.io.IOException;
import java.util.List;

/**
 * Represents an object that can hold permissions.
 */
public interface Permissible {

    /**
     * Check to see if this object has a specified permission.
     *
     * @return true if the object does, false otherwise
     */
    public boolean hasPermission(Permission permission) throws IOException;

    /**
     * Add a specified permission to this permissible object.
     *
     * @param permission
     *     the permission to add
     */
    public void addPermission(Permission permission) throws IOException;

    /**
     * Remove a specified permission from this permissible object.
     *
     * @param permission
     *     the permission to remove
     */
    public void removePermission(Permission permission) throws IOException;

    /**
     * Get a list of the permissions for this permissible object.
     *
     * @return a immutable list of the permissions for this object
     */
    public List<Permission> getPermissions();

}
