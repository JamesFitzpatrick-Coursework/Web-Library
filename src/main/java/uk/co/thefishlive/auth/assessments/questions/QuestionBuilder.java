package uk.co.thefishlive.auth.assessments.questions;

/**
 *
 */
public interface QuestionBuilder {

    public void setQuestionNumber(int number);

    public void setQuestion(String question);

    public Question build();

}
