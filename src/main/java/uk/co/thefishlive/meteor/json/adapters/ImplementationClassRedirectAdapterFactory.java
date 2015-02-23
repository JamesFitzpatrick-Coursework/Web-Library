package uk.co.thefishlive.meteor.json.adapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ImplementationClassRedirectAdapterFactory implements TypeAdapterFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!type.getRawType().getName().startsWith("uk.co.thefishlive.auth")) {
            return null;
        }

        String implName = getImplementationClassName(type.getType());
        try {
            return (TypeAdapter<T>) gson.getAdapter(Class.forName(implName));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not find implementation class (" + implName + ")", e);
        }
    }

    private String getImplementationClassName(Type type) {
        String classname = type.getTypeName();
        return classname.substring(0, 18) + "meteor" + classname.substring(classname.indexOf('.', 19), classname.lastIndexOf('.') + 1) + "Meteor" + classname.substring(classname.lastIndexOf('.') + 1);
    }
}
