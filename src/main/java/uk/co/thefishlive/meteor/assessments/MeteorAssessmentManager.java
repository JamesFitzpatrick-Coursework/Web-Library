package uk.co.thefishlive.meteor.assessments;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uk.co.thefishlive.auth.assessments.Assessment;
import uk.co.thefishlive.auth.assessments.AssessmentFactory;
import uk.co.thefishlive.auth.assessments.AssessmentManager;
import uk.co.thefishlive.auth.assessments.AssessmentProfile;
import uk.co.thefishlive.auth.assessments.assignments.AssignmentFactory;
import uk.co.thefishlive.auth.assessments.exception.AssessmentException;
import uk.co.thefishlive.auth.assessments.questions.Question;
import uk.co.thefishlive.http.HttpClient;
import uk.co.thefishlive.http.HttpHeader;
import uk.co.thefishlive.http.HttpRequest;
import uk.co.thefishlive.http.HttpResponse;
import uk.co.thefishlive.http.RequestType;
import uk.co.thefishlive.http.meteor.MeteorHttpClient;
import uk.co.thefishlive.http.meteor.MeteorHttpRequest;
import uk.co.thefishlive.meteor.MeteorAuthHandler;
import uk.co.thefishlive.meteor.assessments.assignments.MeteorAssignmentFactory;
import uk.co.thefishlive.meteor.json.GsonInstance;
import uk.co.thefishlive.meteor.utils.WebUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MeteorAssessmentManager implements AssessmentManager {

    private final MeteorAuthHandler authHandler;
    private final AssessmentFactory assessmentFactory;
    private final AssignmentFactory assignmentFactory;

    public MeteorAssessmentManager(MeteorAuthHandler authHandler) {
        this.authHandler = authHandler;
        this.assessmentFactory = new MeteorAssessmentFactory(this);
        this.assignmentFactory = new MeteorAssignmentFactory(this);
    }

    @Override
    public List<AssessmentProfile> getAssessments() throws IOException, AssessmentException {
        HttpClient client = MeteorHttpClient.getInstance();

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.getAuthHandler().getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.GET, headers);
        HttpResponse response = client.sendRequest(WebUtils.ASSESSMENTS_ENDPOINT, request);

        if (!response.isSuccessful()) {
            throw new AssessmentException(response.getResponseBody().get("error").getAsString());
        }

        JsonObject body = response.getResponseBody();

        List<AssessmentProfile> profiles = Lists.newArrayList();

        for (JsonElement element : body.getAsJsonArray("assessments")) {
            profiles.add(GsonInstance.get().fromJson(element, AssessmentProfile.class));
        }

        return ImmutableList.copyOf(profiles);
    }

    @Override
    public Assessment getAssessment(AssessmentProfile profile) throws IOException, AssessmentException {
        Preconditions.checkNotNull(profile);
        Preconditions.checkArgument(profile.hasId(), "Must specify id to lookup assessment");

        HttpClient client = MeteorHttpClient.getInstance();

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.getAuthHandler().getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.GET, headers);
        HttpResponse response = client.sendRequest(WebUtils.ASSESSMENT_LOOKUP_ENDPOINT(profile), request);

        if (!response.isSuccessful()) {
            throw new AssessmentException(response.getResponseBody().get("error").getAsString());
        }

        JsonObject json = response.getResponseBody().getAsJsonObject("assessment");
        MeteorAssessmentProfile assessmentProfile = GsonInstance.get().fromJson(json.get("profile"), MeteorAssessmentProfile.class);

        List<Question> questions = Lists.newArrayList();
        for (JsonElement question : json.getAsJsonArray("questions")) {
            questions.add(GsonInstance.get().fromJson(question, Question.class));
        }

        return new MeteorAssessment(this, assessmentProfile, questions);
    }

    @Override
    public boolean deleteAssessment(Assessment assessment) throws IOException, AssessmentException {
        Preconditions.checkNotNull(assessment);

        HttpClient client = MeteorHttpClient.getInstance();

        JsonObject payload = new JsonObject();
        payload.add("assessment", GsonInstance.get().toJsonTree(assessment));

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.getAuthHandler().getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.DELETE, payload, headers);
        HttpResponse response = client.sendRequest(WebUtils.ASSESSMENT_LOOKUP_ENDPOINT(assessment.getProfile()), request);

        if (!response.isSuccessful()) {
            throw new AssessmentException(response.getResponseBody().get("error").getAsString());
        }
        return true;
    }

    @Override
    public boolean updateAssessment(Assessment assessment) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public AssessmentFactory getAssessmentFactory() {
        return this.assessmentFactory;
    }

    @Override
    public AssignmentFactory getAssignmentFactory() {
        return this.assignmentFactory;
    }

    public MeteorAuthHandler getAuthHandler() {
        return this.authHandler;
    }
}
