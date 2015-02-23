package uk.co.thefishlive.meteor.json.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.json.JsonAdapter;

import java.lang.reflect.Type;

/**
 *
 */
public class AuthTokenAdapter implements JsonAdapter<AuthToken> {
    @Override
    public AuthToken deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (jsonElement.isJsonPrimitive()) {
            return (AuthToken) AuthToken.decode(jsonElement.getAsString());
        } else if(jsonElement.isJsonObject()) {
            return (AuthToken) AuthToken.decode(jsonElement.getAsJsonObject().get("token").getAsString());
        } else {
            throw new JsonParseException("Json provided is not a valid token");
        }
    }

    @Override
    public JsonElement serialize(AuthToken token, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(token.toString());
    }
}
