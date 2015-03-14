package uk.co.thefishlive.meteor.assessments.questions.multichoice;

import uk.co.thefishlive.auth.assessments.questions.QuestionType;
import uk.co.thefishlive.auth.assessments.questions.multichoice.MultichoiceQuestion;
import uk.co.thefishlive.auth.assessments.questions.multichoice.Option;
import uk.co.thefishlive.auth.data.Token;
import uk.co.thefishlive.meteor.assessments.questions.MeteorQuestion;

import java.util.List;

public class MeteorMultichoiceQuestion extends MeteorQuestion<Integer> implements MultichoiceQuestion {

    private final List<Option> options;
    private Integer answer = -1;

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
        if (this.answer == null) {
            return -1;
        }

        return this.answer;
    }

    @Override
    public void setCurrentAnswer(Integer answer) {
        this.answer = answer;
    }

    @Override
    public boolean isAnswered() {
        return getCurrentAnswer() != -1;
    }

    @Override
    public QuestionType getType() {
        return QuestionType.MULTI_CHOICE;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MeteorMultichoiceQuestion{");
        sb.append("question-id=").append(id.toString());
        sb.append(", question=").append(question);
        sb.append(", questionNumber=").append(questionNumber);
        sb.append(", options=").append(options);
        sb.append(", answer=").append(answer);
        sb.append('}');
        return sb.toString();
    }
}
