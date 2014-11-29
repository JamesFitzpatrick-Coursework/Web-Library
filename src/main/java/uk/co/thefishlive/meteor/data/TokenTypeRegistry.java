package uk.co.thefishlive.meteor.data;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by James on 15/10/2014.
 */
public class TokenTypeRegistry {

    private static final Map<Integer, String> typesById = new HashMap<>();
    private static final Map<String, Integer> typesByName = new HashMap<>();

    static {
        registerType(0xAA, "client-id");
        registerType(0xAB, "request-token");
        registerType(0xAC, "access-token");
        registerType(0xAD, "refresh-token");
        registerType(0xAE, "user-id");
        registerType(0xAF, "RESERVED");
    }

    public static void registerType(int code, String name) {
        Preconditions.checkArgument(!typesById.containsKey(code),
                String.format("Cannot redeclare token type code (attempted to register %d (%s), but its already registered as %s)", code, name, typesById.get(code)));

        typesById.put(code, name);
        typesByName.put(name, code);
    }

    public static String getType(int code) {
        return typesById.get(code);
    }

    public static int getType(String name) {
        return typesByName.get(name);
    }
}
