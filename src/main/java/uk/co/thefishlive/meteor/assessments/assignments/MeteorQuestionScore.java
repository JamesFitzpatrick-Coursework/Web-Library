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
}
