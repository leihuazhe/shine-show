package com.shine.dev.show.service;

import com.shine.dev.show.common.utils.ImageCrypter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * ImageService
 *
 * @author leihz
 * @version 1.0.0
 * @since 2023/11/10 16:04
 */
@Service
public class ImageService {

  public String upload(MultipartFile file) throws IOException {
    return ImageCrypter.encrypt(file);
  }

  public byte[] download(String fileName) throws Exception {
    return ImageCrypter.decrypt(fileName);
  }
}
