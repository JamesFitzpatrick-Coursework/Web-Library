package uk.co.thefishlive.meteor.login;

import uk.co.thefishlive.auth.data.Profile;
import uk.co.thefishlive.auth.data.Token;
import uk.co.thefishlive.auth.login.LoginHandler;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.http.*;
import uk.co.thefishlive.http.meteor.BasicHttpHeader;
import uk.co.thefishlive.http.meteor.MeteorHttpClient;
import uk.co.thefishlive.http.meteor.MeteorHttpRequest;
import uk.co.thefishlive.meteor.MeteorAuthHandler;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.data.LoginProfile;
import uk.co.thefishlive.meteor.login.exception.LoginException;
import uk.co.thefishlive.meteor.session.MeteorSession;

import static uk.co.thefishlive.meteor.utils.WebUtils.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MeteorLoginHandler implements LoginHandler {

    private static final Gson GSON = new GsonBuilder().create();

    private final MeteorAuthHandler authHandler;
    private final Token clientid;

    public MeteorLoginHandler(MeteorAuthHandler authHandler, Token clientid) {
        this.authHandler = authHandler;
        this.clientid = clientid;
    }

    @Override
    public Session login(Profile profile, char[] password) throws IOException, LoginException {
        HttpClient client = new MeteorHttpClient(authHandler.getProxySettings());

        // Build handshake request
        JsonObject handshakePayload = new JsonObject();
        handshakePayload.addProperty("client-id", clientid.toString());
        if (profile.hasUserId()) {
            handshakePayload.addProperty("user-id", profile.getUserId().toString());
        } else {
            handshakePayload.addProperty("display-name", profile.getDisplayName());
        }

        HttpRequest handshake = new MeteorHttpRequest(RequestType.POST, handshakePayload);

        // Send handshake request
        HttpResponse handshakeResponse = client.sendRequest(HANDSHAKE_ENDPOINT, handshake);

        if (!handshakeResponse.isSuccessful()) {
            throw new LoginException(handshakeResponse.getResponseBody().get("error").getAsString());
        }

        // Retrieve the request token from the handshake response
        Token clientid = AuthToken.decode(handshakeResponse.getResponseBody().get("client-id").getAsString());
        Token requestToken = AuthToken.decode(handshakeResponse.getResponseBody().getAsJsonObject("request-token").get("token").getAsString());
        profile = this.authHandler.getGsonInstance().fromJson(handshakeResponse.getResponseBody().get("user"), LoginProfile.class);

        // Check for client id validity
        if (!clientid.equals(this.clientid)) {
            throw new LoginException("Invalid client id returned by server");
        }

        // Build login request from handshake
        JsonObject loginPayload = new JsonObject();
        loginPayload.addProperty("client-id", clientid.toString());
        loginPayload.addProperty("user-id", profile.getUserId().toString());
        loginPayload.addProperty("password", new String(password));
        loginPayload.addProperty("request-token", requestToken.toString());
        HttpRequest login = new MeteorHttpRequest(RequestType.POST, loginPayload);

        // Send the login request
        HttpResponse loginResponse = client.sendRequest(LOGIN_ENDPOINT, login);

        if (!loginResponse.isSuccessful()) {
            throw new LoginException(loginResponse.getResponseBody().get("error").getAsString());
        }

        // Retrieve the information from request
        clientid = AuthToken.decode(loginResponse.getResponseBody().get("client-id").getAsString());
        Token accessToken = AuthToken.decode(loginResponse.getResponseBody().getAsJsonObject("access-token").get("token").getAsString());
        Token refreshToken = AuthToken.decode(loginResponse.getResponseBody().getAsJsonObject("refresh-token").get("token").getAsString());

        // Check for client id validity
        if (!clientid.equals(this.clientid)) {
            throw new LoginException("Invalid client id returned by server");
        }

        // Create the session object
        return new MeteorSession(this.authHandler.getSessionHandler(),
                                            profile,
                                            accessToken,
                                            refreshToken);
    }

}
