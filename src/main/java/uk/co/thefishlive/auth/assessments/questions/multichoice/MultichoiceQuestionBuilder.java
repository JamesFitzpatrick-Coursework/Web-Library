package uk.co.thefishlive.auth.assessments.questions.multichoice;

import uk.co.thefishlive.auth.assessments.questions.QuestionBuilder;

public interface MultichoiceQuestionBuilder extends QuestionBuilder {

    public void addOption(String option);

}
