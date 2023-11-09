package com.syncsoundz.server.util;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
public class CodeChallengeUtil {

    public static String generate(final String codeVerifier) {
        byte[] digest = null;
        try {
            byte[] bytes = codeVerifier.getBytes(StandardCharsets.US_ASCII);
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bytes, 0, bytes.length);
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException exception) {
            log.error("Error generating code challenge", exception);
        }

        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    }
}
