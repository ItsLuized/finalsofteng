package com.softeng.finalsofteng.controller;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class Encryption {
    private static final String AES = "AES";
    private byte[] keyValue;
    private static Encryption instance = null;

    private Encryption(String key) {
        byte[] secretKey = new byte[32];
        for (int i = 0; i < 32; i++) {
            secretKey[i] = key.getBytes()[i%key.getBytes().length];
        }
        this.keyValue = secretKey;
    }

    public static Encryption getInstance(String nonce){
        if (instance == null){
            instance = new Encryption(nonce);
        }
        return instance;
    }

    public String encrypt(String data) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
    }

    public String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedData)));
    }

    public Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, AES);
        return key;
    }
}
