package com.feiniaojin.grh.starter.config;

import com.feiniaojin.grh.core.check.SwaggerChecker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 全局返回值处理的自动配置.
 *
 * @author <a href="mailto:943868899@qq.com">feiniaojin</a>
 * @version 0.1
 * @since 0.1
 */
@Configuration
@EnableConfigurationProperties(GlobalResultHandlerConfigProperties.class)
@ComponentScan(basePackages = {"com.feiniaojin.grh.core.advice"})
public class GlobalResultHandlerAutoConfig {

  @Bean
  @ConditionalOnClass(name = {"springfox.documentation.swagger.web.ApiResourceController",
      "springfox.documentation.swagger2.web.Swagger2Controller"})
  public SwaggerChecker swaggerChecker() {
    return new SwaggerChecker();
  }
}
