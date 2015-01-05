package uk.co.thefishlive.auth.user;

import uk.co.thefishlive.auth.group.Group;

import java.util.List;

public interface User {

    public UserProfile getUserProfile();

    public Group getPrimaryGroup();

    public List<Group> getGroups();

    public boolean hasPermission(String permission);

}
