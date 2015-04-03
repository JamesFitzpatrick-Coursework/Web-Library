package uk.co.thefishlive.meteor.utils;

import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by James on 03/12/2014.
 */
public class ProxyUtils {

    public static Proxy getSystemProxy() throws URISyntaxException {
        System.setProperty("java.net.useSystemProxies", "true");
        List<Proxy> l = ProxySelector.getDefault().select(new URI(WebUtils.BASE_URL));
        return l.get(0);
    }
}
