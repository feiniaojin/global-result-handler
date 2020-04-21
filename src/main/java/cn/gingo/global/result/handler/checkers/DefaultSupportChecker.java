package cn.gingo.global.result.handler.checkers;

import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;


/**
 * 默认支持检查其，默认都是支持的
 */
public class DefaultSupportChecker implements SupportChecker {

    @Override
    public boolean check(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> clazz) {
        return true;
    }
}
