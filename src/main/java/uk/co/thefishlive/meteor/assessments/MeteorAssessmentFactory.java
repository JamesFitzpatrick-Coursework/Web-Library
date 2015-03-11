package uk.co.thefishlive.meteor.assessments;

import uk.co.thefishlive.auth.AuthHandler;
import uk.co.thefishlive.auth.assessments.AssessmentFactory;
import uk.co.thefishlive.auth.assessments.AssessmentBuilder;
import uk.co.thefishlive.auth.assessments.questions.QuestionBuilder;
import uk.co.thefishlive.auth.assessments.questions.QuestionType;
import uk.co.thefishlive.meteor.assessments.questions.multichoice.MeteorMultichoiceQuestionBuilder;

public class MeteorAssessmentFactory implements AssessmentFactory {

    private MeteorAssessmentManager manager;

    public MeteorAssessmentFactory(MeteorAssessmentManager manager) {
        this.manager = manager;
    }

    @Override
    public AssessmentBuilder createAssessmentBuilder() {
        return new MeteorAssessmentBuilder(this);
    }

    @Override
    public QuestionBuilder createQuestionBuilder(QuestionType type) {
        switch (type) {
            case MULTI_CHOICE:
                return new MeteorMultichoiceQuestionBuilder();
            case ANSWER:
            default:
                throw new UnsupportedOperationException("Specified question type " + type.name() + " is not currently supported");
        }
    }

    public MeteorAssessmentManager getAssessmentManager() {
        return this.manager;
    }
}
