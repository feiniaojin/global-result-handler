package com.feiniaojin.grh.advice;

import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
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
 * @date 2020/05/14
 */
@ControllerAdvice
@Slf4j
@Order(value = 900)
@ConditionalOnClass(name = {"springfox.documentation.swagger.web.ApiResourceController",
    "springfox.documentation.swagger2.web.Swagger2Controller"})
public class SwaggerResponseBodyAdvice implements ResponseBodyAdvice<Object> {

  private Set<Class> classSet = new HashSet<>();

  /**
   * 无参构造.
   */
  public SwaggerResponseBodyAdvice() {

    try {
      classSet.add(Class.forName("springfox.documentation.swagger.web.ApiResourceController"));
      classSet.add(Class.forName("springfox.documentation.swagger2.web.Swagger2Controller"));
    } catch (Exception e) {
      log.debug("Can not find Swagger2 Classes", e);
    }
  }

  @Override
  public boolean supports(MethodParameter methodParameter,
                          Class<? extends HttpMessageConverter<?>> clazz) {
    if (classSet.contains(methodParameter.getContainingClass())) {
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
