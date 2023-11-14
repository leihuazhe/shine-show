package com.shine.dev.show.controller;

import com.shine.dev.show.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * ImageController
 *
 * @author leihz
 * @version 1.0.0
 * @since 2023/11/10 15:59
 */
@RestController
public class ImageController {

  @Autowired
  private ImageService imageService;

  @PostMapping("/in")
  public String uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
    return imageService.upload(file);
  }

  @GetMapping("/out/{fileName}")
  public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName) throws Exception {
    byte[] image = imageService.download(fileName);
    return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.valueOf("image/png"))
        .body(image);
  }
}
