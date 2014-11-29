package uk.co.thefishlive.auth.user;

/**
 * Created by James on 12/11/2014.
 */
public interface Group {

    public String getName();

    public boolean hasPermission(String permission);

}
