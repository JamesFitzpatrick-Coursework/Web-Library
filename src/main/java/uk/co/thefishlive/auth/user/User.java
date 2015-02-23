package uk.co.thefishlive.auth.user;

import uk.co.thefishlive.auth.assessments.assignments.AssignmentTarget;
import uk.co.thefishlive.auth.data.Identifiable;
import uk.co.thefishlive.auth.group.member.GroupMember;
import uk.co.thefishlive.auth.permission.Permissible;
import uk.co.thefishlive.auth.settings.SettingStore;

/**
 * Represents a user stored on the remote user database.
 */
public interface User extends Identifiable<UserProfile>, SettingStore, Permissible, AssignmentTarget, GroupMember {

}
