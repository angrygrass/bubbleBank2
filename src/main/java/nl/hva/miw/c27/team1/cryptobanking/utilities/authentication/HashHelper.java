package nl.hva.miw.c27.team1.cryptobanking.utilities.authentication;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashHelper {
    public static final String SHA_256 = "SHA-256";
    public static final String NO_SUCH_ALGORITHM = "The selected algorithm doesn't exist.";

    public static String hash(String password, String salt, String pepper) {
        byte[] digest;
        try {
            MessageDigest md = MessageDigest.getInstance(SHA_256);
            md.update(pepper.getBytes(StandardCharsets.UTF_8));
            md.update(password.getBytes(StandardCharsets.UTF_8));
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            digest = md.digest();
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
            throw new RuntimeException(NO_SUCH_ALGORITHM);
        }
        return ByteArrayToHexHelper.encodeHexString(digest);
    }

    public static String hash(String password) {
        //overloaded version for consecutive rounds without salt and/or pepper
        return hash(password, "", "");
    }
}
