package uk.co.thefishlive.auth.assessments.questions.multichoice;

/**
 * Represents an option for a multiple choice question.
 */
public interface Option {

    /**
     * Get the id of this option.
     *
     * @return the id of this option
     */
    public int getId();

    /**
     * Get the text of this option.
     *
     * @return the text of this option
     */
    public String getText();

}
