package com.shine.dev.show.common;

import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Encryptor
 *
 * @author leihz
 * @version 1.0.0
 * @since 2023/11/13 14:54
 */
public class Encryptor {

  private static final String AES_ALGORITHM = "AES";
  private static final String WEB_KEY_SECRET = "ZGV2c2hpbmVzaGluZWRldg==";
  private static String WEB_KEY;

  static {
    WEB_KEY = new String(Base64.getDecoder().decode(WEB_KEY_SECRET));
  }

  @SneakyThrows
  public static String encrypt(String plaintext) {
    return encrypt(plaintext, WEB_KEY);
  }

  @SneakyThrows
  public static String decrypt(String ciphertext) {
    return decrypt(ciphertext, WEB_KEY);
  }

  public static String encrypt(String plaintext, String key) throws Exception {
    SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);
    Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
    return Base64.getEncoder().encodeToString(encryptedBytes);
  }

  public static String decrypt(String ciphertext, String key) throws Exception {
    SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);
    Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
    cipher.init(Cipher.DECRYPT_MODE, secretKey);
    byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
    return new String(decryptedBytes, StandardCharsets.UTF_8);
  }

  public static void main(String[] args) {
    try {
      String plaintext = "^98uy^5GGh@";
      //String key = "ThisIsASecretKey";

      // 加密
      String encryptedText = encrypt(plaintext);
      System.out.println("加密后的内容：" + encryptedText);

      // 解密
      String decryptedText = decrypt(encryptedText);
      System.out.println("解密后的内容：" + decryptedText);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
