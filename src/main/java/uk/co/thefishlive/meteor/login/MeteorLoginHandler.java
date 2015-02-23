package uk.co.thefishlive.meteor.login;

import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.auth.data.Token;
import uk.co.thefishlive.auth.login.LoginHandler;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.http.HttpClient;
import uk.co.thefishlive.http.HttpHeader;
import uk.co.thefishlive.http.HttpRequest;
import uk.co.thefishlive.http.HttpResponse;
import uk.co.thefishlive.http.RequestType;
import uk.co.thefishlive.http.meteor.MeteorHttpClient;
import uk.co.thefishlive.http.meteor.MeteorHttpRequest;
import uk.co.thefishlive.meteor.MeteorAuthHandler;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.user.MeteorUserProfile;
import uk.co.thefishlive.auth.login.exception.LoginException;
import uk.co.thefishlive.meteor.session.MeteorSession;

import static uk.co.thefishlive.meteor.utils.WebUtils.*;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MeteorLoginHandler implements LoginHandler {

    private final MeteorAuthHandler authHandler;

    public MeteorLoginHandler(MeteorAuthHandler authHandler) {
        this.authHandler = authHandler;
    }

    @Override
    public Session login(UserProfile profile, char[] password) throws IOException, LoginException {
        HttpClient client = MeteorHttpClient.getInstance();

        // Build handshake request
        JsonObject handshakePayload = new JsonObject();
        handshakePayload.addProperty("user", profile.hasId() ? profile.getId().toString() : profile.getName());

        List<HttpHeader> handshakeHeaders = new ArrayList<>();
        handshakeHeaders.addAll(authHandler.getAuthHeaders());

        HttpRequest handshake = new MeteorHttpRequest(RequestType.POST, handshakePayload, handshakeHeaders);

        // Send handshake request
        HttpResponse handshakeResponse = client.sendRequest(HANDSHAKE_ENDPOINT, handshake);

        if (!handshakeResponse.isSuccessful()) {
            throw new LoginException(handshakeResponse.getResponseBody().get("error").getAsString());
        }

        // Retrieve the request token from the handshake response
        Token clientid = AuthToken.decode(handshakeResponse.getResponseBody().get("client-id").getAsString());
        Token requestToken = AuthToken.decode(handshakeResponse.getResponseBody().getAsJsonObject("request-token").get("token").getAsString());
        profile = this.authHandler.getGsonInstance().fromJson(handshakeResponse.getResponseBody().get("user"), MeteorUserProfile.class);

        // Check for client id validity
        if (!clientid.equals(this.authHandler.getClientId())) {
            throw new LoginException("Invalid client id returned by server");
        }

        // Build login request from handshake
        JsonObject loginPayload = new JsonObject();
        loginPayload.addProperty("user", profile.getId().toString());
        loginPayload.addProperty("password", new String(password));
        loginPayload.addProperty("request-token", requestToken.toString());

        List<HttpHeader> loginHeaders = new ArrayList<>();
        loginHeaders.addAll(authHandler.getAuthHeaders());

        HttpRequest login = new MeteorHttpRequest(RequestType.POST, loginPayload, loginHeaders);

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
        if (!clientid.equals(this.authHandler.getClientId())) {
            throw new LoginException("Invalid client id returned by server");
        }

        // Create the session object
        return new MeteorSession(this.authHandler.getSessionHandler(),
                                            profile,
                                            accessToken,
                                            refreshToken);
    }

}
