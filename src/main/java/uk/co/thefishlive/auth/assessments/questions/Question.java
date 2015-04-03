package uk.co.thefishlive.auth.assessments.questions;

import uk.co.thefishlive.auth.data.Token;
import uk.co.thefishlive.auth.type.Typed;

/**
 * Represents a single question from an assessment.
 */
public interface Question<A> extends Typed<QuestionType> {

    /**
     * Get this question's unique identifier.
     *
     * @return this question's unique id
     */
    public Token getId();

    /**
     * Get the question number for this question.
     *
     * @return the question number for this question
     */
    public int getQuestionNumber();

    /**
     * Get the question text for this question.
     *
     * @return the question text for this question
     */
    public String getQuestion();

    /**
     * Get the current given answer for this question.
     *
     * @return the current given answer for this question
     */
    public A getCurrentAnswer();

    /**
     * Set the current given answer for this question.
     *
     * @param a
     *     the new given answer for the question
     */
    public void setCurrentAnswer(A a);

    /**
     * Gets if this question has been answered.
     *
     * @return true if the question has been answered, false otherwise
     */
    public boolean isAnswered();

}
