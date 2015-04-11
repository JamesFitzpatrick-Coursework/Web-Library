package uk.co.thefishlive.auth.assessments;

import uk.co.thefishlive.auth.assessments.exception.AssessmentException;
import uk.co.thefishlive.auth.assessments.questions.Question;
import uk.co.thefishlive.auth.data.Identifiable;
import uk.co.thefishlive.auth.data.Token;

import java.io.IOException;
import java.util.List;

/**
 * Represents an assessment that can be completed by a user.
 */
public interface Assessment extends Identifiable<AssessmentProfile> {

    /**
     * Gets the question at a specific index in the assessment.
     *
     * @param number the number of the question
     * @return the question at that number
     */
    public Question getQuestion(int number);

    /**
     * Gets all the questions for this assessment.
     *
     * @return a list of all the questions for this assessment
     */
    public List<Question> getQuestions();

    /**
     * Add a question to this assessment.
     *
     * @param question the question to add
     */
    public void addQuestion(Question question) throws IOException, AssessmentException;

    /**
     * Update a question with new information.
     *  @param id the id of the question to update
     * @param question the question data to update that profile to
     */
    public Question updateQuestion(Token id, Question question) throws IOException, AssessmentException;

    /**
     * Delete a given question.
     *
     * @param id the id of the question to delete
     */
    public void deleteQuestion(Token id) throws IOException, AssessmentException;
}
