package uk.co.thefishlive.auth.session;

/**
 * Listener to listen for updates about the current active session.
 */
public interface SessionListener {

    /**
     * Event called when a new session is set as the active session.
     *
     * @param newSession the new active session
     */
    public void onActiveSessionChanged(Session newSession);

}
