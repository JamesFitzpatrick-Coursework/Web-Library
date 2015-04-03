package uk.co.thefishlive.meteor.json.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import uk.co.thefishlive.meteor.json.JsonAdapter;

import java.lang.reflect.Type;

/**
 *
 */
public class ImplementationClassRedirectAdapter implements JsonAdapter<Object> {

    private static final String DEFAULT_PACKAGE = "meteor";
    private static final String DEFAULT_PREFIX = "Meteor";

    private final String newPackage;
    private final String newPrefix;

    public ImplementationClassRedirectAdapter() {
        this(DEFAULT_PACKAGE, DEFAULT_PREFIX);
    }

    public ImplementationClassRedirectAdapter(String newPackage, String newPrefix) {
        this.newPackage = newPackage;
        this.newPrefix = newPrefix;
    }

    @Override
    public Object deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        String implName = getImplementationClassName(type);

        try {
            return context.deserialize(json, Class.forName(implName));
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Could not find implementation type (" + implName + ")", e);
        }
    }

    @Override
    public JsonElement serialize(Object src, Type type, JsonSerializationContext context) {
        String implName = getImplementationClassName(type);

        try {
            return context.serialize(src, Class.forName(implName));
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Could not find implementation type (" + implName + ")", e);
        }
    }

    private String getImplementationClassName(Type type) {
        String classname = type.getTypeName();
        return classname.substring(0, 18) + newPackage + classname.substring(classname.indexOf('.', 19), classname.lastIndexOf('.') + 1) + newPrefix + classname.substring(classname.lastIndexOf('.') + 1);
    }
}
