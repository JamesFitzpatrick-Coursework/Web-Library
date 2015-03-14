package uk.co.thefishlive.meteor.assessments.assignments;

import com.google.gson.annotations.SerializedName;

import uk.co.thefishlive.auth.assessments.assignments.QuestionScore;

/**
 *
 */
public class MeteorQuestionScore implements QuestionScore {

    @SerializedName("score")
    private int score;
    @SerializedName("max-score")
    private int maxScore;

    public MeteorQuestionScore(int score, int maxScore) {
        this.score = score;
        this.maxScore = maxScore;
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public int getMaximumScore() {
        return this.maxScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MeteorQuestionScore that = (MeteorQuestionScore) o;

        if (maxScore != that.maxScore) {
            return false;
        }
        if (score != that.score) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = score;
        result = 31 * result + maxScore;
        return result;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MeteorQuestionScore{");
        sb.append("score=").append(score);
        sb.append(", maxScore=").append(maxScore);
        sb.append('}');
        return sb.toString();
    }
}
