package uk.co.thefishlive.meteor.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import uk.co.thefishlive.auth.group.GroupProfile;
import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.meteor.user.MeteorUserProfile;
import uk.co.thefishlive.meteor.group.MeteorGroupProfile;
import uk.co.thefishlive.meteor.json.MeteorUserProfileAdapter;
import uk.co.thefishlive.meteor.json.MeteorGroupProfileAdapter;

public class SerialisationUtils {

    private static Gson GSON;

    public static Gson getGsonInstance() {
        if (GSON == null) {

            GSON = new GsonBuilder()
                    .registerTypeAdapter(UserProfile.class, new MeteorUserProfileAdapter())
                    .registerTypeAdapter(MeteorUserProfile.class, new MeteorUserProfileAdapter())
                    .registerTypeAdapter(GroupProfile.class, new MeteorGroupProfileAdapter())
                    .registerTypeAdapter(MeteorGroupProfile.class, new MeteorGroupProfileAdapter())
                    .create();

        }

        return GSON;
    }
}
