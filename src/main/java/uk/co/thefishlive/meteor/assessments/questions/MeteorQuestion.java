package uk.co.thefishlive.meteor.assessments.questions;

import com.google.gson.annotations.SerializedName;
import uk.co.thefishlive.auth.assessments.questions.Question;
import uk.co.thefishlive.auth.data.Token;

public abstract class MeteorQuestion<A> implements Question<A> {

    @SerializedName("question-id")
    protected final Token id;
    @SerializedName("question-number")
    protected final int questionNumber;
    protected final String question;

    public MeteorQuestion(Token id, int questionNumber, String question) {
        this.id = id;
        this.questionNumber = questionNumber;
        this.question = question;
    }

    @Override
    public Token getId() {
        return id;
    }

    @Override
    public int getQuestionNumber() {
        return questionNumber;
    }

    @Override
    public String getQuestion() {
        return question;
    }
}
