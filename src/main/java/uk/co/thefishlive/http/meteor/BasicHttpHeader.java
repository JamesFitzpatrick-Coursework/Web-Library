package uk.co.thefishlive.http.meteor;

import uk.co.thefishlive.http.HttpHeader;

/**
 * A immutable key value header.
 */
public class BasicHttpHeader implements HttpHeader {

    private final String key;
    private final String value;

    public BasicHttpHeader(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getName() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
