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
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;

/**
 * 空返回值的拦截处理.
 *
 * @author qinyujie
 * @version 0.1
 * @since 0.1
 */
@ControllerAdvice
@Slf4j
@Order(value = 1000)
public class VoidResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Resource
    private GlobalResultHandlerConfigProperties config;

    @Resource
    private SupportCheckerHolder supportCheckerHolder;

    /**
     * 只处理返回空的Controller方法.
     *
     * @param methodParameter 返回类型
     * @param clazz           消息转换器
     * @return 是否对这种返回值进行处理
     */
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> clazz) {

        //因为是要找出不必处理的，只要checker找出一个假的，那么就不能再支持了，必须返回false强制不再处理
        for (SupportChecker supportChecker : supportCheckerHolder.getSupportCheckers()) {
            if (!supportChecker.check(methodParameter, clazz)) {
                return false;
            }
        }

        return methodParameter.getMethod().getReturnType().equals(Void.TYPE);
    }

    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        return new ResponseBean();
    }
}