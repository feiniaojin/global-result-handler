package com.feiniaojin.grh.config;

import com.feiniaojin.grh.check.SwaggerChecker;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 全局返回值处理的自动配置.
 *
 * @author feiniaojin
 * @version 0.1
 * @since 0.1
 */
@Configuration
@EnableConfigurationProperties(GlobalResultHandlerConfigProperties.class)
@ComponentScan(basePackages = {"com.feiniaojin.grh.advice"})
public class GlobalResultHandlerAutoConfig {

  @Bean
  public SwaggerChecker swaggerChecker() {
    return new SwaggerChecker();
  }
}
