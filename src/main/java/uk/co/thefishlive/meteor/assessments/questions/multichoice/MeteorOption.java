package uk.co.thefishlive.meteor.assessments.questions.multichoice;

import uk.co.thefishlive.auth.assessments.questions.multichoice.Option;

/**
 *
 */
public class MeteorOption implements Option {
    private final int id;
    private final String text;

    protected MeteorOption() {
        this(-1, "");
    }

    public MeteorOption(int id, String option) {
        this.id = id;
        this.text = option;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getText() {
        return this.text;
    }
}
