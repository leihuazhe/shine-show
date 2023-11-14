package com.shine.dev.show.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebStaticConfigurer
 *
 * @author leihz
 * @version 1.0.0
 * @since 2023/11/14 11:18
 */
//@Configuration
public class WebStaticConfigurer implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/**").addResourceLocations("classpath:/templates/");
  }
}
