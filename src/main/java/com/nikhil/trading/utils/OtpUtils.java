package com.nikhil.trading.utils;

import java.security.SecureRandom;

public class OtpUtils {

    // Define the length of the OTP
    private static final int OTP_LENGTH = 6;

    // Characters to use for OTP generation
    private static final String OTP_CHARS = "0123456789";

    // Create an instance of SecureRandom
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Generate a random OTP of the specified length.
     * @return Generated OTP as a String.
     */
    public static String generateOtp() {
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(OTP_CHARS.charAt(RANDOM.nextInt(OTP_CHARS.length())));
        }
        return otp.toString();
    }
}
