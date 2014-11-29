package uk.co.thefishlive.meteor.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by James on 20/11/2014.
 */
public class WebUtils {

    public static final String BASE_URL = "http://auth.thefishlive.co.uk/v1";

    public static final URL HANDSHAKE_ENDPOINT = constantUrl(BASE_URL + "/handshake");
    public static final URL LOGIN_ENDPOINT = constantUrl(BASE_URL + "/login");
    public static final URL REFRESH_ENDPOINT = constantUrl(BASE_URL + "/refresh");
    public static final URL INVALIDATE_ENDPOINT = constantUrl(BASE_URL + "/invalidate");
    public static final URL VALIDATE_ENDPOINT = constantUrl(BASE_URL + "/validate");


    public static URL constantUrl(String url) {
        try {
            return URI.create(url).toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
