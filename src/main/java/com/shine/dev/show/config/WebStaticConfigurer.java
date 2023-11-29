package com.shine.dev.show.config;

import com.shine.dev.show.aop.AccessInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebStaticConfigurer
 *
 * @author leihz
 * @version 1.0.0
 * @since 2023/11/14 11:18
 */
@Configuration
public class WebStaticConfigurer implements WebMvcConfigurer {

//  @Override
//  public void addResourceHandlers(ResourceHandlerRegistry registry) {
//    registry.addResourceHandler("/**").addResourceLocations("classpath:/templates/");
//  }

  @Bean
  public AccessInterceptor clientInfoInterceptor() {
    return new AccessInterceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // 添加拦截器，拦截所有请求
    registry.addInterceptor(clientInfoInterceptor()).addPathPatterns("/**");
  }
}
