package uk.co.thefishlive.auth.assessments.assignments;

/**
 * Represents the score achieved on a specific question of an assessment
 */
public interface QuestionScore {

    /**
     * Gets the question number that this score represents.
     *
     * @return the question number this score represents
     */
    public int getQuestionNumber();

    /**
     * Get the score that was achieved in this question.
     *
     * @return the score that was achieved for this question
     */
    public int getScore();

    /**
     * Get the maximum score available for this question.
     *
     * @return the maximum score available for this question
     */
    public int getMaximumScore();

}
