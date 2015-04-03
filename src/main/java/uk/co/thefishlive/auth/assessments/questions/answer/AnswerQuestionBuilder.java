package uk.co.thefishlive.auth.assessments.questions.answer;

import uk.co.thefishlive.auth.assessments.questions.QuestionBuilder;

import java.util.Set;

/**
 * A builder to build short answer choice questions.
 */
public interface AnswerQuestionBuilder extends QuestionBuilder {

    /**
     * Add a possible answer for this question.
     *
     * @param answer
     *     the new possible answer for this question
     */
    public void addAnswer(String answer);

    /**
     * Get all the possible answers for this question.
     *
     * @return a list of all the possible answers for this question
     */
    public Set<String> getAnswers();
}
