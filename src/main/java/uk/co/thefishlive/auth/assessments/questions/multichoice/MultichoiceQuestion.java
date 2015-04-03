package uk.co.thefishlive.auth.assessments.questions.multichoice;

import uk.co.thefishlive.auth.assessments.questions.Question;

import java.util.List;

/**
 * Represents a multiple choice question
 */
public interface MultichoiceQuestion extends Question<Integer> {

    /**
     * Get the options for this question.
     *
     * @return a list of the options for this question
     */
    public List<Option> getOptions();

}
