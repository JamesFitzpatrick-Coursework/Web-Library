package uk.co.thefishlive.meteor;

import uk.co.thefishlive.auth.AuthHandler;
import uk.co.thefishlive.auth.data.Token;
import uk.co.thefishlive.auth.group.GroupManager;
import uk.co.thefishlive.auth.login.LoginHandler;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.auth.session.SessionHandler;
import uk.co.thefishlive.auth.user.UserManager;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.data.AuthToken.AuthTokenHandler;
import uk.co.thefishlive.meteor.login.MeteorLoginHandler;
import uk.co.thefishlive.meteor.session.MeteorSession;
import uk.co.thefishlive.meteor.session.MeteorSessionHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.Proxy;

public class MeteorAuthHandler implements AuthHandler {

    private final Gson gson;
    private final Proxy proxy;
    private LoginHandler loginHandler;
    private SessionHandler sessionHandler;
    private UserManager userManager;
    private GroupManager groupManager;
    private Session activeSession;

    public MeteorAuthHandler(Proxy proxy) {
        this.gson = new GsonBuilder()
                        .registerTypeAdapter(Token.class, new AuthTokenHandler())
                        .create();
        this.proxy = proxy;

        Token clientid = AuthToken.generateRandom("client-id");
        this.loginHandler = new MeteorLoginHandler(this, clientid);
        this.sessionHandler = new MeteorSessionHandler(this, clientid);
    }

    public Gson getGsonInstance() {
        return this.gson;
    }

    public Proxy getProxySettings() {
        return this.proxy;
    }

    @Override
    public LoginHandler getLoginHandler() {
        return this.loginHandler;
    }

    @Override
    public SessionHandler getSessionHandler() {
        return this.sessionHandler;
    }

    @Override
    public UserManager getUserManager() {
        return this.userManager;
    }

    @Override
    public GroupManager getGroupManager() {
        return this.groupManager;
    }

    @Override
    public void setActiveSession(Session session) {
        if (!(session instanceof MeteorSession)) throw new IllegalArgumentException("Session is not a valid Meteor session");
        this.activeSession = session;
    }

    @Override
    public Session getActiveSession() {
        return this.activeSession;
    }
}
