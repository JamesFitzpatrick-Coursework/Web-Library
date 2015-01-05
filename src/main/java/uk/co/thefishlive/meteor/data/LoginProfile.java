package uk.co.thefishlive.meteor.data;

import com.google.common.base.Preconditions;
import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.auth.data.Token;

import com.google.gson.annotations.SerializedName;

public class LoginProfile implements UserProfile {

    @SerializedName("user-id")
    private Token userid;
    @SerializedName("user-name")
    private String username;
    @SerializedName("display-name")
    private String displayName;

    protected LoginProfile() {}

    public LoginProfile(String username) {
        this(null, username, null);
    }

    public LoginProfile(String username, String displayName) {
        this(null, username, displayName);
    }

    public LoginProfile(Token userid) {
        this(userid, null);
    }

    public LoginProfile(Token userid, String username) {
        this(userid, username, null);
    }

    public LoginProfile(Token userid, String username, String displayname) {
        Preconditions.checkArgument(userid != null || username != null || displayname != null, "Either username, displayname or userid must be provided.");

        this.userid = userid;
        this.username = username;
        this.displayName = displayname;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public Token getUserId() {
        return this.userid;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean hasUserId() {
        return this.userid != null;
    }

    @Override
    public boolean hasUsername() {
        return this.username != null;
    }

    @Override
    public boolean hasDisplayName() {
        return this.displayName != null;
    }

    @Override
    public boolean isComplete() {
        return this.hasUserId() && this.hasDisplayName() && this.hasUsername();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginProfile that = (LoginProfile) o;

        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        if (displayName != null ? !displayName.equals(that.displayName) : that.displayName != null) return false;
        return !(username != null ? !username.equals(that.username) : that.username != null);
    }

    @Override
    public int hashCode() {
        int result = userid != null ? userid.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LoginProfile{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", disply-name='" + displayName + '\'' +
                '}';
    }

}
