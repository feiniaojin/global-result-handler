package com.feiniaojin.grh.starter.config;

import com.feiniaojin.grh.core.support.SwaggerChecker;
import com.feiniaojin.grh.core.config.GlobalResultHandlerConfigProperties;
import com.feiniaojin.grh.core.support.SwaggerResponseBodyAdvice;
import com.feiniaojin.grh.def.HttpExceptionConverter;
import com.feiniaojin.grh.def.ResponseBeanFactory;
import com.feiniaojin.grh.def.ResponseMetaFactory;
import com.feiniaojin.grh.def.ValidationExceptionConverter;
import com.feiniaojin.grh.def.defaults.DefaultHttpExceptionConverter;
import com.feiniaojin.grh.def.defaults.DefaultResponseBeanFactory;
import com.feiniaojin.grh.def.defaults.DefaultResponseMetaFactory;
import com.feiniaojin.grh.def.defaults.DefaultValidationExceptionConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
  @ConditionalOnBean(name = {"apiResourceController",
      "swagger2Controller"})
  public SwaggerChecker swaggerChecker() {
    return new SwaggerChecker();
  }

  @Bean
  @ConditionalOnBean(name = {"swaggerChecker"})
  public SwaggerResponseBodyAdvice swaggerResponseBodyAdvice() {
    return new SwaggerResponseBodyAdvice();
  }

  @Bean
  @ConditionalOnMissingBean(value = {ResponseBeanFactory.class})
  public ResponseBeanFactory responseBeanFactory() {
    return new DefaultResponseBeanFactory();
  }

  @Bean
  @ConditionalOnMissingBean(value = {HttpExceptionConverter.class})
  public HttpExceptionConverter httpExceptionConverter() {
    return new DefaultHttpExceptionConverter();
  }

  @Bean
  @ConditionalOnMissingBean(value = {ValidationExceptionConverter.class})
  public ValidationExceptionConverter validationExceptionConverter() {
    return new DefaultValidationExceptionConverter();
  }

  @Bean
  @ConditionalOnMissingBean(value = {ResponseMetaFactory.class})
  public DefaultResponseMetaFactory responseMetaFactory() {
    return new DefaultResponseMetaFactory();
  }
}
