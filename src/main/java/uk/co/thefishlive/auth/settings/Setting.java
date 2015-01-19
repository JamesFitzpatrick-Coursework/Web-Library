package uk.co.thefishlive.auth.settings;

import com.google.gson.JsonElement;

/**
 * Represents a setting stored on a remote server about an object
 */
public interface Setting<K, V> {

    /**
     * Get this settings key.
     *
     * @return this settings key
     */
    public K getKey();

    /**
     * Get the value of this setting.
     *
     * @return the value of this setting as a string
     */
    public V getValue();

    /**
     * Convert this setting to a serializable json value.
     *
     * @return the json element to send to the server
     */
    public JsonElement toJson();

}
