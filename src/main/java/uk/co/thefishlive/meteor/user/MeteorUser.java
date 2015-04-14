package uk.co.thefishlive.meteor.user;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uk.co.thefishlive.auth.assessments.Assessment;
import uk.co.thefishlive.auth.assessments.assignments.Assignment;
import uk.co.thefishlive.auth.assessments.assignments.AssignmentResult;
import uk.co.thefishlive.auth.assessments.assignments.QuestionScore;
import uk.co.thefishlive.auth.group.GroupProfile;
import uk.co.thefishlive.auth.permission.Permission;
import uk.co.thefishlive.auth.settings.Setting;
import uk.co.thefishlive.auth.user.User;
import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.http.HttpClient;
import uk.co.thefishlive.http.HttpHeader;
import uk.co.thefishlive.http.HttpRequest;
import uk.co.thefishlive.http.HttpResponse;
import uk.co.thefishlive.http.RequestType;
import uk.co.thefishlive.http.meteor.MeteorHttpClient;
import uk.co.thefishlive.http.meteor.MeteorHttpRequest;
import uk.co.thefishlive.meteor.MeteorAuthHandler;
import uk.co.thefishlive.meteor.assessments.MeteorAssessmentManager;
import uk.co.thefishlive.meteor.assessments.assignments.MeteorAssignment;
import uk.co.thefishlive.meteor.assessments.assignments.MeteorAssignmentResult;
import uk.co.thefishlive.meteor.assessments.assignments.MeteorQuestionScore;
import uk.co.thefishlive.meteor.json.GsonInstance;
import uk.co.thefishlive.meteor.json.annotations.Internal;
import uk.co.thefishlive.meteor.settings.StringSetting;
import uk.co.thefishlive.meteor.utils.WebUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MeteorUser implements User {

    private static final Gson GSON = GsonInstance.get();

    @Internal
    private final MeteorAuthHandler authHandler;
    private UserProfile profile;
    private List<GroupProfile> groups;

    public MeteorUser(MeteorAuthHandler authHandler, UserProfile profile) {
        this.authHandler = authHandler;
        this.profile = profile;
    }

    @Override
    public List<GroupProfile> getGroups() {
        if (groups == null) {
            groups = new ArrayList<>();

            try {
                HttpClient client = MeteorHttpClient.getInstance();

                List<HttpHeader> headers = new ArrayList<>();
                headers.addAll(this.authHandler.getAuthHeaders());

                HttpRequest request = new MeteorHttpRequest(RequestType.GET, headers);
                HttpResponse response = client.sendRequest(WebUtils.USER_GROUPS_ENDPOINT(getProfile()), request);

                JsonObject payload = response.getResponseBody();

                for (JsonElement element : payload.getAsJsonArray("groups")) {
                    this.groups.add(GSON.fromJson(element, GroupProfile.class));
                }
            } catch (IOException e) {
                Throwables.propagate(e);
            }
        }

        return ImmutableList.copyOf(groups);
    }

    @Override
    public void addGroup(GroupProfile group) {
        try {
            HttpClient client = MeteorHttpClient.getInstance();

            JsonObject payload = new JsonObject();
            payload.addProperty("group", group.getIdentifier());

            List<HttpHeader> headers = new ArrayList<>();
            headers.addAll(this.authHandler.getAuthHeaders());

            HttpRequest request = new MeteorHttpRequest(RequestType.POST, payload, headers);
            client.sendRequest(WebUtils.USER_GROUPS_ADD_ENDPOINT(getProfile()), request);
        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }

    @Override
    public UserProfile updateProfile(UserProfile profile) {
        try {
            HttpClient client = MeteorHttpClient.getInstance();

            JsonObject payload = new JsonObject();
            if (profile.hasDisplayName()) {
                payload.addProperty("display-name", profile.getDisplayName());
            }
            if (profile.hasName()) {
                payload.addProperty("user-name", profile.getName());
            }

            List<HttpHeader> headers = new ArrayList<>();
            headers.addAll(this.authHandler.getAuthHeaders());

            HttpRequest request = new MeteorHttpRequest(RequestType.POST, payload, headers);
            HttpResponse response = client.sendRequest(WebUtils.USER_LOOKUP_ENDPOINT(getProfile()), request);

            return GSON.fromJson(response.getResponseBody().get("profile"), UserProfile.class);
        } catch (IOException e) {
            Throwables.propagate(e);
            return null;
        }
    }

    @Override
    public UserProfile getProfile() {
        return profile;
    }

    @Override
    public boolean hasPermission(Permission permission) throws IOException {
        HttpClient client = MeteorHttpClient.getInstance();

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.authHandler.getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.GET, headers);
        HttpResponse response = client.sendRequest(WebUtils.USER_PERMISSION_CHECK_ENDPOINT(getProfile(), permission), request);

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
        client.sendRequest(WebUtils.USER_PERMISSION_EDIT_ENDPOINT(getProfile()), request);
    }

    @Override
    public void removePermission(Permission permission) throws IOException {
        HttpClient client = MeteorHttpClient.getInstance();

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.authHandler.getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.DELETE, headers);
        client.sendRequest(WebUtils.USER_PERMISSION_CHECK_ENDPOINT(getProfile(), permission), request);
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
        HttpResponse response = client.sendRequest(WebUtils.USER_SETTING_LOOKUP_ENDPOINT(getProfile(), key), request);

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
        client.sendRequest(WebUtils.USER_SETTING_EDIT_ENDPOINT(getProfile()), request);
    }

    @Override
    public void deleteSetting(String key) throws IOException {
        HttpClient client = MeteorHttpClient.getInstance();

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.authHandler.getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.DELETE, headers);
        client.sendRequest(WebUtils.USER_SETTING_LOOKUP_ENDPOINT(getProfile(), key), request);
    }

    @Override
    public List<Assignment> getOutstandingAssignments() {
        List<Assignment> assignments = Lists.newArrayList();

        try {
            HttpClient client = MeteorHttpClient.getInstance();

            List<HttpHeader> headers = new ArrayList<>();
            headers.addAll(this.authHandler.getAuthHeaders());

            HttpRequest request = new MeteorHttpRequest(RequestType.GET, headers);
            HttpResponse response = client.sendRequest(WebUtils.USER_ASSIGNMENT_LOOKUP_OUTSTANDING(getProfile()), request);

            JsonObject payload = response.getResponseBody();

            for (JsonElement element : payload.getAsJsonArray("assignments")) {
                Assignment assignment = GSON.fromJson(element.getAsJsonObject().getAsJsonObject("assignment"), Assignment.class);
                ((MeteorAssignment) assignment).setHandler((MeteorAssessmentManager) authHandler.getAssessmentManager());
                assignments.add(assignment);
            }
        } catch (IOException e) {
            Throwables.propagate(e);
        }

        return assignments;
    }

    @Override
    public List<Assignment> getAllAssignments() {
        List<Assignment> assignments = Lists.newArrayList();

        try {
            HttpClient client = MeteorHttpClient.getInstance();

            List<HttpHeader> headers = new ArrayList<>();
            headers.addAll(this.authHandler.getAuthHeaders());

            HttpRequest request = new MeteorHttpRequest(RequestType.GET, headers);
            HttpResponse response = client.sendRequest(WebUtils.USER_ASSIGNMENT_LOOKUP_ALL(getProfile()), request);

            JsonObject payload = response.getResponseBody();

            for (JsonElement element : payload.getAsJsonArray("assignments")) {
                Assignment assignment = GSON.fromJson(element, Assignment.class);
                ((MeteorAssignment) assignment).setHandler((MeteorAssessmentManager) authHandler.getAssessmentManager());
                assignments.add(assignment);
            }
        } catch (IOException e) {
            Throwables.propagate(e);
        }

        return assignments;
    }

    @Override
    public List<Assignment> getCompletedAssignments() {
        List<Assignment> assignments = Lists.newArrayList();

        try {
            HttpClient client = MeteorHttpClient.getInstance();

            List<HttpHeader> headers = new ArrayList<>();
            headers.addAll(this.authHandler.getAuthHeaders());

            HttpRequest request = new MeteorHttpRequest(RequestType.GET, headers);
            HttpResponse response = client.sendRequest(WebUtils.USER_ASSIGNMENT_LOOKUP_COMPLETED(getProfile()), request);

            JsonObject payload = response.getResponseBody();

            for (JsonElement element : payload.getAsJsonArray("assignments")) {
                Assignment assignment = GSON.fromJson(element, Assignment.class);
                ((MeteorAssignment) assignment).setHandler((MeteorAssessmentManager) authHandler.getAssessmentManager());
                assignments.add(assignment);
            }
        } catch (IOException e) {
            Throwables.propagate(e);
        }

        return assignments;
    }

    @Override
    public void assignAssessment(Assignment assignment) {
        try {
            HttpClient client = MeteorHttpClient.getInstance();

            List<HttpHeader> headers = new ArrayList<>();
            headers.addAll(this.authHandler.getAuthHeaders());

            JsonObject payload = new JsonObject();
            payload.addProperty("assignment", assignment.getAssessmentProfile().getId().toString());

            HttpRequest request = new MeteorHttpRequest(RequestType.POST, payload, headers);
            client.sendRequest(WebUtils.USER_ASSIGNMENT_ADD(getProfile()), request);
        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }

    @Override
    public AssignmentResult submitAssessment(Assignment assignment, Assessment assessment) {
        AssignmentResult result = null;

        try {
            HttpClient client = MeteorHttpClient.getInstance();

            List<HttpHeader> headers = new ArrayList<>();
            headers.addAll(this.authHandler.getAuthHeaders());

            JsonObject answers = new JsonObject();
            assessment.getQuestions().stream().forEach(question -> answers
                                                           .addProperty(
                                                               question.getId().toString(),
                                                               String.valueOf(question.getCurrentAnswer()))
            );

            JsonObject payload = new JsonObject();
            payload.addProperty("assignment", assignment.getAssignmentId().toString());
            payload.add("answers", answers);

            HttpRequest request = new MeteorHttpRequest(RequestType.POST, payload, headers);
            HttpResponse response = client.sendRequest(WebUtils.USER_ASSIGNMENT_COMPLETE(getProfile()), request);

            JsonObject responseBody = response.getResponseBody();
            Map<Integer, QuestionScore> scores = Maps.newHashMap();
            int i = 1;

            System.out.println(response.getResponseBody());

            for (JsonElement element : responseBody.getAsJsonArray("scores")) {
                JsonObject question = element.getAsJsonObject();

                scores.put(
                    question.get("question-number").getAsInt(),
                    new MeteorQuestionScore(
                        question.get("question-number").getAsInt(),
                        question.get("score").getAsInt(),
                        question.get("max-score").getAsInt()
                    )
                );
            }

            result = new MeteorAssignmentResult(assignment, scores);

        } catch (IOException e) {
            Throwables.propagate(e);
        }

        return result;
    }
}
