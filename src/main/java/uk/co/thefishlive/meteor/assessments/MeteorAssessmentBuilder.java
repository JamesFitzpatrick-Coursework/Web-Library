package uk.co.thefishlive.meteor.assessments;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import uk.co.thefishlive.auth.assessments.Assessment;
import uk.co.thefishlive.auth.assessments.AssessmentBuilder;
import uk.co.thefishlive.auth.assessments.questions.QuestionBuilder;
import uk.co.thefishlive.auth.assessments.questions.Question;
import uk.co.thefishlive.auth.assessments.questions.QuestionType;
import uk.co.thefishlive.http.HttpClient;
import uk.co.thefishlive.http.HttpHeader;
import uk.co.thefishlive.http.HttpRequest;
import uk.co.thefishlive.http.HttpResponse;
import uk.co.thefishlive.http.RequestType;
import uk.co.thefishlive.http.meteor.MeteorHttpClient;
import uk.co.thefishlive.http.meteor.MeteorHttpRequest;
import uk.co.thefishlive.auth.assessments.exception.AssessmentCreateException;
import uk.co.thefishlive.meteor.json.GsonInstance;
import uk.co.thefishlive.meteor.json.annotations.Internal;
import uk.co.thefishlive.meteor.utils.WebUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MeteorAssessmentBuilder implements AssessmentBuilder {

    @Internal
    private final MeteorAssessmentFactory factory;

    private List<Question> questions = Lists.newArrayList();
    private String name;

    public MeteorAssessmentBuilder(MeteorAssessmentFactory factory) {
        this.factory = factory;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public QuestionBuilder createQuestionBuilder(QuestionType type) {
        return factory.createQuestionBuilder(type);
    }

    @Override
    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    @Override
    public Assessment build() throws IOException, AssessmentCreateException {
        Assessment assessment = new MeteorAssessment(
                factory.getAssessmentManager(),
                new MeteorAssessmentProfile(null, this.name, this.name),
                questions
        );

        HttpClient client = MeteorHttpClient.getInstance();

        JsonObject payload = new JsonObject();
        payload.add("assessment", GsonInstance.get().toJsonTree(assessment));

        List<HttpHeader> headers = new ArrayList<>();
        headers.addAll(this.factory.getAssessmentManager().getAuthHandler().getAuthHeaders());

        HttpRequest request = new MeteorHttpRequest(RequestType.POST, payload, headers);
        HttpResponse response = client.sendRequest(WebUtils.ASSESSMENT_CREATE_ENDPOINT, request);

        if (!response.isSuccessful()) {
            throw new AssessmentCreateException(response.getResponseBody().get("error").getAsString());
        }

        return GsonInstance.get().fromJson(response.getResponseBody().get("assessment"), MeteorAssessment.class);
    }
}
