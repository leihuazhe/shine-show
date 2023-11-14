package com.shine.dev.show.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * image 加密/解密
 *
 * @author leihz
 * @version 1.0.0
 * @since 2023/11/10 15:54
 */
public class ImageCrypter {

  //private static final String IMAGE_BASE_PATH = "/root/image/";
  private static final String IMAGE_BASE_PATH = "/Users/maple/logs/";


  private static final String IMAGE_KEY = "IMAGE_KEY_JEFF_2023_11_10";

  public static String encrypt(MultipartFile file) throws IOException {

    String imageId = Tools.uidWithSuffix(file.getOriginalFilename());
    int key = IMAGE_KEY.hashCode();
    byte[] data = file.getBytes();
    int i = 0;
    for (byte b : data) {
      data[i] = (byte) (b ^ key);
      i++;
    }
    // Opening a file for writing purpose
    try (FileOutputStream fos = new FileOutputStream(IMAGE_BASE_PATH + imageId)) {
      fos.write(data);
    }
    return imageId;
  }

  public static byte[] decrypt(String fileName) throws Exception {
    try (FileInputStream fis = new FileInputStream(IMAGE_BASE_PATH + fileName)) {
      byte data[] = new byte[fis.available()];
      // Read the array
      fis.read(data);
      int i = 0;
      int key = IMAGE_KEY.hashCode();
      for (byte b : data) {
        data[i] = (byte) (b ^ key);
        i++;
      }
      return data;
    }
  }

  public static void decrypt(FileInputStream fis) throws IOException {
    // Converting image into byte array,it will
    // Create a array of same size as image.
    byte data[] = new byte[fis.available()];
    // Read the array
    fis.read(data);
    int i = 0;
    // Performing an XOR operation
    // on each value of
    // byte array to Decrypt it.
    int key = IMAGE_KEY.hashCode();
    for (byte b : data) {
      data[i] = (byte) (b ^ key);
      i++;
    }
    // Opening file for writing purpose
    FileOutputStream fos = new FileOutputStream("OUT_PATH");
    // Writing Decrypted data on Image
    fos.write(data);
    fos.close();
    fis.close();
    System.out.println("Decryption Done...");
  }

}
