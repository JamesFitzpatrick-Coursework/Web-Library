package uk.co.thefishlive.meteor.assessments.assignments;

import com.google.common.collect.ImmutableMap;

import uk.co.thefishlive.auth.assessments.assignments.Assignment;
import uk.co.thefishlive.auth.assessments.assignments.AssignmentResult;
import uk.co.thefishlive.auth.assessments.assignments.QuestionScore;

import java.util.Map;

/**
 *
 */
public class MeteorAssignmentResult implements AssignmentResult {

    private Assignment assignment;
    private Map<Integer, QuestionScore> scores;

    public MeteorAssignmentResult(Assignment assignment, Map<Integer, QuestionScore> scores) {
        this.assignment = assignment;
        this.scores = scores;
    }

    @Override
    public Assignment getAssignment() {
        return this.assignment;
    }

    @Override
    public int getTotalScore() {
        return this.scores.values().stream().mapToInt(QuestionScore::getScore).sum();
    }

    @Override
    public int getMaximumScore() {
        return this.scores.values().stream().mapToInt(QuestionScore::getMaximumScore).sum();
    }

    @Override
    public Map<Integer, QuestionScore> getScores() {
        return ImmutableMap.copyOf(this.scores);
    }

    @Override
    public float getPercentage() {
        return ((getTotalScore()  * 100f) / ((float) getMaximumScore()));
    }
}
