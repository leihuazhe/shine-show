package com.shine.dev.show.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AccessInterceptor
 *
 * @author leihz
 * @version 1.0.0
 * @since 2023/11/29 16:01
 */
@Slf4j
public class AccessInterceptor implements HandlerInterceptor {



  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    // 获取客户端的IP地址
    String clientIP = request.getRemoteAddr();
    // 获取User-Agent信息
    String userAgent = request.getHeader("User-Agent");
    // 将信息存储到request中，以便在Controller中使用
    request.setAttribute("clientIP", clientIP);
    request.setAttribute("userAgent", userAgent);

    log.info("Client IP: {}, User-Agent: {}", clientIP, userAgent);
    return true;
  }
}
