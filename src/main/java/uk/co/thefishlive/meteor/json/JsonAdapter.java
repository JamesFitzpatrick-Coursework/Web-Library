package uk.co.thefishlive.meteor.json;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public interface JsonAdapter<T> extends JsonSerializer<T>, JsonDeserializer<T> {

}
