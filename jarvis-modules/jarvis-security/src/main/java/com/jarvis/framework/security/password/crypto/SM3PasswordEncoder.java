package com.jarvis.framework.security.password.crypto;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年3月21日
 */
public class SM3PasswordEncoder implements PasswordEncoder {

    private static final String PREFIX = "{";

    private static final String SUFFIX = "}";

    private final StringKeyGenerator saltGenerator = new Base64StringKeyGenerator();

    private boolean encodeHashAsBase64;

    public void setEncodeHashAsBase64(boolean encodeHashAsBase64) {
        this.encodeHashAsBase64 = encodeHashAsBase64;
    }

    /**
     * Encodes the rawPass using a MessageDigest. If a salt is specified it will be merged
     * with the password before encoding.
     *
     * @param rawPassword The plain text password
     * @return Hex string of password digest (or base64 encoded string if
     *         encodeHashAsBase64 is enabled.
     */
    @Override
    public String encode(CharSequence rawPassword) {
        final String salt = PREFIX + this.saltGenerator.generateKey() + SUFFIX;
        return digest(salt, rawPassword);
    }

    private String digest(String salt, CharSequence rawPassword) {
        if (rawPassword == null) {
            rawPassword = "";
        }
        final String saltedPassword = rawPassword + salt;
        final byte[] saltedPasswordBytes = Utf8.encode(saltedPassword);
        final SM3Digest digest = new SM3Digest();
        digest.update(saltedPasswordBytes, 0, saltedPasswordBytes.length);
        final byte[] md = new byte[32];
        digest.doFinal(md, 0);
        final String encoded = encode(md);
        return salt + encoded;
    }

    private String encode(byte[] digest) {
        if (this.encodeHashAsBase64) {
            return Utf8.decode(Base64.getEncoder().encode(digest));
        }
        return new String(Hex.encode(digest));
    }

    /**
     * Takes a previously encoded password and compares it with a rawpassword after mixing
     * in the salt and encoding that value
     *
     * @param rawPassword plain text password
     * @param encodedPassword previously encoded password
     * @return true or false
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        final String salt = extractSalt(encodedPassword);
        final String rawPasswordEncoded = digest(salt, rawPassword);
        return MessageDigest.isEqual(encodedPassword.getBytes(StandardCharsets.UTF_8),
                rawPasswordEncoded.getBytes(StandardCharsets.UTF_8));
    }

    private String extractSalt(String prefixEncodedPassword) {
        final int start = prefixEncodedPassword.indexOf(PREFIX);
        if (start != 0) {
            return "";
        }
        final int end = prefixEncodedPassword.indexOf(SUFFIX, start);
        if (end < 0) {
            return "";
        }
        return prefixEncodedPassword.substring(start, end + 1);
    }

}
