package uk.co.thefishlive.meteor.session;

import com.google.gson.JsonObject;

import uk.co.thefishlive.auth.data.Token;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.auth.session.SessionHandler;
import uk.co.thefishlive.auth.session.exception.SessionException;
import uk.co.thefishlive.http.HttpClient;
import uk.co.thefishlive.http.HttpHeader;
import uk.co.thefishlive.http.HttpRequest;
import uk.co.thefishlive.http.HttpResponse;
import uk.co.thefishlive.http.RequestType;
import uk.co.thefishlive.http.meteor.MeteorHttpClient;
import uk.co.thefishlive.http.meteor.MeteorHttpRequest;
import uk.co.thefishlive.meteor.MeteorAuthHandler;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.json.annotations.Internal;
import uk.co.thefishlive.meteor.utils.WebUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MeteorSessionHandler implements SessionHandler {

    @Internal
    private final MeteorAuthHandler authHandler;

    public MeteorSessionHandler(MeteorAuthHandler authHandler) {
        this.authHandler = authHandler;
    }

    @Override
    public boolean isValid(Session session) throws IOException, SessionException {
        if (!(session instanceof MeteorSession)) {
            throw new SessionException("Cannot validate session, not a meteor session.");
        }
        MeteorSession meteorSession = (MeteorSession) session;

        HttpClient client = MeteorHttpClient.getInstance();

        JsonObject refreshPayload = new JsonObject();
        refreshPayload.addProperty("user", session.getProfile().getId().toString());
        refreshPayload.addProperty("token", meteorSession.getRefreshToken().toString());

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(authHandler.getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.POST, refreshPayload, headers);

        HttpResponse response = client.sendRequest(WebUtils.VALIDATE_ENDPOINT, request);
        return response.isSuccessful();
    }

    @Override
    public boolean invalidate(Session session) throws SessionException, IOException {
        if (!(session instanceof MeteorSession)) {
            throw new SessionException("Cannot invalidate session, not a meteor session.");
        }
        MeteorSession meteorSession = (MeteorSession) session;

        HttpClient client = MeteorHttpClient.getInstance();

        JsonObject refreshPayload = new JsonObject();
        refreshPayload.addProperty("user", session.getProfile().getId().toString());
        refreshPayload.addProperty("token", meteorSession.getRefreshToken().toString());

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(authHandler.getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.POST, refreshPayload, headers);
        HttpResponse response = client.sendRequest(WebUtils.INVALIDATE_ENDPOINT, request);

        if (!response.isSuccessful()) {
            throw new SessionException(response.getResponseBody().get("error").getAsString());
        }

        return true;
    }

    @Override
    public Session refresh(Session session) throws IOException, SessionException {
        if (!(session instanceof MeteorSession)) {
            throw new SessionException("Cannot refresh session, not a meteor session.");
        }
        MeteorSession meteorSession = (MeteorSession) session;

        HttpClient client = MeteorHttpClient.getInstance();

        JsonObject refreshPayload = new JsonObject();
        refreshPayload.addProperty("user", session.getProfile().getId().toString());
        refreshPayload.addProperty("refresh-token", meteorSession.getRefreshToken().toString());

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(authHandler.getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.POST, refreshPayload, headers);
        HttpResponse response = client.sendRequest(WebUtils.REFRESH_ENDPOINT, request);

        if (!response.isSuccessful()) {
            throw new SessionException(response.getResponseBody().get("error").getAsString());
        }

        Token access = AuthToken.decode(response.getResponseBody().getAsJsonObject("access-token").get("token").getAsString());

        return new MeteorSession(this, session.getProfile(), access, meteorSession.getRefreshToken());
    }

}
