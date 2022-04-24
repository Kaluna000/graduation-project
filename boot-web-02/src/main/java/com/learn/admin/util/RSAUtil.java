package com.learn.admin.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAUtil {

    private static final String ALGORITHM = "RSA";
    private static final String PUBLICK_EY = "PUBLICK_EY";
    private static final String PRIVATE_KEY = "PRIVATE_KEY";
    public static final String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIdZwXJrb3Q+/JP7ZoG11Q/o8xc7zuN+p5ebZWuxryNPn6dCLv/kl1W3sQklwAmC888UIbJZcXzDnEqOO93FOiW/nFOqBhT24q5kYv1cKkAeuHMp4QAicXXQnpHKiydXnviB7O3dCVsbi9O3z9XM6jDuJKMeEOMMLmmiZuFBpFZ/AgMBAAECgYBdZJRzxCgamR+laJIZrIMAwGeEP5OJLCquFN7NicRkZBuwyJ0vmubFkcOJoU+6AG8FE0oRC2Cgi0T7uNscMmvk/q5xSI+KtA755ekhPMkr9cCl04WWhZ3NfBMaJYvv7zRV91UR/dD/n/CKzx7qyMzCWKoAu2Ocd90mh8BBYWyzYQJBANaVGrF1cD9wZm1bqkmdi/f+siec/XLA5+z5Fu5krlot+Tg9Pm4bAOWbSxG8Vj0tus38F27ut9dquBlAlF/D4JECQQChearSMILR/MYw20CJ3bQ4OersLe6YDBCtkvShxc6NBaI9CgjON/Gq8piJIH2zWNTFW7VTKVkMAbsqck4p3k4PAkBes0ErjjOym61ol+rIQFJ0aSBA4UTtbvssFyYqoZ5cyCtTyZuSxjIYIvf3ZNH+f/8QRzAkUhAqbru+0URqGy0xAkEAjX7j5ROFTyTFNwKpZyXTSoHWDeeh77KC2tavbyK6gHCzXfFxDeOP7VBvwZmrDIGG0PovxhOxaAgMToGjZKTcmQJAP/LU3GMhM3s9zbCj99kWWRmzejl2uECj7YSj8qqwNC6MRIMJ0eX5pc+3aaOcsS6bqVD+nbmguGoM57x+6wQhnA==";
    public static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHWcFya290PvyT+2aBtdUP6PMXO87jfqeXm2Vrsa8jT5+nQi7/5JdVt7EJJcAJgvPPFCGyWXF8w5xKjjvdxTolv5xTqgYU9uKuZGL9XCpAHrhzKeEAInF10J6RyosnV574gezt3QlbG4vTt8/VzOow7iSjHhDjDC5pombhQaRWfwIDAQAB";
    /**
     * 加密算法
     */
    private static final String CIPHER_DE = "RSA";
    /**
     * 解密算法
     */
    private static final String CIPHER_EN = "RSA";
    /**
     * 密钥长度
     */
    private static final Integer KEY_LENGTH = 1024;

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 生成秘钥对，公钥和私钥
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Map<String, Object> genKeyPair() throws NoSuchAlgorithmException {
        Map<String, Object> keyMap = new HashMap<>();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(KEY_LENGTH); // 秘钥字节数
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        keyMap.put(PUBLICK_EY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     * @throws InvalidKeySpecException
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        // 得到公钥
        byte[] keyBytes = Base64.decodeBase64(publicKey.getBytes());
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        Key key = keyFactory.generatePublic(x509EncodedKeySpec);
        // 加密数据，分段加密
        Cipher cipher = Cipher.getInstance(CIPHER_EN);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        int inputLength = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        while (inputLength - offset > 0) {
            if (inputLength - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offset, inputLength - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String privateKey) {
        // 得到私钥
        byte[] keyBytes = Base64.decodeBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            Key key = keyFactory.generatePrivate(pKCS8EncodedKeySpec);
            // 解密数据，分段解密
            Cipher cipher = Cipher.getInstance(CIPHER_DE);
            cipher.init(Cipher.DECRYPT_MODE, key);
            int inputLength = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offset = 0;
            byte[] cache;
            int i = 0;
            byte[] tmp;
            while (inputLength - offset > 0) {
                if (inputLength - offset > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offset, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offset, inputLength - offset);
                }
                out.write(cache);
                i++;
                offset = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return decryptedData;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取公钥
     *
     * @param keyMap
     * @return
     */
    public static String getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PUBLICK_EY);
        String str = new String(Base64.encodeBase64(key.getEncoded()));
        return str;
    }

    /**
     * 获取私钥
     *
     * @param keyMap
     * @return
     */
    public static String getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        String str = new String(Base64.encodeBase64(key.getEncoded()));
        return str;
    }

    public static void main(String[] args) throws Exception {
//        Map<String, Object> keyMap = RSAUtil.genKeyPair();
//        String publicKey = RSAUtil.getPublicKey(keyMap);
//        String privateKey = RSAUtil.getPrivateKey(keyMap);
//        System.out.println("公钥：" + publicKey);
//        System.out.println("私钥：" + privateKey);
//        // 公钥加密
//        String sourceStr = "111";
//        System.out.println("加密前：" + sourceStr);
//        byte[] encryptStrByte = RSAUtil.encryptByPublicKey(sourceStr.getBytes(), publicKey);
//        byte[] btt = Base64.encodeBase64(encryptStrByte);
//        String encryptStr = new String(btt);
//        System.out.println("加密后：" + encryptStr);
//        System.out.println("长度：" + encryptStr.length());

        // 私钥解密
        String encryptStr = "GFdX9Sr+cmOGRHqI4PWOfSQJXlai9AAr1tbPljuVtyLh/lHcb18Lvkw3+Pgr460OO82yZBC/79QfwD8qSNgzilVkm/doQzio5+Q9ig0u7FjqmDhzMBP5vqnCXesUOe0HwCFMjfYAJfde00byI0H+J9H5JQo5qPmHCiMoeOO22jE=";
        byte[] decryptStrByte = RSAUtil.decryptByPrivateKey(Base64.decodeBase64(encryptStr), privateKey);
        String sourceStr_1 = new String(decryptStrByte);
        System.out.println("解密后：" + sourceStr_1);

    }
}
