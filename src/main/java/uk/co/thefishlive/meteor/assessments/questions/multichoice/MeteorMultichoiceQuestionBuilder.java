package uk.co.thefishlive.meteor.assessments.questions.multichoice;

import com.google.common.collect.Lists;
import uk.co.thefishlive.auth.assessments.questions.multichoice.MultichoiceQuestionBuilder;
import uk.co.thefishlive.auth.assessments.questions.Question;
import uk.co.thefishlive.auth.assessments.questions.multichoice.Option;
import uk.co.thefishlive.meteor.assessments.questions.MeteorQuestionBuilder;
import uk.co.thefishlive.meteor.data.AuthToken;

import java.util.List;

public class MeteorMultichoiceQuestionBuilder extends MeteorQuestionBuilder implements MultichoiceQuestionBuilder {
    private List<Option> options = Lists.newArrayList();

    @Override
    public void addOption(String option) {
        this.options.add(new MeteorOption(options.size(), option));
    }

    @Override
    public Question build() {
        return new MeteorMultichoiceQuestion(
                null, // Will get filled in by the server
                questionNumber,
                question,
                options
        );
    }
}
