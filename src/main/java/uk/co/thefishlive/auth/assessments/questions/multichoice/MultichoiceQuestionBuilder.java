package uk.co.thefishlive.auth.assessments.questions.multichoice;

import uk.co.thefishlive.auth.assessments.questions.QuestionBuilder;

import java.util.List;

/**
 * A builder to build multiple choice questions.
 */
public interface MultichoiceQuestionBuilder extends QuestionBuilder {

    /**
     * Add a option to this question.
     *
     * @param option
     *     the text of the option to add
     */
    public void addOption(String option);

    /**
     * Get the options currently associated with this question.
     *
     * @return a list of the options for this question
     */
    public List<Option> getOptions();

}
