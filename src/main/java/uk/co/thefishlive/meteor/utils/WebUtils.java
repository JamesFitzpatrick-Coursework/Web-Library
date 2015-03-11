package uk.co.thefishlive.meteor.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import uk.co.thefishlive.auth.assessments.AssessmentProfile;
import uk.co.thefishlive.auth.assessments.assignments.Assignment;
import uk.co.thefishlive.auth.group.GroupProfile;
import uk.co.thefishlive.auth.permission.Permission;
import uk.co.thefishlive.auth.settings.Setting;
import uk.co.thefishlive.auth.user.UserProfile;

public class WebUtils {

    public static final String BASE_URL = "http://auth.thefishlive.co.uk/v1";

    public static final URL HANDSHAKE_ENDPOINT = constantUrl(BASE_URL + "/handshake");
    public static final URL LOGIN_ENDPOINT = constantUrl(BASE_URL + "/login");
    public static final URL REFRESH_ENDPOINT = constantUrl(BASE_URL + "/refresh");
    public static final URL INVALIDATE_ENDPOINT = constantUrl(BASE_URL + "/invalidate");
    public static final URL VALIDATE_ENDPOINT = constantUrl(BASE_URL + "/validate");

    public static final URL USERS_ENDPOINT = constantUrl(BASE_URL + "/users");
    public static final URL USER_CREATE_ENDPOINT = constantUrl(USERS_ENDPOINT + "/create");
    public static final URL GROUPS_ENDPOINT = constantUrl(BASE_URL + "/groups");
    public static final URL GROUP_CREATE_ENDPOINT = constantUrl(GROUPS_ENDPOINT + "/create");
    public static final URL ASSESSMENTS_ENDPOINT = constantUrl(BASE_URL + "/assessments");
    public static final URL ASSESSMENT_CREATE_ENDPOINT = constantUrl(ASSESSMENTS_ENDPOINT + "/create");

    public static URL constantUrl(String url) {
        try {
            return URI.create(url).toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static URL USER_LOOKUP_ENDPOINT(UserProfile profile) {
        return constantUrl(USERS_ENDPOINT + "/" + profile.getIdentifier() + "/");
    }

    public static URL USER_GROUPS_ENDPOINT(UserProfile profile) {
        return constantUrl(USERS_ENDPOINT + "/" + profile.getIdentifier() + "/groups/");
    }

    public static URL USER_GROUPS_ADD_ENDPOINT(UserProfile profile) {
        return constantUrl(USERS_ENDPOINT + "/" + profile.getIdentifier() + "/groups/add/");
    }

    public static URL USER_DELETE_ENDPOINT(UserProfile profile) {
        return constantUrl(USERS_ENDPOINT + "/" + profile.getIdentifier() + "/");
    }

    public static URL USER_PERMISSION_VIEW_ENDPOINT(UserProfile profile) {
        return constantUrl(USERS_ENDPOINT + "/" + profile.getIdentifier() + "/permissions/");
    }

    public static URL USER_PERMISSION_EDIT_ENDPOINT(UserProfile profile) {
        return constantUrl(USERS_ENDPOINT + "/" + profile.getIdentifier() + "/permissions/edit/");
    }

    public static URL USER_PERMISSION_CHECK_ENDPOINT(UserProfile profile, Permission permission) {
        return constantUrl(USERS_ENDPOINT + "/" + profile.getIdentifier() + "/permissions/" + permission.getKey());
    }

    public static URL USER_SETTING_EDIT_ENDPOINT(UserProfile profile) {
        return constantUrl(USERS_ENDPOINT + "/" + profile.getIdentifier() + "/settings/edit/");
    }

    public static URL USER_SETTING_LOOKUP_ENDPOINT(UserProfile profile, String setting) {
        return constantUrl(USERS_ENDPOINT + "/" + profile.getIdentifier() + "/settings/" + setting);
    }

    public static URL USER_ASSIGNMENT_ADD(UserProfile profile) {
        return constantUrl(USERS_ENDPOINT + "/" + profile.getIdentifier() + "/assignments/add");
    }

    public static URL USER_ASSIGNMENT_COMPLETE(UserProfile profile) {
        return constantUrl(USERS_ENDPOINT + "/" + profile.getIdentifier() + "/assignments/complete");
    }

    public static URL USER_ASSIGNMENT_LOOKUP_OUTSTANDING(UserProfile profile) {
        return constantUrl(USERS_ENDPOINT + "/" + profile.getIdentifier() + "/assignments/");
    }

    public static URL USER_ASSIGNMENT_LOOKUP_COMPLETED(UserProfile profile) {
        return constantUrl(USERS_ENDPOINT + "/" + profile.getIdentifier() + "/assignments/completed");
    }

    public static URL USER_ASSIGNMENT_LOOKUP_ALL(UserProfile profile) {
        return constantUrl(USERS_ENDPOINT + "/" + profile.getIdentifier() + "/assignments/all");
    }

    public static URL GROUP_LOOKUP_ENDPOINT(GroupProfile profile) {
        return constantUrl(GROUPS_ENDPOINT + "/" + profile.getIdentifier() + "/");
    }

    public static URL GROUP_USERS_ENDPOINT(GroupProfile profile) {
        return constantUrl(GROUPS_ENDPOINT + "/" + profile.getIdentifier() + "/users/");
    }

    public static URL GROUP_DELETE_ENDPOINT(GroupProfile profile) {
        return constantUrl(GROUPS_ENDPOINT + "/" + profile.getIdentifier() + "/");
    }

    public static URL GROUP_PERMISSION_VIEW_ENDPOINT(GroupProfile profile) {
        return constantUrl(GROUPS_ENDPOINT + "/" + profile.getIdentifier() + "/permissions/");
    }

    public static URL GROUP_PERMISSION_EDIT_ENDPOINT(GroupProfile profile) {
        return constantUrl(GROUPS_ENDPOINT + "/" + profile.getIdentifier() + "/permissions/edit/");
    }

    public static URL GROUP_PERMISSION_CHECK_ENDPOINT(GroupProfile profile, Permission permission) {
        return constantUrl(GROUPS_ENDPOINT + "/" + profile.getIdentifier() + "/permissions/" + permission.getKey());
    }

    public static URL GROUP_SETTING_EDIT_ENDPOINT(GroupProfile profile) {
        return constantUrl(GROUPS_ENDPOINT + "/" + profile.getIdentifier() + "/settings/edit/");
    }

    public static URL GROUP_SETTING_LOOKUP_ENDPOINT(GroupProfile profile, String setting) {
        return constantUrl(GROUPS_ENDPOINT + "/" + profile.getIdentifier() + "/settings/" + setting);
    }

    public static URL ASSESSMENT_LOOKUP_ENDPOINT(AssessmentProfile profile) {
        return constantUrl(ASSESSMENTS_ENDPOINT + "/" + profile.getIdentifier() + "/");
    }
}
