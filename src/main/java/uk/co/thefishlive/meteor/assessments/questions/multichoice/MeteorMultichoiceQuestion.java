package uk.co.thefishlive.meteor.assessments.questions.multichoice;

import uk.co.thefishlive.auth.assessments.questions.QuestionType;
import uk.co.thefishlive.auth.assessments.questions.multichoice.MultichoiceQuestion;
import uk.co.thefishlive.auth.assessments.questions.multichoice.Option;
import uk.co.thefishlive.auth.data.Token;
import uk.co.thefishlive.meteor.assessments.questions.MeteorQuestion;

import java.util.List;

public class MeteorMultichoiceQuestion extends MeteorQuestion<Integer> implements MultichoiceQuestion {

    private final List<Option> options;
    private Integer answer;

    public MeteorMultichoiceQuestion(Token id, int questionNumber, String question, List<Option> options) {
        super(id, questionNumber, question);
        this.options = options;
    }

    @Override
    public List<Option> getOptions() {
        return this.options;
    }

    @Override
    public Integer getCurrentAnswer() {
        return this.answer;
    }

    @Override
    public void setCurrentAnswer(Integer answer) {
        this.answer = answer;
    }

    @Override
    public boolean isAnswered() {
        return this.answer == -1;
    }

    @Override
    public QuestionType getType() {
        return QuestionType.MULTI_CHOICE;
    }
}
