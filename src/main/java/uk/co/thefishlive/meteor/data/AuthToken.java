package uk.co.thefishlive.meteor.data;

import uk.co.thefishlive.auth.data.Token;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Pattern;

public class AuthToken implements Token {

    private static final int TOKEN_LENGTH = 16;
    private static final int[] FORMAT = new int[] { 1, 4, 7, 4 };

    static {
        Preconditions.checkArgument(TOKEN_LENGTH == FORMAT[0] + FORMAT[1] + FORMAT[2] + FORMAT[3]);
    }

    private byte[] token;

    public AuthToken(byte[] token) {
        Preconditions.checkArgument(token.length == TOKEN_LENGTH);
        this.token = token;
    }

    public AuthToken(byte code, byte[] userId, byte[] random, byte[] server) {
        Preconditions.checkArgument(userId.length == FORMAT[1]);
        Preconditions.checkArgument(random.length == FORMAT[2]);
        Preconditions.checkArgument(server.length == FORMAT[3]);

        token = new byte[TOKEN_LENGTH];
        token[0] = code;
        System.arraycopy(userId, 0, token, 1, userId.length);
        System.arraycopy(random, 0, token, 5, random.length);
        System.arraycopy(server, 0, token, 12, server.length);
    }

    public byte getRawTokenType() {
        return token[0];
    }

    public String getTokenType() {
        String type = TokenTypeRegistry.getType(getRawTokenType());
        return type == null ? "Unknown" : type;
    }

    public byte[] getRawPart(int part) {
        Preconditions.checkArgument(part > 0 && part < FORMAT.length);
        byte[] secret = new byte[FORMAT[part]];
        System.arraycopy(this.token, FORMAT[part - 1], secret, 0, FORMAT[part]);
        return secret;
    }

    public String getUserSecret() {
        return toString(getRawPart(1));
    }

    public String getRandomness() {
        return toString(getRawPart(2));
    }

    public String getServerSecret() {
        return toString(getRawPart(3));
    }

    private String toString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(toString(b));
        }
        return builder.toString();
    }

    public String toString() {
        String token = "";
        int count = 0;
        int part = 0;

        for (byte current : this.token) {
            String hex = toString(current);

            if (count == FORMAT[part]) {
                count = 0;
                part++;
                token += "-";
            }

            token += hex;
            count++;
        }

        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthToken authToken = (AuthToken) o;

        return Arrays.equals(token, authToken.token);

    }

    @Override
    public int hashCode() {
        return token != null ? Arrays.hashCode(token) : 0;
    }

    private static String toString(byte b) {
        String hex = Integer.toHexString(b);

        if (hex.length() < 2) {
            hex = "0" + hex;
        } else if (hex.length() > 2) {
            hex = hex.substring(hex.length() - 2);
        }

        return hex.toUpperCase();
    }

    public static AuthToken generateRandom(String type) {
        Random random = new Random();
        byte[] randomness = new byte[FORMAT[1] + FORMAT[2] + FORMAT[3]];
        random.nextBytes(randomness);

        byte[] token = new byte[TOKEN_LENGTH];
        System.arraycopy(randomness, 0, token, 1, randomness.length);
        token[0] = (byte) TokenTypeRegistry.getType(type);

        return new AuthToken(token);
    }

    public static Token decode(String token) {
        String[] parts = Iterables.toArray(Splitter.on("-").split(token), String.class);
        byte[][] tokenParts = new byte[4][];
        int j;

        for (int i = 0; i < parts.length; i++) {
            Iterator<String> itr = Splitter.fixedLength(2).split(parts[i]).iterator();
            tokenParts[i] = new byte[parts[i].length() / 2];
            j = 0;

            while(itr.hasNext()) {
                String current = itr.next();
                tokenParts[i][j++] = Integer.valueOf(current, 16).byteValue();
            }
        }

        return new AuthToken(tokenParts[0][0], tokenParts[1], tokenParts[2], tokenParts[3]);
    }


    public static class AuthTokenHandler implements JsonSerializer<Token>, JsonDeserializer<Token> {

        @Override
        public Token deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return AuthToken.decode(jsonElement.getAsString());
        }

        @Override
        public JsonElement serialize(Token token, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(token.toString());
        }
    }
}
