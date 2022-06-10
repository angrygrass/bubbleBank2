package nl.hva.miw.c27.team1.cryptobanking.utilities.authentication;

import java.security.SecureRandom;

/**
 * This class generates salts
 */
public class SaltMaker {

    private int saltLength;
    private final SecureRandom secureRNG;
    public final static int DEFAULT_HASH_LENGTH = 64;

    public SaltMaker(int saltLength) {
        this.saltLength = saltLength;
        secureRNG = new SecureRandom();
    }

    public SaltMaker() {
        this(DEFAULT_HASH_LENGTH);
    }

    public int getSaltLength() {
        return saltLength;
    }

    public void setSaltLength(int saltLength) {
        this.saltLength = saltLength;
    }

    /**
     *
     * @return salt with a specified number of hex characters
     *
     */
    public String generateSalt() {
        int noOfBytes = saltLength/2;
        boolean even = (saltLength % 2 == 0);

        byte[] arr = new byte[(even)?(noOfBytes):(noOfBytes + 1)];
        secureRNG.nextBytes(arr); // fills the array with random bytes
        String salt = ByteArrayToHexHelper.encodeHexString(arr);
        return (even)?(salt):(salt.substring(1));
    }
}

