package uk.co.thefishlive.auth.assessments;

import uk.co.thefishlive.auth.assessments.questions.Question;
import uk.co.thefishlive.auth.data.Identifiable;

import java.util.List;

/**
 *
 */
public interface Assessment extends Identifiable<AssessmentProfile> {

    public Question getQuestion(int number);

    public List<Question> getQuestions();

}
