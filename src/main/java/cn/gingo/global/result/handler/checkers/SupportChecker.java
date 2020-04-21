package cn.gingo.global.result.handler.checkers;

import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * 校验是否需要处理的filter
 */
public interface SupportChecker {

    boolean check(MethodParameter methodParameter,
                  Class<? extends HttpMessageConverter<?>> clazz);

}
