package uk.co.thefishlive.auth.assessments;

import uk.co.thefishlive.auth.assessments.questions.Question;
import uk.co.thefishlive.auth.assessments.questions.QuestionBuilder;
import uk.co.thefishlive.auth.assessments.questions.QuestionType;

/**
 * Factory to create assessments from and handle all assessment based operations.
 */
public interface AssessmentFactory {

    /**
     * Create a new builder to build assessments.
     *
     * @return a new builder to build assessments
     */
    public AssessmentBuilder createAssessmentBuilder();

    /**
     * Create a new builder for building questions of a given type.
     *
     * @param type
     *     the type of question to build
     *
     * @return a new builder to build questions of the given type
     */
    public QuestionBuilder createQuestionBuilder(QuestionType type);

    /**
     * Create a new builder for building questions of a given type based off a existing question of that type.
     *
     * @param question
     *     the question to base this builder off
     *
     * @return a new builder to edit the question provided
     */
    public QuestionBuilder createQuestionBuilder(Question question);
}
