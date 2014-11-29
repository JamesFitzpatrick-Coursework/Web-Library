package uk.co.thefishlive.auth.user;

import uk.co.thefishlive.auth.data.Profile;

/**
 * Created by James on 12/11/2014.
 */
public interface User {

    public Profile getUserProfile();

    public Group getPrimaryGroup();

    public boolean hasPermission(String permission);

}
