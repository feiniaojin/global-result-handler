package com.feiniaojin.grh.boot.starter;


import com.feiniaojin.grh.core.advice.*;
import com.feiniaojin.grh.core.config.GrhConfigProperties;
import com.feiniaojin.grh.def.HttpExceptionConverter;
import com.feiniaojin.grh.def.ResponseBeanFactory;
import com.feiniaojin.grh.def.ResponseMetaFactory;
import com.feiniaojin.grh.def.ValidationExceptionConverter;
import com.feiniaojin.grh.defaults.DefaultHttpExceptionConverter;
import com.feiniaojin.grh.defaults.DefaultResponseBeanFactory;
import com.feiniaojin.grh.defaults.DefaultResponseMetaFactory;
import com.feiniaojin.grh.defaults.DefaultValidationExceptionConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 全局返回值处理的自动配置.
 *
 * @author <a href="mailto:943868899@qq.com">Yujie</a>
 * @version 0.1
 * @since 0.1
 */
@Configuration
@EnableConfigurationProperties(GrhConfigProperties.class)
public class GrhAutoConfig {

    @Bean
    @ConditionalOnMissingBean(value = GlobalExceptionAdvice.class)
    public GlobalExceptionAdvice globalExceptionAdvice() {
        return new GlobalExceptionAdvice();
    }

    @Bean
    @ConditionalOnMissingBean(HttpExceptionAdvice.class)
    public HttpExceptionAdvice httpExceptionAdvice() {
        return new HttpExceptionAdvice();
    }

    @Bean
    @ConditionalOnMissingBean(NotVoidResponseBodyAdvice.class)
    public NotVoidResponseBodyAdvice notVoidResponseBodyAdvice() {
        return new NotVoidResponseBodyAdvice();
    }

    @Bean
    @ConditionalOnMissingBean(ValidationExceptionAdvice.class)
    public ValidationExceptionAdvice validationExceptionAdvice() {
        return new ValidationExceptionAdvice();
    }

    @Bean
    @ConditionalOnMissingBean(VoidResponseBodyAdvice.class)
    public VoidResponseBodyAdvice voidResponseBodyAdvice() {
        return new VoidResponseBodyAdvice();
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
