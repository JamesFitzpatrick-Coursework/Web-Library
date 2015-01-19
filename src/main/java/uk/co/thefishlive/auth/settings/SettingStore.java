package uk.co.thefishlive.auth.settings;

import java.io.IOException;

/**
 * Represents an object that holds settings about another object.
 */
public interface SettingStore {

    /**
     * Retrieve a the value of a specific setting held by this object.
     *
     * @param key the key of the setting to lookup
     * @return the setting requested
     */
    public Setting<String, ?> getSetting(String key) throws IOException;

    /**
     * Set a specific setting for this object.
     *
     * @param setting the wrapper for the setting to set
     */
    public void setSetting(Setting<String, ?> setting) throws IOException;

    /**
     * Delete a specific setting from this object
     *
     * @param key the setting key to delete
     */
    public void deleteSetting(String key) throws IOException;

}
