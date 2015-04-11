package uk.co.thefishlive.meteor.assessments.questions;

import uk.co.thefishlive.auth.assessments.questions.Question;
import uk.co.thefishlive.auth.assessments.questions.QuestionBuilder;
import uk.co.thefishlive.auth.data.Token;

public abstract class MeteorQuestionBuilder implements QuestionBuilder {

    protected Token questionId;
    protected String question;
    protected int questionNumber;

    public MeteorQuestionBuilder() {
    }

    public MeteorQuestionBuilder(Question question) {
        this.questionId = question.getId();
        this.question = question.getQuestion();
        this.questionNumber = question.getQuestionNumber();
    }

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
