package uk.co.thefishlive.meteor.assessments;

import com.google.common.collect.ImmutableList;
import uk.co.thefishlive.auth.assessments.Assessment;
import uk.co.thefishlive.auth.assessments.AssessmentProfile;
import uk.co.thefishlive.auth.assessments.questions.Question;

import java.util.List;

public class MeteorAssessment implements Assessment {

    private AssessmentProfile profile;
    private List<Question> questions;

    public MeteorAssessment(AssessmentProfile profile, List<Question> questions) {
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
    public AssessmentProfile getProfile() {
        return this.profile;
    }

    @Override
    public AssessmentProfile updateProfile(AssessmentProfile update) {
        throw new UnsupportedOperationException("Cannot update assessment profile");
    }
}
