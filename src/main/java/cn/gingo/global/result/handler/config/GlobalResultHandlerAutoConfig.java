package cn.gingo.global.result.handler.config;

import cn.gingo.global.result.handler.advice.GlobalExceptionAdvice;
import cn.gingo.global.result.handler.advice.HttpExceptionAdvice;
import cn.gingo.global.result.handler.advice.NotVoidResponseBodyAdvice;
import cn.gingo.global.result.handler.advice.VoidResponseBodyAdvice;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 全局返回值处理的自动配置.
 * @author qinyujie
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
  public NotVoidResponseBodyAdvice notVoidResponseBodyAdvice() {
    return new NotVoidResponseBodyAdvice();
  }

  @Bean
  public VoidResponseBodyAdvice voidResponseBodyAdvice() {
    return new VoidResponseBodyAdvice();
  }

  @Bean
  public HttpExceptionAdvice httpExceptionAdvice() {
    return new HttpExceptionAdvice();
  }


}
