package uk.co.thefishlive.auth.assessments.assignments;

import uk.co.thefishlive.auth.assessments.Assessment;

import java.util.List;

/**
 * Represents a object that can be assigned assessments
 */
public interface AssignmentTarget {

    /**
     * Get all the current outstanding assignments for this object.
     *
     * @return a list of all the current assignments for this object
     */
    public List<Assignment> getOutstandingAssignments();

    /**
     * Get all the assignments ever assigned to this object.
     *
     * @return a list of all the assignments for this object
     */
    public List<Assignment> getAllAssignments();

    /**
     * Get all the assignments ever completed by this object.
     *
     * @return a list of all the assignments completed by this object
     */
    public List<Assignment> getCompletedAssignments();

    /**
     * Assign an assignment to this object.
     *
     * @param assignment
     *     the new assignment to assign to this object
     */
    public void assignAssessment(Assignment assignment);

    /**
     * Submit assessment to the server to be marked.
     *
     * @param assignment
     *     the assignment that has been completed
     * @param assessment
     *     the completed assessment object
     *
     * @return the marked assessment
     */
    public AssignmentResult submitAssessment(Assignment assignment, Assessment assessment);

}
