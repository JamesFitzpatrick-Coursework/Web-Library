package uk.co.thefishlive.meteor.session;

import uk.co.thefishlive.auth.data.Profile;
import uk.co.thefishlive.auth.data.Token;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.auth.session.SessionHandler;
import uk.co.thefishlive.meteor.session.exception.SessionException;

import java.io.IOException;

/**
 * Created by James on 21/11/2014.
 */
public class MeteorSession implements Session {

    private SessionHandler handler;
    private Profile profile;
    private Token access;
    private Token refresh;

    public MeteorSession(SessionHandler handler, Profile profile, Token access, Token refresh) {
        this.handler = handler;
        this.profile = profile;
        this.access = access;
        this.refresh = refresh;
    }

    @Override
    public Profile getOwner() {
        return this.profile;
    }

    @Override
    public SessionHandler getHandler() {
        return this.handler;
    }

    @Override
    public Session refreshSession() throws IOException, SessionException {
        return getHandler().refresh(this);
    }

    @Override
    public boolean invalidate() throws IOException, SessionException {
        return getHandler().invalidate(this);
    }

    @Override
    public boolean isValid() throws IOException, SessionException {
        return getHandler().isValid(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeteorSession that = (MeteorSession) o;

        if (access != null ? !access.equals(that.access) : that.access != null) return false;
        if (profile != null ? !profile.equals(that.profile) : that.profile != null) return false;
        return !(refresh != null ? !refresh.equals(that.refresh) : that.refresh != null);
    }

    @Override
    public int hashCode() {
        int result = profile != null ? profile.hashCode() : 0;
        result = 31 * result + (access != null ? access.hashCode() : 0);
        result = 31 * result + (refresh != null ? refresh.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MeteorSession{" +
                "profile=" + profile +
                ", access=" + access +
                ", refresh=" + refresh +
                '}';
    }

    public Token getRefreshToken() {
        return refresh;
    }

    public Token getAccessToken() {
        return access;
    }
}
