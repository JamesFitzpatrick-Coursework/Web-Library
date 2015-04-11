package uk.co.thefishlive.meteor.assessments.assignments;

import uk.co.thefishlive.auth.assessments.AssessmentProfile;
import uk.co.thefishlive.auth.assessments.assignments.Assignment;
import uk.co.thefishlive.auth.assessments.assignments.AssignmentFactory;
import uk.co.thefishlive.meteor.assessments.MeteorAssessmentManager;

import java.util.Date;

/**
 *
 */
public class MeteorAssignmentFactory implements AssignmentFactory {

    private MeteorAssessmentManager manager;

    public MeteorAssignmentFactory(MeteorAssessmentManager manager) {
        this.manager = manager;
    }

    @Override
    public Assignment createAssignment(AssessmentProfile profile, Date deadline) {
        return new MeteorAssignment(manager, profile, deadline); // TODO magic this off to the server
    }

}
