package uk.co.thefishlive.meteor.group;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uk.co.thefishlive.auth.group.Group;
import uk.co.thefishlive.auth.group.GroupManager;
import uk.co.thefishlive.auth.group.GroupProfile;
import uk.co.thefishlive.http.HttpClient;
import uk.co.thefishlive.http.HttpHeader;
import uk.co.thefishlive.http.HttpRequest;
import uk.co.thefishlive.http.HttpResponse;
import uk.co.thefishlive.http.RequestType;
import uk.co.thefishlive.http.meteor.MeteorHttpClient;
import uk.co.thefishlive.http.meteor.MeteorHttpRequest;
import uk.co.thefishlive.meteor.MeteorAuthHandler;
import uk.co.thefishlive.meteor.json.annotations.Internal;
import uk.co.thefishlive.meteor.utils.SerialisationUtils;
import uk.co.thefishlive.meteor.utils.WebUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MeteorGroupManager implements GroupManager {

    private static final Gson GSON = SerialisationUtils.getGsonInstance();

    @Internal
    private MeteorAuthHandler authHandler;

    public MeteorGroupManager(MeteorAuthHandler authHandler) {
        this.authHandler = authHandler;
    }

    @Override
    public Group getGroupProfile(GroupProfile group) throws IOException {
        HttpClient client = MeteorHttpClient.getInstance();

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.authHandler.getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.GET, headers);
        HttpResponse response = client.sendRequest(WebUtils.GROUP_LOOKUP_ENDPOINT(group), request);

        JsonObject payload = response.getResponseBody();
        return new MeteorGroup(this.authHandler, GSON.fromJson(payload.get("profile"), MeteorGroupProfile.class));
    }

    @Override
    public GroupProfile createGroup(GroupProfile profile) throws IOException {
        HttpClient client = MeteorHttpClient.getInstance();

        JsonObject payload = new JsonObject();
        payload.addProperty("group-name", profile.getName());

        if (profile.hasDisplayName()) {
            payload.addProperty("display-name", profile.getDisplayName());
        }

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.authHandler.getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.POST, payload, headers);
        HttpResponse response = client.sendRequest(WebUtils.GROUP_CREATE_ENDPOINT, request);

        JsonObject body = response.getResponseBody();
        return GSON.fromJson(body.get("group"), GroupProfile.class);
    }

    @Override
    public boolean deleteGroup(GroupProfile profile) throws IOException {
        HttpClient client = MeteorHttpClient.getInstance();

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.authHandler.getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.DELETE, headers);
        client.sendRequest(WebUtils.GROUP_DELETE_ENDPOINT(profile), request);
        return true;
    }

    @Override
    public List<GroupProfile> getGroups() throws IOException {
        HttpClient client = MeteorHttpClient.getInstance();

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.authHandler.getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.GET, headers);
        HttpResponse response = client.sendRequest(WebUtils.GROUPS_ENDPOINT, request);

        JsonObject body = response.getResponseBody();
        List<GroupProfile> profiles = new ArrayList<>();

        for (JsonElement element : body.getAsJsonArray("groups")) {
            profiles.add(GSON.fromJson(element, GroupProfile.class));
        }

        return profiles;
    }
}
