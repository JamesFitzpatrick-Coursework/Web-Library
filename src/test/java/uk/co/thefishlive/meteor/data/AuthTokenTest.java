package uk.co.thefishlive.meteor.data;

import uk.co.thefishlive.auth.data.Token;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class AuthTokenTest {

    @Test
    public void testTokenDecoding() {
        AuthToken token = AuthToken.generateRandom("client-id");

        System.out.println(token.toString());
        Token token2 = AuthToken.decode(token.toString());
        System.out.println(token2.toString());
    }

    private String toString(byte[] userSecret) {
        StringBuilder builder = new StringBuilder();
        for (byte element : userSecret) {
            builder.append(Integer.toHexString(element));
        }
        return builder.toString();
    }

    /*@Test
    public void testTokenToString() {
        AuthToken token = AuthToken.generateRandom();
        String toString = token.toString();
        assertTrue(32 == toString.length());
        System.out.println(toString);
    }*/
}
