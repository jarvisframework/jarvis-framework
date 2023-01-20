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

public class SM3PasswordEncoder implements PasswordEncoder {
    private static final String PREFIX = "{";
    private static final String SUFFIX = "}";
    private final StringKeyGenerator saltGenerator = new Base64StringKeyGenerator();
    private boolean encodeHashAsBase64;

    public SM3PasswordEncoder() {
    }

    public void setEncodeHashAsBase64(boolean encodeHashAsBase64) {
        this.encodeHashAsBase64 = encodeHashAsBase64;
    }

    public String encode(CharSequence rawPassword) {
        String salt = "{" + this.saltGenerator.generateKey() + "}";
        return this.digest(salt, rawPassword);
    }

    private String digest(String salt, CharSequence rawPassword) {
        if (rawPassword == null) {
            rawPassword = "";
        }

        String saltedPassword = rawPassword + salt;
        byte[] saltedPasswordBytes = Utf8.encode(saltedPassword);
        SM3Digest digest = new SM3Digest();
        digest.update(saltedPasswordBytes, 0, saltedPasswordBytes.length);
        byte[] md = new byte[32];
        digest.doFinal(md, 0);
        String encoded = this.encode(md);
        return salt + encoded;
    }

    private String encode(byte[] digest) {
        return this.encodeHashAsBase64 ? Utf8.decode(Base64.getEncoder().encode(digest)) : new String(Hex.encode(digest));
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String salt = this.extractSalt(encodedPassword);
        String rawPasswordEncoded = this.digest(salt, rawPassword);
        return MessageDigest.isEqual(encodedPassword.getBytes(StandardCharsets.UTF_8), rawPasswordEncoded.getBytes(StandardCharsets.UTF_8));
    }

    private String extractSalt(String prefixEncodedPassword) {
        int start = prefixEncodedPassword.indexOf("{");
        if (start != 0) {
            return "";
        } else {
            int end = prefixEncodedPassword.indexOf("}", start);
            return end < 0 ? "" : prefixEncodedPassword.substring(start, end + 1);
        }
    }
}
