package uk.co.thefishlive.auth.permission;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by James on 12/11/2014.
 */
public class PermissionRegistry {

    private static final Map<String, Permission> permissions = new HashMap<>();

    protected PermissionRegistry() {} // Static class

    public static void registerPermission(Permission permission) {
        Preconditions.checkNotNull(permission);
        Preconditions.checkArgument(permissions.containsKey(permission.getKey()), "Permission with key " + permission.getKey() + " is already registered.");
        permissions.put(permission.getKey(), permission);
    }

    public static Permission getPermission(String name) {
        Preconditions.checkNotNull(name);
        return permissions.get(name);
    }
}
