package cn.gingo.global.result.handler.advice;

import cn.gingo.global.result.handler.bean.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

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


  /**
   * 只处理返回空的Controller方法.
   *
   * @param returnType    返回类型
   * @param converterType 消息转换器
   * @return 是否对这种返回值进行处理
   */
  @Override
  public boolean supports(MethodParameter returnType,
                          Class<? extends HttpMessageConverter<?>> converterType) {
    return returnType.getMethod().getReturnType().equals(Void.TYPE);
  }

  @Override
  public Object beforeBodyWrite(Object body,
                                MethodParameter returnType,
                                MediaType selectedContentType,
                                Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                ServerHttpRequest request,
                                ServerHttpResponse response) {
    return new ResponseBean();
  }
}