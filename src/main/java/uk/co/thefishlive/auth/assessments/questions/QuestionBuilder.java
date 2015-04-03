package uk.co.thefishlive.auth.assessments.questions;

import uk.co.thefishlive.auth.assessments.Assessment;

/**
 * Represents a builder which builds {@link Question}'s for {@link Assessment}'s
 */
public interface QuestionBuilder {

    /**
     * Set the question number for this question.
     *
     * @param number
     *     the number for this question
     */
    public void setQuestionNumber(int number);

    /**
     * Get the question number for this question.
     *
     * @return the question number for this question, or {@code -1} if it is not set
     */
    public int getQuestionNumber();

    /**
     * Set the question for this question.
     *
     * @param question
     *     the question for this question
     */
    public void setQuestion(String question);

    /**
     * Get the question for this question.
     *
     * @return the question for this question, or null if it is not set
     */
    public String getQuestion();

    /**
     * Build this question.
     *
     * @return the new built question
     */
    public Question build();

}
