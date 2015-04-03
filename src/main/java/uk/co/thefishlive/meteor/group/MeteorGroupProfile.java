package uk.co.thefishlive.meteor.group;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.SerializedName;

import uk.co.thefishlive.auth.data.Token;
import uk.co.thefishlive.auth.group.GroupProfile;

public class MeteorGroupProfile implements GroupProfile {

    @SerializedName("group-id")
    private Token groupid;
    @SerializedName("group-name")
    private String groupname;
    @SerializedName("display-name")
    private String displayName;

    protected MeteorGroupProfile() {
    }

    public MeteorGroupProfile(String groupname) {
        this(null, groupname, null);
    }

    public MeteorGroupProfile(String groupname, String displayName) {
        this(null, groupname, displayName);
    }

    public MeteorGroupProfile(Token groupid) {
        this(groupid, null);
    }

    public MeteorGroupProfile(Token groupid, String groupname) {
        this(groupid, groupname, null);
    }

    public MeteorGroupProfile(Token groupid, String groupname, String displayname) {
        Preconditions.checkArgument(groupid != null || groupname != null || displayname != null, "Either groupname, displayname or groupid must be provided.");

        this.groupid = groupid;
        this.groupname = groupname;
        this.displayName = displayname;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public String getIdentifier() {
        return (hasId() ? getId().toString() : getName());
    }

    @Override
    public Token getId() {
        return this.groupid;
    }

    @Override
    public String getName() {
        return this.groupname;
    }

    @Override
    public boolean hasId() {
        return this.groupid != null;
    }

    @Override
    public boolean hasName() {
        return this.groupname != null;
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MeteorGroupProfile that = (MeteorGroupProfile) o;

        if (groupid != null ? !groupid.equals(that.groupid) : that.groupid != null) {
            return false;
        }
        if (displayName != null ? !displayName.equals(that.displayName) : that.displayName != null) {
            return false;
        }
        return !(groupname != null ? !groupname.equals(that.groupname) : that.groupname != null);
    }

    @Override
    public int hashCode() {
        int result = groupid != null ? groupid.hashCode() : 0;
        result = 31 * result + (groupname != null ? groupname.hashCode() : 0);
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
               "groupid=" + groupid +
               ", groupname='" + groupname + '\'' +
               ", display-name='" + displayName + '\'' +
               '}';
    }
}
