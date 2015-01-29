package uk.co.thefishlive.auth.data;

/**
 * Represents a class that can be identified by a single
 * {@link Profile}
 *
 * @param <P> the type of profile that identifies this class
 */
public interface Identifiable<P extends Profile> {

    /**
     * Gets this objects unique profile
     *
     * @return the unique profile for this object
     */
    public P getProfile();

    /**
     * Update the profile used to identify this object. Any fields left null
     * or any fields that cannot be updated but were specified will left as
     * they currently are.
     * <p />
     * <b>Note:</b> This will only update certain fields on the underlying
     * profile and these will be different depending on class, as a general
     * rule key  fields (eg. Ids) cannot be updated.
     * <p />
     * Some classes are also immutable and so this method will throw an
     * {@link UnsupportedOperationException}.
     *
     * @throws UnsupportedOperationException if this class does not support
     *      updating the profile.
     * @param update the new profile to use
     * @return the new profile fleshed out.
     */
    public P updateProfile(P update);

}
