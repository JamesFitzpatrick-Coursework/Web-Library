package uk.co.thefishlive.meteor.data;

import uk.co.thefishlive.auth.data.Token;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class AuthTokenTest {

    @Test
    public void testTokenDecoding() {
        AuthToken token = AuthToken.generateRandom("client-id");

        String tokenString = token.toString();
        System.out.println(tokenString);
        Token token2 = AuthToken.decode(tokenString);
        assertEquals(tokenString, token2.toString());
    }

    @Test
    public void testTokenGenerating1() {
        AuthToken token = AuthToken.generateRandom("user-id");
        System.out.println(token.toString());
    }

    @Test
    public void testTokenGenerating2() {
        AuthToken token = AuthToken.generateRandom("group-id");
        System.out.println(token.toString());
    }
}
