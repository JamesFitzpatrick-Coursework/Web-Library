package uk.co.thefishlive.auth.permission;

/**
 * Created by James on 13/01/2015.
 */
public class SimplePermission implements Permission {

    private final String key;
    private final String description;

    public SimplePermission(String key, String description) {
        this.key = key;
        this.description = description;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SimplePermission that = (SimplePermission) o;

        if (!description.equals(that.description)) {
            return false;
        }
        if (!key.equals(that.key)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SimplePermission{" +
               "key='" + key + '\'' +
               ", description='" + description + '\'' +
               '}';
    }
}
