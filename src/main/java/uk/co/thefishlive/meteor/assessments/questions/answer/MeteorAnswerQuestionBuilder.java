package uk.co.thefishlive.meteor.assessments.questions.answer;

import com.google.common.collect.ImmutableSet;

import uk.co.thefishlive.auth.assessments.questions.Question;
import uk.co.thefishlive.auth.assessments.questions.answer.AnswerQuestionBuilder;
import uk.co.thefishlive.meteor.assessments.questions.MeteorQuestionBuilder;

import java.util.Set;

/**
 *
 */
public class MeteorAnswerQuestionBuilder extends MeteorQuestionBuilder implements AnswerQuestionBuilder {

    private Set<String> answers;

    @Override
    public void addAnswer(String answer) {
        this.answers.add(answer);
    }

    @Override
    public Set<String> getAnswers() {
        return ImmutableSet.copyOf(answers);
    }

    @Override
    public Question build() {
        return new MeteorAnswerQuestion(
            null, // will get filled in on the server
            questionNumber,
            question,
            answers
        );
    }
}
