package uk.co.thefishlive.meteor.utils;

import com.google.gson.Gson;

import uk.co.thefishlive.meteor.json.GsonInstance;

public class SerialisationUtils {

    public static Gson getGsonInstance() {
        return GsonInstance.get();
    }
}
