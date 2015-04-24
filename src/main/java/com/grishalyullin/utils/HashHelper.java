package com.grishalyullin.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HashHelper {

    /**
     * Hash algorithm name.
     */
    private static final String HASH_ALG = "MD5";

    /**
     * Logger for UserService class.
     */
    private static final Logger LOG = Logger.getLogger(HashHelper.class.getName());

    /**
     * Characters for random string generation.
     */
    private static final String RAND_STR_CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * Random string length.
     */
    private static final int RAND_STR_LENGTH = 32;

    /**
     * SecureRandom for random string generation.
     */
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Generates hash string.
     *
     * @param input - input string
     * @return Hash string
     */
    public static String generateHash(String input) {
        String generateHash = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance(HASH_ALG);
            md5.update(input.getBytes());
            byte inputStrData[] = md5.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : inputStrData) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            generateHash = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            LOG.log(Level.SEVERE, "Algorithm \"" + HASH_ALG + "\" was not found.", e);
        }
        return generateHash;
    }

    public static String generateRandomHash() {
        return generateHash(generateRandomString(RAND_STR_LENGTH));
    }

    private static String generateRandomString(int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = RAND_STR_CHARACTERS.charAt(RANDOM.nextInt(RAND_STR_CHARACTERS.length()));
        }
        return new String(text);
    }
}
