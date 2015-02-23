package uk.co.thefishlive.meteor.assessments;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.SerializedName;
import uk.co.thefishlive.auth.assessments.AssessmentProfile;
import uk.co.thefishlive.auth.data.Token;

public class MeteorAssessmentProfile implements AssessmentProfile {

    @SerializedName("assessment-id")
    private Token assessmentId;
    @SerializedName("assessment-name")
    private String assessmentName;
    @SerializedName("display-name")
    private String displayName;

    protected MeteorAssessmentProfile() {}

    public MeteorAssessmentProfile(String assessmentName) {
        this(null, assessmentName, null);
    }

    public MeteorAssessmentProfile(String assessmentName, String displayName) {
        this(null, assessmentName, displayName);
    }

    public MeteorAssessmentProfile(Token assessmentId) {
        this(assessmentId, null);
    }

    public MeteorAssessmentProfile(Token assessmentId, String assessmentName) {
        this(assessmentId, assessmentName, null);
    }

    public MeteorAssessmentProfile(Token assessmentId, String assessmentName, String displayname) {
        this.assessmentId = assessmentId;
        this.assessmentName = assessmentName;
        this.displayName = displayname;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public String getIdentifier() {
        return getId().toString();
    }

    @Override
    public Token getId() {
        return this.assessmentId;
    }

    @Override
    public String getName() {
        return this.assessmentName;
    }

    @Override
    public boolean hasId() {
        return this.assessmentId != null;
    }

    @Override
    public boolean hasName() {
        return this.assessmentName != null;
    }

    @Override
    public boolean hasDisplayName() {
        return this.displayName != null;
    }

    @Override
    public boolean isComplete() {
        return this.hasId() && this.hasDisplayName() && this.hasName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeteorAssessmentProfile that = (MeteorAssessmentProfile) o;

        if (assessmentId != null ? !assessmentId.equals(that.assessmentId) : that.assessmentId != null) return false;
        if (displayName != null ? !displayName.equals(that.displayName) : that.displayName != null) return false;
        return !(assessmentName != null ? !assessmentName.equals(that.assessmentName) : that.assessmentName != null);
    }

    @Override
    public int hashCode() {
        int result = assessmentId != null ? assessmentId.hashCode() : 0;
        result = 31 * result + (assessmentName != null ? assessmentName.hashCode() : 0);
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "assessmentId=" + assessmentId +
                ", assessmentName='" + assessmentName + '\'' +
                ", display-name='" + displayName + '\'' +
                '}';
    }
}
