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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MeteorOption that = (MeteorOption) o;

        if (id != that.id) {
            return false;
        }
        if (text != null ? !text.equals(that.text) : that.text != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MeteorOption{");
        sb.append("id=").append(id);
        sb.append(", text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
