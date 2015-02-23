package uk.co.thefishlive.auth.assessments.questions.multichoice;

import uk.co.thefishlive.auth.assessments.questions.Question;

import java.util.List;

public interface MultichoiceQuestion extends Question<Integer> {

    public List<Option> getOptions();

}
