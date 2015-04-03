package uk.co.thefishlive.meteor.assessments.questions.answer;

import uk.co.thefishlive.auth.assessments.questions.QuestionType;
import uk.co.thefishlive.auth.assessments.questions.answer.AnswerQuestion;
import uk.co.thefishlive.auth.data.Token;
import uk.co.thefishlive.meteor.assessments.questions.MeteorQuestion;

import java.util.Set;

public class MeteorAnswerQuestion extends MeteorQuestion<Set<String>> implements AnswerQuestion {

    private final Set<String> answers;

    public MeteorAnswerQuestion(Token id, int questionNumber, String question, Set<String> answers) {
        super(id, questionNumber, question);

        this.answers = answers;
    }

    @Override
    public Set<String> getCurrentAnswer() {
        return this.answers;
    }

    @Override
    public void setCurrentAnswer(Set<String> strings) {
        this.answers.clear();
        this.answers.addAll(strings);
    }

    @Override
    public boolean isAnswered() {
        return !this.answers.isEmpty();
    }

    @Override
    public QuestionType getType() {
        return QuestionType.ANSWER;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MeteorAnswerQuestion{");
        sb.append("question-id=").append(id.toString());
        sb.append(", question=").append(question);
        sb.append(", questionNumber=").append(questionNumber);
        sb.append(", answers=").append(answers);
        sb.append('}');
        return sb.toString();
    }
}
