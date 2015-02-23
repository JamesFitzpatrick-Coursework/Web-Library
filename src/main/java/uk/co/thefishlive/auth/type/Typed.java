package uk.co.thefishlive.auth.type;

/**
 * Represents a object that can exist in multiple different "types".
 */
public interface Typed<T extends Enum> {

    /**
     * Get the type of this object.
     *
     * @return the type of this object
     */
    public T getType();

}
