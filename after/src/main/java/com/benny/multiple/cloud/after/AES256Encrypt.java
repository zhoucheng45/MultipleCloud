package com.benny.multiple.cloud.after;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
@Slf4j
public class AES256Encrypt {

  private static final String ALGORITHM = "AES/ECB/PKCS5Padding"; 
  private static final byte[] KEY = "12345678901234561234567890123456".getBytes();

  public static String encrypt(String input){
    try {
      Cipher cipher = Cipher.getInstance(ALGORITHM);
      SecretKeySpec key = new SecretKeySpec(KEY, "AES");
      cipher.init(Cipher.ENCRYPT_MODE, key);
      byte[] encrypted = cipher.doFinal(input.getBytes());
      return Base64.encodeBase64String(encrypted);
    } catch (Exception e) {
      log.error("AES加密失败");
      throw new RuntimeException(e);
    }
  }

  public static String decrypt(String input) {
    try {
      Cipher cipher = Cipher.getInstance(ALGORITHM);
      SecretKeySpec key = new SecretKeySpec(KEY, "AES");
      cipher.init(Cipher.DECRYPT_MODE, key);
      byte[] decrypted = cipher.doFinal(Base64.decodeBase64(input));
      return new String(decrypted);
    }catch (Exception e){
      log.error("AES decrypt fail!, input="+input, e);
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) {
    String original = "Hwcloud@123";
    String encrypted = encrypt(original);
    String decrypted = decrypt(encrypted);
    
    System.out.println("Original message: " + original);
    System.out.println("Encrypted message: " + encrypted);
    System.out.println("Decrypted message: " + decrypted);
  }
}