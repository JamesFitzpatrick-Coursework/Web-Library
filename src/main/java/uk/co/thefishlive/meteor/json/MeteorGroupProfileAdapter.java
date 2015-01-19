package uk.co.thefishlive.meteor.json;

import com.google.gson.*;
import java.lang.reflect.Type;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.group.MeteorGroupProfile;

public class MeteorGroupProfileAdapter implements JsonSerializer<MeteorGroupProfile>, JsonDeserializer<MeteorGroupProfile> {
    @Override
    public MeteorGroupProfile deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();

        if (!(object.has("group-id") && object.has("group-name") && object.has("display-name"))) {
            throw new JsonParseException("Json Object provided is not a login profile");
        }

        return new MeteorGroupProfile(AuthToken.decode(object.get("group-id").getAsString()), object.get("group-name").getAsString(), object.get("display-name").getAsString());
    }

    @Override
    public JsonElement serialize(MeteorGroupProfile loginProfile, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.addProperty("group-id", loginProfile.getId().toString());
        object.addProperty("group-name", loginProfile.getName());
        object.addProperty("display-name", loginProfile.getDisplayName());
        return object;
    }
}
