package com.feiniaojin.grh.core.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 用于支持Swagger2.
 *
 * @author <a href="mailto:943868899@qq.com">feiniaojin</a>
 */
@ControllerAdvice
@Order(value = 900)
public class SwaggerResponseBodyAdvice implements ResponseBodyAdvice<Object> {

  private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerResponseBodyAdvice.class);

  @Autowired(required = false)
  private SwaggerChecker swaggerChecker = null;

  @Override
  public boolean supports(MethodParameter methodParameter,
                          Class<? extends HttpMessageConverter<?>> clazz) {
    if (swaggerChecker != null &&
        swaggerChecker.isSwaggerClass(methodParameter.getContainingClass())) {
      return true;
    }

    return false;
  }

  @Override
  public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
                                Class<? extends HttpMessageConverter<?>> clazz,
                                ServerHttpRequest serverHttpRequest,
                                ServerHttpResponse serverHttpResponse) {

    return o;
  }
}
