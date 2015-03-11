package uk.co.thefishlive.meteor.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import uk.co.thefishlive.auth.AuthHandler;
import uk.co.thefishlive.auth.data.Token;
import uk.co.thefishlive.auth.user.User;
import uk.co.thefishlive.auth.user.UserManager;
import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.http.*;
import uk.co.thefishlive.http.meteor.BasicHttpHeader;
import uk.co.thefishlive.http.meteor.MeteorHttpClient;
import uk.co.thefishlive.http.meteor.MeteorHttpRequest;
import uk.co.thefishlive.meteor.MeteorAuthHandler;
import uk.co.thefishlive.meteor.json.annotations.Internal;
import uk.co.thefishlive.meteor.session.MeteorSession;
import uk.co.thefishlive.meteor.utils.SerialisationUtils;
import uk.co.thefishlive.meteor.utils.WebUtils;

public class MeteorUserManager implements UserManager {

    private static final Gson GSON = SerialisationUtils.getGsonInstance();

    @Internal
    private MeteorAuthHandler authHandler;

    public MeteorUserManager(MeteorAuthHandler authHandler) {
        this.authHandler = authHandler;
    }

    @Override
    public User getUserProfile(UserProfile user) throws IOException {
        HttpClient client = MeteorHttpClient.getInstance();

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.authHandler.getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.GET, headers);
        HttpResponse response = client.sendRequest(WebUtils.USER_LOOKUP_ENDPOINT(user), request);

        JsonObject payload = response.getResponseBody();
        return new MeteorUser(this.authHandler, GSON.fromJson(payload.get("profile"), MeteorUserProfile.class));
    }

    @Override
    public UserProfile createUser(UserProfile profile, char[] password) throws IOException {
        HttpClient client = MeteorHttpClient.getInstance();

        JsonObject payload = new JsonObject();
        payload.addProperty("username", profile.getName());
        payload.addProperty("password", new String(password));

        if (profile.hasDisplayName()) {
            payload.addProperty("display-name", profile.getDisplayName());
        }

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.authHandler.getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.POST, payload, headers);
        HttpResponse response = client.sendRequest(WebUtils.USER_CREATE_ENDPOINT, request);

        JsonObject body = response.getResponseBody();
        return GSON.fromJson(body.get("user"), MeteorUserProfile.class);
    }

    @Override
    public boolean deleteUser(UserProfile profile) throws IOException {
        HttpClient client = MeteorHttpClient.getInstance();

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.authHandler.getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.DELETE, headers);
        HttpResponse response = client.sendRequest(WebUtils.USER_DELETE_ENDPOINT(profile), request);
        return true;
    }

    @Override
    public List<UserProfile> getUsers() throws IOException {
        HttpClient client = MeteorHttpClient.getInstance();

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.authHandler.getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.GET, headers);
        HttpResponse response = client.sendRequest(WebUtils.USERS_ENDPOINT, request);

        JsonObject body = response.getResponseBody();
        List<UserProfile> profiles = new ArrayList<>();

        for (JsonElement element : body.getAsJsonArray("users")) {
            profiles.add(GSON.fromJson(element, MeteorUserProfile.class));
        }

        return profiles;
    }

    public AuthHandler getAuthHandler() {
        return this.authHandler;
    }
}
