package uk.co.thefishlive.meteor.session;

import uk.co.thefishlive.auth.data.Token;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.auth.session.SessionHandler;
import uk.co.thefishlive.http.HttpClient;
import uk.co.thefishlive.http.HttpRequest;
import uk.co.thefishlive.http.HttpResponse;
import uk.co.thefishlive.http.RequestType;
import uk.co.thefishlive.http.meteor.MeteorHttpClient;
import uk.co.thefishlive.http.meteor.MeteorHttpRequest;
import uk.co.thefishlive.meteor.MeteorAuthHandler;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.session.exception.SessionException;
import uk.co.thefishlive.meteor.utils.WebUtils;

import com.google.gson.JsonObject;

import java.io.IOException;

public class MeteorSessionHandler implements SessionHandler {

    private final MeteorAuthHandler authHandler;
    private final Token clientid;

    public MeteorSessionHandler(MeteorAuthHandler authHandler, Token clientid) {
        this.authHandler = authHandler;
        this.clientid = clientid;
    }

    @Override
    public boolean isValid(Session session) throws IOException, SessionException {
        if (!(session instanceof MeteorSession)) throw new SessionException("Cannot refresh session, not a meteor session.");
        MeteorSession meteorSession = (MeteorSession) session;

        HttpClient client = new MeteorHttpClient();

        JsonObject refreshPayload = new JsonObject();
        refreshPayload.addProperty("client-id", clientid.toString());
        refreshPayload.addProperty("user-id", session.getOwner().getUserId().toString());
        refreshPayload.addProperty("token", meteorSession.getRefreshToken().toString());
        HttpRequest request = new MeteorHttpRequest(RequestType.POST, refreshPayload);

        HttpResponse response = client.sendRequest(WebUtils.VALIDATE_ENDPOINT, request);
        return response.isSuccessful();
    }

    @Override
    public boolean invalidate(Session session) throws SessionException, IOException {
        if (!(session instanceof MeteorSession)) throw new SessionException("Cannot refresh session, not a meteor session.");
        MeteorSession meteorSession = (MeteorSession) session;

        HttpClient client = new MeteorHttpClient();

        JsonObject refreshPayload = new JsonObject();
        refreshPayload.addProperty("client-id", clientid.toString());
        refreshPayload.addProperty("user-id", session.getOwner().getUserId().toString());
        refreshPayload.addProperty("token", meteorSession.getRefreshToken().toString());
        HttpRequest request = new MeteorHttpRequest(RequestType.POST, refreshPayload);

        HttpResponse response = client.sendRequest(WebUtils.INVALIDATE_ENDPOINT, request);

        if (!response.isSuccessful()) {
            throw new SessionException(response.getResponseBody().get("error").getAsString());
        }

        return true;
    }

    @Override
    public Session refresh(Session session) throws IOException, SessionException {
        if (!(session instanceof MeteorSession)) throw new SessionException("Cannot refresh session, not a meteor session.");
        MeteorSession meteorSession = (MeteorSession) session;

        HttpClient client = new MeteorHttpClient();

        JsonObject refreshPayload = new JsonObject();
        refreshPayload.addProperty("client-id", clientid.toString());
        refreshPayload.addProperty("user-id", session.getOwner().getUserId().toString());
        refreshPayload.addProperty("refresh-token", meteorSession.getRefreshToken().toString());
        HttpRequest request = new MeteorHttpRequest(RequestType.POST, refreshPayload);

        HttpResponse response = client.sendRequest(WebUtils.REFRESH_ENDPOINT, request);

        if (!response.isSuccessful()) {
            throw new SessionException(response.getResponseBody().get("error").getAsString());
        }

        Token access = AuthToken.decode(response.getResponseBody().getAsJsonObject("access-token").get("token").getAsString());

        return new MeteorSession(this, session.getOwner(), access, meteorSession.getRefreshToken());
    }

}
