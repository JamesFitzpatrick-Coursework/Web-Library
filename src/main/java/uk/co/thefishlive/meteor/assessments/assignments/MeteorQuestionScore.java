package uk.co.thefishlive.meteor.assessments.assignments;

import com.google.gson.annotations.SerializedName;

import uk.co.thefishlive.auth.assessments.assignments.QuestionScore;

import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 */
public class MeteorQuestionScore implements QuestionScore {

    @SerializedName("question-number")
    private SimpleIntegerProperty questionNumber = new SimpleIntegerProperty();
    @SerializedName("score")
    private SimpleIntegerProperty score = new SimpleIntegerProperty();
    @SerializedName("max-score")
    private SimpleIntegerProperty maxScore = new SimpleIntegerProperty();

    public MeteorQuestionScore(int questionNumber, int score, int maxScore) {
        this.questionNumber.set(questionNumber);
        this.score.set(score);
        this.maxScore.set(maxScore);
    }

    @Override
    public int getQuestionNumber() {
        return this.questionNumber.get();
    }

    @Override
    public int getScore() {
        return this.score.get();
    }

    @Override
    public int getMaximumScore() {
        return this.maxScore.get();
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

        return maxScore.get() == that.maxScore.get() &&
               score.get() == that.score.get() &&
               questionNumber.get() == that.questionNumber.get();

    }

    @Override
    public int hashCode() {
        int result = score.get();
        result = 31 * result + maxScore.get();
        result = 31 * result + questionNumber.get();
        return result;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MeteorQuestionScore{");
        sb.append("question-number=").append(questionNumber.get());
        sb.append(", score=").append(score.get());
        sb.append(", maxScore=").append(maxScore.get());
        sb.append('}');
        return sb.toString();
    }
}
