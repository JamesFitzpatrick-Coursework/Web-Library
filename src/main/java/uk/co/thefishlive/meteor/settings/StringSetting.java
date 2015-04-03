package uk.co.thefishlive.meteor.settings;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import uk.co.thefishlive.auth.settings.Setting;

public class StringSetting implements Setting<String, String> {

    private final String key;
    private final String value;

    public StringSetting(String key, String value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);

        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(this.getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StringSetting that = (StringSetting) o;

        if (!key.equals(that.key)) {
            return false;
        }
        if (!value.equals(that.value)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "StringSetting{" +
               "key='" + key + '\'' +
               ", value='" + value + '\'' +
               '}';
    }
}
