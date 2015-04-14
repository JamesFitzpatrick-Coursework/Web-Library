package uk.co.thefishlive.auth.assessments.assignments;

import java.util.Map;

/**
 * Represents a assignment that has been marked by the remote server.
 */
public interface AssignmentResult {

    /**
     * Get the assignment that has been marked
     *
     * @return the assignment that has been marked
     */
    public Assignment getAssignment();

    /**
     * Get the total score for the assessment that was achieved.
     *
     * @return the total score achieved for this assessment
     */
    public int getTotalScore();

    /**
     * Get the maximum score available for this assignment.
     *
     * @return the maximum score available for this assignment
     */
    public int getMaximumScore();

    /**
     * Get the score breakdown for each question.
     *
     * @return a map of the score breakdown for each question
     */
    public Map<Integer, QuestionScore> getScores();

    /**
     * Get the percentage score for this assignment.
     * <p />
     * This is equivalent to {@code {@link #getTotalScore()}/{@link #getMaximumScore()} * 100}
     *
     * @return the percentage score for this assignment
     */
    public float getPercentage();
}
