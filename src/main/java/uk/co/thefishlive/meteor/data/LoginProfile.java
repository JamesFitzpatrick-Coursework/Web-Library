package uk.co.thefishlive.meteor.data;

import com.google.common.base.Preconditions;
import uk.co.thefishlive.auth.data.Profile;
import uk.co.thefishlive.auth.data.Token;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

/**
 * Created by James on 14/10/2014.
 */
public class LoginProfile implements Profile {

    @SerializedName("user-id")
    private Token userid;
    @SerializedName("display-name")
    private String username;

    protected LoginProfile() {}

    public LoginProfile(String username) {
        this(null, username);
    }

    public LoginProfile(Token userid) {
        this(userid, null);
    }

    public LoginProfile(Token userid, String username) {
        Preconditions.checkArgument(userid != null || username != null, "Either username or userid must be provided.");

        this.userid = userid;
        this.username = username;
    }

    @Override
    public String getDisplayName() {
        return this.username;
    }

    @Override
    public Token getUserId() {
        return this.userid;
    }

    @Override
    public boolean hasUserId() {
        return this.userid != null;
    }

    @Override
    public boolean hasUserName() {
        return this.username != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginProfile that = (LoginProfile) o;

        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        return !(username != null ? !username.equals(that.username) : that.username != null);
    }

    @Override
    public int hashCode() {
        int result = userid != null ? userid.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LoginProfile{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                '}';
    }

}
