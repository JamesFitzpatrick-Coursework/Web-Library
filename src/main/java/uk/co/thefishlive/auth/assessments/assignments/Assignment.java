package uk.co.thefishlive.auth.assessments.assignments;

import uk.co.thefishlive.auth.assessments.Assessment;
import uk.co.thefishlive.auth.assessments.AssessmentManager;
import uk.co.thefishlive.auth.assessments.AssessmentProfile;
import uk.co.thefishlive.auth.assessments.exception.AssessmentException;
import uk.co.thefishlive.auth.data.Token;

import java.io.IOException;
import java.util.Date;

/**
 * Represents an assignment that can be given to users to complete.
 */
public interface Assignment {

    /**
     * Gets the unique identifier for this assignment.
     *
     * @return the unique identifier for this assignment
     */
    public Token getAssignmentId();

    /**
     * Gets the profile of the assessment associated with this assignment.
     *
     * @return the profile of the assessment associated with this assignment
     */
    public AssessmentProfile getAssessmentProfile();

    /**
     * Get the assessment object associated with this assignment.
     *
     * @return the assessment object associated with this assignment
     */
    public Assessment getAssessment() throws IOException, AssessmentException;

    /**
     * Get the deadline for this assignment.
     *
     * @return the deadline for this assignment
     */
    public Date getDeadline();

}
