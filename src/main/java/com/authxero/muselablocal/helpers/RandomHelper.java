package com.authxero.muselablocal.helpers;

import java.security.SecureRandom;

public class RandomHelper {
    private static final SecureRandom secureRandom = new SecureRandom();

    public static long generateRandomLong(long min, long max) {
        if (min >= max) {
            throw new IllegalArgumentException("Invalid range: min must be less than max");
        }
        return min + (long) (secureRandom.nextDouble() * (max - min));
    }

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            sb.append(characters.charAt(randomIndex));
        }
        return sb.toString();
    }
}
