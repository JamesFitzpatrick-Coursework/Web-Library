package uk.co.thefishlive.auth.assessments;

import uk.co.thefishlive.auth.assessments.questions.QuestionBuilder;
import uk.co.thefishlive.auth.assessments.questions.Question;
import uk.co.thefishlive.auth.assessments.questions.QuestionType;
import uk.co.thefishlive.auth.assessments.exception.AssessmentCreateException;

import java.io.IOException;

/**
 *
 */
public interface AssessmentBuilder {

    public void setName(String name);

    public QuestionBuilder createQuestionBuilder(QuestionType type);

    public void addQuestion(Question question);

    public Assessment build() throws IOException, AssessmentCreateException;

}
