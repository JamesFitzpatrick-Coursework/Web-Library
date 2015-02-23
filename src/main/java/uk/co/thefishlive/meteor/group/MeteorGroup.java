package uk.co.thefishlive.meteor.group;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.thefishlive.auth.assessments.Assessment;
import uk.co.thefishlive.auth.assessments.AssessmentProfile;
import uk.co.thefishlive.auth.assessments.assignments.Assignment;
import uk.co.thefishlive.auth.assessments.assignments.AssignmentResult;
import uk.co.thefishlive.auth.group.Group;
import uk.co.thefishlive.auth.group.GroupProfile;
import uk.co.thefishlive.auth.group.member.GroupMemberProfile;
import uk.co.thefishlive.auth.permission.Permission;
import uk.co.thefishlive.auth.settings.Setting;
import uk.co.thefishlive.auth.user.User;
import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.http.*;
import uk.co.thefishlive.http.meteor.BasicHttpHeader;
import uk.co.thefishlive.http.meteor.MeteorHttpClient;
import uk.co.thefishlive.http.meteor.MeteorHttpRequest;
import uk.co.thefishlive.meteor.MeteorAuthHandler;
import uk.co.thefishlive.meteor.session.MeteorSession;
import uk.co.thefishlive.meteor.settings.StringSetting;
import uk.co.thefishlive.meteor.utils.SerialisationUtils;
import uk.co.thefishlive.meteor.utils.WebUtils;

public class MeteorGroup implements Group {

    private static final Gson GSON = SerialisationUtils.getGsonInstance();

    private final MeteorAuthHandler authHandler;
    private GroupProfile profile;
    private List<GroupMemberProfile> groups;

    public MeteorGroup(MeteorAuthHandler authHandler, GroupProfile profile) {
        this.authHandler = authHandler;
        this.profile = profile;
    }

    @Override
    public List<GroupMemberProfile> getUsers() {
        if (groups == null) {
            groups = new ArrayList<>();

            try {
                HttpClient client = MeteorHttpClient.getInstance();

                List<HttpHeader> headers = new ArrayList<>();
                headers.add(new BasicHttpHeader("X-Client", this.authHandler.getClientId().toString()));
                headers.addAll(this.authHandler.getAuthHeaders());

                HttpRequest request = new MeteorHttpRequest(RequestType.GET, headers);
                HttpResponse response = client.sendRequest(WebUtils.GROUP_USERS_ENDPOINT(getProfile()), request);

                JsonObject payload = response.getResponseBody();

                for (JsonElement element : payload.getAsJsonArray("users")) {
                    this.groups.add(GSON.fromJson(element, UserProfile.class));
                }
            } catch (IOException e) {
                Throwables.propagate(e);
            }
        }

        return ImmutableList.copyOf(groups);
    }

    @Override
    public GroupProfile getProfile() {
        return profile;
    }

    @Override
    public GroupProfile updateProfile(GroupProfile update) {
        try {
            HttpClient client = MeteorHttpClient.getInstance();

            JsonObject payload = new JsonObject();
            if (update.hasDisplayName()) payload.addProperty("display-name", update.getDisplayName());
            if (update.hasName()) payload.addProperty("group-name", update.getName());

            List<HttpHeader> headers = new ArrayList<>();
            headers.addAll(this.authHandler.getAuthHeaders());

            HttpRequest request = new MeteorHttpRequest(RequestType.POST, payload, headers);
            HttpResponse response = client.sendRequest(WebUtils.GROUP_LOOKUP_ENDPOINT(getProfile()), request);

            return GSON.fromJson(response.getResponseBody().get("profile"), GroupProfile.class);
        } catch (IOException e) {
            Throwables.propagate(e);
            return null;
        }
    }

    @Override
    public boolean hasPermission(Permission permission) throws IOException {
        HttpClient client = MeteorHttpClient.getInstance();

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.authHandler.getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.GET, headers);
        HttpResponse response = client.sendRequest(WebUtils.GROUP_PERMISSION_CHECK_ENDPOINT(getProfile(), permission), request);

        JsonObject payload = response.getResponseBody();
        return payload.get("present").getAsBoolean();
    }

    @Override
    public void addPermission(Permission permission) throws IOException {
        HttpClient client = MeteorHttpClient.getInstance();

        JsonObject payload = new JsonObject();
        payload.addProperty("permission", permission.getKey());

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.authHandler.getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.POST, payload, headers);
        client.sendRequest(WebUtils.GROUP_PERMISSION_EDIT_ENDPOINT(getProfile()), request);
    }

    @Override
    public void removePermission(Permission permission) throws IOException {
        HttpClient client = MeteorHttpClient.getInstance();

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.authHandler.getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.DELETE, headers);
        client.sendRequest(WebUtils.GROUP_PERMISSION_CHECK_ENDPOINT(getProfile(), permission), request);
    }

    @Override
    public List<Permission> getPermissions() {
        return null;
    }

    @Override
    public Setting<String, String> getSetting(String key) throws IOException {
        HttpClient client = MeteorHttpClient.getInstance();

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.authHandler.getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.GET, headers);
        HttpResponse response = client.sendRequest(WebUtils.GROUP_SETTING_LOOKUP_ENDPOINT(getProfile(), key), request);

        JsonObject payload = response.getResponseBody();
        return GSON.fromJson(payload.get("setting"), StringSetting.class);
    }

    @Override
    public void setSetting(Setting<String, ?> setting) throws IOException {
        HttpClient client = MeteorHttpClient.getInstance();

        JsonObject payload = new JsonObject();
        payload.add("setting", GSON.toJsonTree(setting));

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.authHandler.getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.POST, payload, headers);
        client.sendRequest(WebUtils.GROUP_SETTING_EDIT_ENDPOINT(getProfile()), request);
    }

    @Override
    public void deleteSetting(String key) throws IOException {
        HttpClient client = MeteorHttpClient.getInstance();

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.authHandler.getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.DELETE, headers);
        client.sendRequest(WebUtils.GROUP_SETTING_LOOKUP_ENDPOINT(getProfile(), key), request);
    }

    @Override
    public List<Assignment> getOutstandingAssignments() {
        return null;
    }

    @Override
    public List<Assignment> getAllAssignments() {
        return null;
    }

    @Override
    public List<Assignment> getCompletedAssignments() {
        return null;
    }

    @Override
    public void assignAssessment(Assignment assignment) {

    }

    @Override
    public AssignmentResult submitAssessment(Assignment assignment, Assessment assessment) {
        return null;
    }
}
