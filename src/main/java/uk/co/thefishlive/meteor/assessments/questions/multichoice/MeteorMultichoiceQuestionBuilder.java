package uk.co.thefishlive.meteor.assessments.questions.multichoice;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import uk.co.thefishlive.auth.assessments.questions.Question;
import uk.co.thefishlive.auth.assessments.questions.multichoice.MultichoiceQuestion;
import uk.co.thefishlive.auth.assessments.questions.multichoice.MultichoiceQuestionBuilder;
import uk.co.thefishlive.auth.assessments.questions.multichoice.Option;
import uk.co.thefishlive.meteor.assessments.questions.MeteorQuestionBuilder;

import java.util.List;

public class MeteorMultichoiceQuestionBuilder extends MeteorQuestionBuilder implements MultichoiceQuestionBuilder {

    private List<Option> options = Lists.newArrayList();

    public MeteorMultichoiceQuestionBuilder() {
        super();
    }

    public MeteorMultichoiceQuestionBuilder(MultichoiceQuestion question) {
        super(question);
        options = question.getOptions();
    }

    @Override
    public void addOption(String option) {
        this.options.add(new MeteorOption(options.size(), option));
    }

    @Override
    public List<Option> getOptions() {
        return ImmutableList.copyOf(options);
    }

    @Override
    public void clearOptions() {
        this.options.clear();
    }

    @Override
    public Question build() {
        return new MeteorMultichoiceQuestion(
            questionId,
            questionNumber,
            question,
            options
        );
    }
}
