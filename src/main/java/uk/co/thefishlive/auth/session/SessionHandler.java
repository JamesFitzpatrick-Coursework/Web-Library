package uk.co.thefishlive.auth.session;

import uk.co.thefishlive.meteor.session.exception.SessionException;

import java.io.IOException;

/**
 * Created by James on 14/10/2014.
 */
public interface SessionHandler {

    public boolean isValid(Session session) throws IOException, SessionException;

    public boolean invalidate(Session session) throws SessionException, IOException;

    public Session refresh(Session session) throws IOException, SessionException;

}
