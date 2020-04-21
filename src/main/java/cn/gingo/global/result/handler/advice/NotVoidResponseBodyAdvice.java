package cn.gingo.global.result.handler.advice;

import cn.gingo.global.result.handler.bean.ResponseBean;
import cn.gingo.global.result.handler.config.GlobalResultHandlerConfigProperties;
import cn.gingo.global.result.handler.checkers.SupportChecker;
import cn.gingo.global.result.handler.checkers.SupportCheckerHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;

/**
 * 非空返回值的处理.
 *
 * @author qinyujie
 * @version 0.1
 * @since 0.1
 */
@ControllerAdvice
@Slf4j
@Order(value = 1000)
public class NotVoidResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Resource
    private GlobalResultHandlerConfigProperties config;

    @Resource
    private SupportCheckerHolder supportCheckerHolder;

    /**
     * 只处理不返回void的，并且MappingJackson2HttpMessageConverter支持的类型.
     *
     * @param methodParameter 方法参数
     * @param clazz           处理器
     * @return 是否支持
     */
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> clazz) {

        //因为是要找出不必处理的，只要checker找出一个假的，那么就不能再支持了，必须返回false强制不再处理
        for (SupportChecker supportChecker : supportCheckerHolder.getSupportCheckers()) {
            if (!supportChecker.check(methodParameter, clazz)) {
                return false;
            }
        }

        return !methodParameter.getMethod().getReturnType().equals(Void.TYPE)
                && MappingJackson2HttpMessageConverter.class.isAssignableFrom(clazz);
    }

    public Object beforeBodyWrite(Object body,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> clazz,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        if (body == null) {
            return new ResponseBean();
        } else if (!(body instanceof ResponseBean)) {
            return new ResponseBean(body);
        } else {
            return body;
        }
    }
}
