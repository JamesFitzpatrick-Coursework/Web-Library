package uk.co.thefishlive.meteor.assessments;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;

import uk.co.thefishlive.auth.assessments.Assessment;
import uk.co.thefishlive.auth.assessments.AssessmentProfile;
import uk.co.thefishlive.auth.assessments.exception.AssessmentException;
import uk.co.thefishlive.auth.assessments.questions.Question;
import uk.co.thefishlive.auth.data.Token;
import uk.co.thefishlive.http.HttpClient;
import uk.co.thefishlive.http.HttpHeader;
import uk.co.thefishlive.http.HttpRequest;
import uk.co.thefishlive.http.HttpResponse;
import uk.co.thefishlive.http.RequestType;
import uk.co.thefishlive.http.meteor.MeteorHttpClient;
import uk.co.thefishlive.http.meteor.MeteorHttpRequest;
import uk.co.thefishlive.meteor.assessments.questions.MeteorQuestion;
import uk.co.thefishlive.meteor.json.GsonInstance;
import uk.co.thefishlive.meteor.json.annotations.Internal;
import uk.co.thefishlive.meteor.utils.WebUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MeteorAssessment implements Assessment {

    @Internal
    private final MeteorAssessmentManager manager;

    private AssessmentProfile profile;
    private List<Question> questions;

    public MeteorAssessment(MeteorAssessmentManager manager, AssessmentProfile profile, List<Question> questions) {
        this.manager = manager;

        this.profile = profile;
        this.questions = questions;
    }

    @Override
    public Question getQuestion(int number) {
        return this.questions.get(number);
    }

    @Override
    public List<Question> getQuestions() {
        return ImmutableList.copyOf(questions);
    }

    @Override
    public void addQuestion(Question question) throws IOException, AssessmentException {
        HttpClient client = MeteorHttpClient.getInstance();

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.manager.getAuthHandler().getAuthHeaders());

        JsonObject payload = new JsonObject();
        payload.add("question", GsonInstance.get().toJsonTree(question, MeteorQuestion.class));
        System.out.println(payload);

        HttpRequest request = new MeteorHttpRequest(RequestType.POST, payload, headers);
        HttpResponse response = client.sendRequest(WebUtils.ASSESSMENT_ADD_QUESTION_ENDPOINT(profile), request);

        if (!response.isSuccessful()) {
            throw new AssessmentException(response.getResponseBody().get("error").getAsString());
        }
    }

    @Override
    public Question updateQuestion(Token id, Question question) throws IOException, AssessmentException {
        HttpClient client = MeteorHttpClient.getInstance();

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.manager.getAuthHandler().getAuthHeaders());

        JsonObject payload = new JsonObject();
        payload.add("question", GsonInstance.get().toJsonTree(question, MeteorQuestion.class));
        System.out.println(payload);

        HttpRequest request = new MeteorHttpRequest(RequestType.POST, payload, headers);
        HttpResponse response = client.sendRequest(WebUtils.ASSESSMENT_LOOKUP_QUESTION_ENDPOINT(profile, id), request);

        if (!response.isSuccessful()) {
            throw new AssessmentException(response.getResponseBody().get("error").getAsString());
        }

        return GsonInstance.get().fromJson(response.getResponseBody().get("question"), MeteorQuestion.class);
    }

    @Override
    public void deleteQuestion(Token id) throws IOException, AssessmentException{
        HttpClient client = MeteorHttpClient.getInstance();

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.manager.getAuthHandler().getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.DELETE, headers);
        HttpResponse response = client.sendRequest(WebUtils.ASSESSMENT_LOOKUP_QUESTION_ENDPOINT(profile, id), request);

        if (!response.isSuccessful()) {
            throw new AssessmentException(response.getResponseBody().get("error").getAsString());
        }
    }

    @Override
    public AssessmentProfile getProfile() {
        return this.profile;
    }

    @Override
    public AssessmentProfile updateProfile(AssessmentProfile update) {
        throw new UnsupportedOperationException("Cannot update assessment profile");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MeteorAssessment{");
        sb.append("manager=").append(manager);
        sb.append(", profile=").append(profile);
        sb.append(", questions=").append(questions);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MeteorAssessment that = (MeteorAssessment) o;

        if (manager != null ? !manager.equals(that.manager) : that.manager != null) {
            return false;
        }
        if (profile != null ? !profile.equals(that.profile) : that.profile != null) {
            return false;
        }
        if (questions != null ? !questions.equals(that.questions) : that.questions != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = manager != null ? manager.hashCode() : 0;
        result = 31 * result + (profile != null ? profile.hashCode() : 0);
        result = 31 * result + (questions != null ? questions.hashCode() : 0);
        return result;
    }
}
