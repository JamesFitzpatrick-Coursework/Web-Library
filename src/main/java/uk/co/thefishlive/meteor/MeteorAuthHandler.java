package uk.co.thefishlive.meteor;

import com.google.common.collect.ImmutableList;
import com.google.common.net.HttpHeaders;
import java.util.ArrayList;
import java.util.List;
import uk.co.thefishlive.auth.AuthHandler;
import uk.co.thefishlive.auth.data.Token;
import uk.co.thefishlive.auth.group.GroupManager;
import uk.co.thefishlive.auth.login.LoginHandler;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.auth.session.SessionHandler;
import uk.co.thefishlive.auth.user.UserManager;
import uk.co.thefishlive.http.HttpHeader;
import uk.co.thefishlive.http.meteor.BasicHttpHeader;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.data.AuthToken.AuthTokenHandler;
import uk.co.thefishlive.meteor.group.MeteorGroupManager;
import uk.co.thefishlive.meteor.login.MeteorLoginHandler;
import uk.co.thefishlive.meteor.session.MeteorSession;
import uk.co.thefishlive.meteor.session.MeteorSessionHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.Proxy;
import uk.co.thefishlive.meteor.user.MeteorUserManager;
import uk.co.thefishlive.meteor.utils.SerialisationUtils;

public class MeteorAuthHandler implements AuthHandler {

    private final Gson gson;
    private final Proxy proxy;
    private LoginHandler loginHandler;
    private SessionHandler sessionHandler;
    private UserManager userManager;
    private GroupManager groupManager;
    private Session activeSession;
    private Token clientId;

    public MeteorAuthHandler(Proxy proxy) {
        this(proxy, AuthToken.generateRandom("client-id"));
    }

    public MeteorAuthHandler(Proxy proxy, Token clientId) {
        this.gson = SerialisationUtils.getGsonInstance();
        this.proxy = proxy;

        this.clientId = clientId;
        this.loginHandler = new MeteorLoginHandler(this, clientId);
        this.sessionHandler = new MeteorSessionHandler(this, clientId);
        this.userManager = new MeteorUserManager(this, clientId);
        this.groupManager = new MeteorGroupManager(this, clientId);
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

    @Override
    public List<HttpHeader> getAuthHeaders() {
        List<HttpHeader> headers = new ArrayList<>();

        if (this.getActiveSession() != null) {
            headers.add(new BasicHttpHeader("X-Authentication-User", this.getActiveSession().getProfile().getIdentifier()));
            headers.add(new BasicHttpHeader("X-Authentication-Token", ((MeteorSession) this.getActiveSession()).getAccessToken().toString()));
        }

        return ImmutableList.copyOf(headers);
    }

    @Override
    public Token getClientId() {
        return this.clientId;
    }
}
