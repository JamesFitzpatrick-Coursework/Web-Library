package uk.co.thefishlive.auth.group;

public interface Group {

    public String getName();

    public boolean hasPermission(String permission);

}
