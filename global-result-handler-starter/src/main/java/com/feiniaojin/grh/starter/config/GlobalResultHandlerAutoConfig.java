package com.feiniaojin.grh.starter.config;

import com.feiniaojin.grh.core.advice.GlobalExceptionAdvice;
import com.feiniaojin.grh.core.advice.HttpExceptionAdvice;
import com.feiniaojin.grh.core.advice.NotVoidResponseBodyAdvice;
import com.feiniaojin.grh.core.advice.VoidResponseBodyAdvice;
import com.feiniaojin.grh.core.config.GlobalResultHandlerConfigProperties;
import com.feiniaojin.grh.core.support.SwaggerChecker;
import com.feiniaojin.grh.core.support.SwaggerNotVoidResponseBodyAdvice;
import com.feiniaojin.grh.def.HttpExceptionConverter;
import com.feiniaojin.grh.def.ResponseBeanFactory;
import com.feiniaojin.grh.def.ResponseMetaFactory;
import com.feiniaojin.grh.def.ValidationExceptionConverter;
import com.feiniaojin.grh.def.defaults.DefaultHttpExceptionConverter;
import com.feiniaojin.grh.def.defaults.DefaultResponseBeanFactory;
import com.feiniaojin.grh.def.defaults.DefaultResponseMetaFactory;
import com.feiniaojin.grh.def.defaults.DefaultValidationExceptionConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
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
public class GlobalResultHandlerAutoConfig {

  @Bean
  public GlobalExceptionAdvice globalExceptionAdvice() {
    return new GlobalExceptionAdvice();
  }

  @Bean
  public HttpExceptionAdvice httpExceptionAdvice() {
    return new HttpExceptionAdvice();
  }

  @Bean
  public VoidResponseBodyAdvice voidResponseBodyAdvice() {
    return new VoidResponseBodyAdvice();
  }

  @Bean
  @ConditionalOnMissingClass(value = {
      "springfox.documentation.swagger2.annotations.EnableSwagger2"})
  public NotVoidResponseBodyAdvice notVoidResponseBodyAdvice() {
    return new NotVoidResponseBodyAdvice();
  }

  @Bean
  @ConditionalOnClass(name = {
      "springfox.documentation.swagger2.annotations.EnableSwagger2"})
  public SwaggerNotVoidResponseBodyAdvice swaggerNotVoidResponseBodyAdvice() {
    return new SwaggerNotVoidResponseBodyAdvice();
  }

  @Bean
  @ConditionalOnClass(name = {"springfox.documentation.swagger2.annotations.EnableSwagger2"})
  public SwaggerChecker swaggerChecker() {
    return new SwaggerChecker();
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
