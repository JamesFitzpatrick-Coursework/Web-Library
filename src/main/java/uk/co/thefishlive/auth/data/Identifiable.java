package uk.co.thefishlive.auth.data;

public interface Identifiable<P extends Profile> {

    public P getProfile();

}
