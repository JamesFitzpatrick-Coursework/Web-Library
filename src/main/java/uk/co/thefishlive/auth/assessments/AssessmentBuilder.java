package uk.co.thefishlive.auth.assessments;

import uk.co.thefishlive.auth.assessments.exception.AssessmentCreateException;
import uk.co.thefishlive.auth.assessments.questions.Question;
import uk.co.thefishlive.auth.assessments.questions.QuestionBuilder;
import uk.co.thefishlive.auth.assessments.questions.QuestionType;

import java.io.IOException;

/**
 * A builder to build Assessments.
 */
public interface AssessmentBuilder {

    /**
     * Set the name of this assessment.
     *
     * @param name
     *     the name of the assessment
     */
    public void setName(String name);

    /**
     * Set the display name of this assessment.
     *
     * @param name
     *      the display name for this assessment
     */
    public void setDisplayName(String name);

    /**
     * Create a question builder of a given type for this assessment.
     *
     * @param type
     *     the type of question to build
     *
     * @return the new question builder
     */
    public QuestionBuilder createQuestionBuilder(QuestionType type);

    /**
     * Add a question to this assessment.
     *
     * @param question
     *     the question to add
     */
    public void addQuestion(Question question);

    /**
     * Build this assessment and add it to the remote server.
     * <p>
     * <b>Note:</b> This is a blocking operation
     *
     * @return the new assessment
     * @throws IOException
     *     if there was a error sending the request to the server
     * @throws AssessmentCreateException
     *     if there was a error handling the request on the server
     */
    public Assessment build() throws IOException, AssessmentCreateException;

}
