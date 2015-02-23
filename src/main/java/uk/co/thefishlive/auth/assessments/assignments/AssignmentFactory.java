package uk.co.thefishlive.auth.assessments.assignments;

import uk.co.thefishlive.auth.assessments.AssessmentProfile;

import java.util.Date;

/**
 * A factory to create assignments for users.
 */
public interface AssignmentFactory {

    /**
     * Create a new assignment to issue to users.
     *
     * @param profile the assessment to use for this assignment
     * @param deadline the deadline for this assignment
     * @return the new assignment
     */
    public Assignment createAssignment(AssessmentProfile profile, Date deadline);

}
