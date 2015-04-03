package uk.co.thefishlive.auth.assessments;

import uk.co.thefishlive.auth.assessments.assignments.AssignmentFactory;
import uk.co.thefishlive.auth.assessments.exception.AssessmentException;

import java.io.IOException;
import java.util.List;

/**
 * Represents a class that handles assessment management with a remote server.
 */
public interface AssessmentManager {

    /**
     * Get all assessments on the remote server.
     *
     * @return a list of all the assessments on the remote server
     */
    public List<AssessmentProfile> getAssessments() throws IOException, AssessmentException;

    /**
     * Get a assessment from the profile provided.
     * <p>
     * <bold>Note - </bold>Only the assessment id from the profile is used for lookup so the other
     * values are not needed to be provided.
     *
     * @param profile
     *     the profile to use to lookup the assessment
     *
     * @return the specified assessment
     */
    public Assessment getAssessment(AssessmentProfile profile) throws IOException, AssessmentException;

    /**
     * Delete a given assessment from the remote server.
     *
     * @param assessment
     *     the assessment to delete
     *
     * @return true if the assessment was deleted successfully, false otherwise
     */
    public boolean deleteAssessment(Assessment assessment) throws IOException, AssessmentException;

    /**
     * Update a given assessment on the remote server.
     *
     * @param assessment
     *     the assessment to update
     *
     * @return true if the assessment was updated successfully, false otherwise
     */
    public boolean updateAssessment(Assessment assessment) throws IOException, AssessmentException;

    /**
     * Get the assessment factory associated with this manager, used to create new assessments.
     *
     * @return the assessment factory associated with this manager
     */
    public AssessmentFactory getAssessmentFactory();

    /**
     * Get the assignment factory associated with this manager, used to create new assignment.
     *
     * @return the assignment factory associated with this manager
     */
    public AssignmentFactory getAssignmentFactory();
}
