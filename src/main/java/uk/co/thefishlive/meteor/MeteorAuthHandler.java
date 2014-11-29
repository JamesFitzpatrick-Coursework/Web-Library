package uk.co.thefishlive.meteor;

import uk.co.thefishlive.auth.AuthHandler;
import uk.co.thefishlive.auth.data.Token;
import uk.co.thefishlive.auth.login.LoginHandler;
import uk.co.thefishlive.auth.session.SessionHandler;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.data.AuthToken.AuthTokenHandler;
import uk.co.thefishlive.meteor.login.MeteorLoginHandler;
import uk.co.thefishlive.meteor.session.MeteorSessionHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by James on 14/10/2014.
 */
public class MeteorAuthHandler implements AuthHandler {

    protected final Gson gson;
    private LoginHandler loginHandler;
    private SessionHandler sessionHandler;

    public MeteorAuthHandler() {
        this.gson = new GsonBuilder()
                        .registerTypeAdapter(Token.class, new AuthTokenHandler())
                        .create();

        Token clientid = AuthToken.generateRandom("client-id");
        this.loginHandler = new MeteorLoginHandler(this, clientid);
        this.sessionHandler = new MeteorSessionHandler(this, clientid);
    }

    public Gson getGsonInstance() {
        return this.gson;
    }

    @Override
    public LoginHandler getLoginHandler() {
        return this.loginHandler;
    }

    @Override
    public SessionHandler getSessionHandler() {
        return this.sessionHandler;
    }
}
