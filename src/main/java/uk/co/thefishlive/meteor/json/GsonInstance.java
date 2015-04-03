package uk.co.thefishlive.meteor.json;

import com.google.common.base.Preconditions;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;

import uk.co.thefishlive.auth.data.Token;
import uk.co.thefishlive.meteor.assessments.questions.MeteorQuestion;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.json.adapters.AuthTokenAdapter;
import uk.co.thefishlive.meteor.json.adapters.ImplementationClassRedirectAdapter;
import uk.co.thefishlive.meteor.json.adapters.ImplementationClassRedirectAdapterFactory;
import uk.co.thefishlive.meteor.json.adapters.MeteorQuestionAdapter;
import uk.co.thefishlive.meteor.json.annotations.Internal;

import java.lang.reflect.Type;

/**
 *
 */
public class GsonInstance {

    private static Gson instance;
    private static GsonBuilder builder = new GsonBuilder();

    static {
        registerAdapter(MeteorQuestion.class, new MeteorQuestionAdapter());
        registerAdapter(AuthToken.class, new AuthTokenAdapter());

        registerAdapterFactory(new ImplementationClassRedirectAdapterFactory());
        registerAdapter(Token.class, new ImplementationClassRedirectAdapter("meteor", "Auth"));

        buildInstance();
    }

    public static Gson get() {
        if (!isBuilt()) {
            buildInstance();
        }

        return instance;
    }

    public static void buildInstance() {
        instance = builder
            .setPrettyPrinting()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .setExclusionStrategies(buildExclusionStrategy())
            .create();

        builder = null;
    }

    private static ExclusionStrategy buildExclusionStrategy() {
        return new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getAnnotation(Internal.class) != null || f.getDeclaredClass() == Gson.class;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return clazz.getAnnotation(Internal.class) != null;
            }
        };
    }

    public static void registerAdapter(Type type, JsonAdapter<?> adapter) {
        Preconditions.checkState(!isBuilt(), "Cannot register adapter after building instance");
        builder.registerTypeAdapter(type, adapter);
    }

    public static void registerAdapterFactory(TypeAdapterFactory factory) {
        Preconditions.checkState(!isBuilt(), "Cannot register adapter after building instance");
        builder.registerTypeAdapterFactory(factory);
    }

    public static boolean isBuilt() {
        return builder == null;
    }

}
