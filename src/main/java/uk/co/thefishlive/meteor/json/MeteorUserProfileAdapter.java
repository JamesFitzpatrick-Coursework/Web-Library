package uk.co.thefishlive.meteor.json;

import com.google.gson.*;
import java.lang.reflect.Type;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.user.MeteorUserProfile;

public class MeteorUserProfileAdapter implements JsonSerializer<MeteorUserProfile>, JsonDeserializer<MeteorUserProfile> {
    @Override
    public MeteorUserProfile deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();

        if (!(object.has("user-id") && object.has("user-name") && object.has("display-name"))) {
            throw new JsonParseException("Json Object provided is not a login profile.\n(" + jsonElement + ")");
        }

        return new MeteorUserProfile(AuthToken.decode(object.get("user-id").getAsString()), object.get("user-name").getAsString(), object.get("display-name").getAsString());
    }

    @Override
    public JsonElement serialize(MeteorUserProfile loginProfile, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.addProperty("user-id", loginProfile.getId().toString());
        object.addProperty("user-name", loginProfile.getName());
        object.addProperty("display-name", loginProfile.getDisplayName());
        return object;
    }
}
