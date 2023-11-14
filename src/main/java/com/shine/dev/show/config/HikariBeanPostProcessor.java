package com.shine.dev.show.config;

import com.shine.dev.show.common.Encryptor;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

/**
 * HikariBeanPostProcessor
 *
 * @author leihz
 * @version 1.0.0
 * @since 2023/11/13 15:42
 */
@Configuration
public class HikariBeanPostProcessor implements BeanPostProcessor {

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

    if (bean instanceof HikariDataSource) {
      HikariDataSource dataSource = (HikariDataSource) bean;
      dataSource.setPassword(Encryptor.decrypt(dataSource.getPassword()));
    }
    return bean;
  }
}
