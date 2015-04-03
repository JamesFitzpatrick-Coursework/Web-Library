package uk.co.thefishlive.auth;

import uk.co.thefishlive.auth.assessments.AssessmentManager;
import uk.co.thefishlive.auth.group.GroupManager;
import uk.co.thefishlive.auth.login.LoginHandler;
import uk.co.thefishlive.auth.session.SessionHandler;
import uk.co.thefishlive.auth.session.SessionStore;
import uk.co.thefishlive.auth.user.UserManager;

/**
 * Represents the handler controlling all authentication, contains methods to authenticate users.
 */
public interface AuthHandler extends SessionStore, Client {

    /**
     * Gets the login handler, handles login flow.
     *
     * @return the current login handler
     */
    public LoginHandler getLoginHandler();

    /**
     * Gets the session handler, handles session management.
     *
     * @return the current session manager
     */
    public SessionHandler getSessionHandler();

    /**
     * Gets the current user manager.
     *
     * @return the current user manager instance
     */
    public UserManager getUserManager();

    /**
     * Gets the current group manager.
     *
     * @return the current group manager instance
     */
    public GroupManager getGroupManager();

    /**
     * Gets the current assessment manager.
     *
     * @return the current assessment manager
     */
    public AssessmentManager getAssessmentManager();

}
