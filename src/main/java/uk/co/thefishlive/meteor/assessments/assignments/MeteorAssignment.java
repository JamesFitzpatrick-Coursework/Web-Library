package uk.co.thefishlive.meteor.assessments.assignments;

import com.google.gson.annotations.SerializedName;

import uk.co.thefishlive.auth.assessments.Assessment;
import uk.co.thefishlive.auth.assessments.AssessmentProfile;
import uk.co.thefishlive.auth.assessments.assignments.Assignment;
import uk.co.thefishlive.auth.assessments.exception.AssessmentException;
import uk.co.thefishlive.auth.data.Token;
import uk.co.thefishlive.meteor.assessments.MeteorAssessmentManager;
import uk.co.thefishlive.meteor.json.annotations.Internal;

import java.io.IOException;
import java.util.Date;

/**
 *
 */
public class MeteorAssignment implements Assignment {

    @Internal
    private MeteorAssessmentManager manager;
    @Internal
    private Assessment assessment;

    @SerializedName("assignment-id")
    private Token assignmentId;
    @SerializedName("assessment")
    private AssessmentProfile assessmentProfile;
    @SerializedName("assignment-deadline")
    private Date deadline;

    protected MeteorAssignment() {
    }

    public MeteorAssignment(MeteorAssessmentManager manager, AssessmentProfile assessmentProfile, Date deadline) {
        this.manager = manager;
        this.assessmentProfile = assessmentProfile;
        this.deadline = deadline;
    }

    @Override
    public Token getAssignmentId() {
        return assignmentId;
    }

    @Override
    public AssessmentProfile getAssessmentProfile() {
        return this.assessmentProfile;
    }

    @Override
    public Assessment getAssessment() throws IOException, AssessmentException {
        if (this.assessment == null) {
            this.assessment = manager.getAssessment(this.assessmentProfile);
        }

        return this.assessment;
    }

    @Override
    public Date getDeadline() {
        return this.deadline;
    }

    public void setHandler(MeteorAssessmentManager handler) {
        this.manager = handler;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MeteorAssignment{");
        sb.append("assignmentId=").append(assignmentId);
        sb.append(", assessment=").append(assessmentProfile);
        sb.append(", deadline=").append(deadline);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MeteorAssignment that = (MeteorAssignment) o;

        if (assessmentProfile != null ? !assessmentProfile
            .equals(that.assessmentProfile) : that.assessmentProfile != null) {
            return false;
        }
        if (assignmentId != null ? !assignmentId.equals(that.assignmentId)
                                 : that.assignmentId != null) {
            return false;
        }
        if (deadline != null ? !deadline.equals(that.deadline) : that.deadline != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = assignmentId != null ? assignmentId.hashCode() : 0;
        result = 31 * result + (assessmentProfile != null ? assessmentProfile.hashCode() : 0);
        result = 31 * result + (deadline != null ? deadline.hashCode() : 0);
        return result;
    }
}
