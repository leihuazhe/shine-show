package com.shine.dev.show.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * StaticController
 *
 * @author leihz
 * @version 1.0.0
 * @since 2023/11/10 17:14
 */
@Controller
public class StaticController {

  @RequestMapping("/to_upload")
  public String toUploadHtml() {
    return "upload_index";
  }
}
