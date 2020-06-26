package com.feiniaojin.grh.core.advice;

import com.feiniaojin.grh.core.check.SwaggerChecker;
import com.feiniaojin.grh.def.ResponseBean;
import com.feiniaojin.grh.def.ResponseBeanFactory;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 非空返回值的处理.
 *
 * @author <a href="mailto:943868899@qq.com">feiniaojin</a>
 * @version 0.1
 * @since 0.1
 */
@ControllerAdvice
@Order(value = 1000)
public class NotVoidResponseBodyAdvice implements ResponseBodyAdvice<Object> {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpExceptionAdvice.class);

  @Resource
  private ResponseBeanFactory responseBeanFactory;

  @Resource
  private SwaggerChecker swaggerChecker;

  /**
   * 只处理不返回void的，并且MappingJackson2HttpMessageConverter支持的类型.
   *
   * @param methodParameter 方法参数
   * @param clazz           处理器
   * @return 是否支持
   */
  @Override
  public boolean supports(MethodParameter methodParameter,
                          Class<? extends HttpMessageConverter<?>> clazz) {

    return !methodParameter.getMethod().getReturnType().equals(Void.TYPE)
        && MappingJackson2HttpMessageConverter.class.isAssignableFrom(clazz);
  }

  @Override
  public Object beforeBodyWrite(Object body,
                                MethodParameter methodParameter,
                                MediaType mediaType,
                                Class<? extends HttpMessageConverter<?>> clazz,
                                ServerHttpRequest serverHttpRequest,
                                ServerHttpResponse serverHttpResponse) {
    if (body == null) {
      return responseBeanFactory.newInstance();
    } else if (body instanceof ResponseBean
        || swaggerChecker.isSwaggerClass(body)
        || swaggerChecker.isSwaggerUrl(serverHttpRequest)) {
      return body;
    } else {
      return responseBeanFactory.newSuccessInstance(body);
    }
  }


}
