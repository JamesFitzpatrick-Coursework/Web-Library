package uk.co.thefishlive.meteor.assessments;

import com.google.common.collect.ImmutableList;

import uk.co.thefishlive.auth.assessments.Assessment;
import uk.co.thefishlive.auth.assessments.AssessmentManager;
import uk.co.thefishlive.auth.assessments.AssessmentProfile;
import uk.co.thefishlive.auth.assessments.questions.Question;
import uk.co.thefishlive.meteor.json.annotations.Internal;

import java.util.List;

public class MeteorAssessment implements Assessment {

    @Internal
    private final AssessmentManager manager;

    private AssessmentProfile profile;
    private List<Question> questions;

    public MeteorAssessment(AssessmentManager manager, AssessmentProfile profile, List<Question> questions) {
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
