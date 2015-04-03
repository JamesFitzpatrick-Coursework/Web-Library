package uk.co.thefishlive.meteor.assessments.questions;

import uk.co.thefishlive.auth.assessments.questions.QuestionBuilder;

public abstract class MeteorQuestionBuilder implements QuestionBuilder {

    protected String question;
    protected int questionNumber;

    @Override
    public void setQuestionNumber(int number) {
        this.questionNumber = number;
    }

    @Override
    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public int getQuestionNumber() {
        return this.questionNumber;
    }

    @Override
    public String getQuestion() {
        return this.question;
    }

}
