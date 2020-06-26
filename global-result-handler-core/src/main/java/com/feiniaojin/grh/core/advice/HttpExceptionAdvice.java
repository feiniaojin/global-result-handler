package com.feiniaojin.grh.core.advice;

import com.feiniaojin.grh.def.ExceptionConverter;
import com.feiniaojin.grh.def.ResponseBean;
import com.feiniaojin.grh.def.ResponseBeanFactory;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Http异常的处理.
 *
 * @author <a href="mailto:943868899@qq.com">feiniaojin</a>
 * @version 0.1
 * @since 0.1
 */
@ControllerAdvice
@Order(value = 1010)
public class HttpExceptionAdvice {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpExceptionAdvice.class);

  @Resource
  private ExceptionConverter exceptionConverter;

  @Resource
  private ResponseBeanFactory responseBeanFactory;

  /**
   * Http异常转换成系统自定义异常.
   *
   * @param exception Http异常.
   * @return ResponseBean
   */
  @ExceptionHandler(value = ServletException.class)
  @ResponseBody
  public ResponseBean handleServletException(ServletException exception) {
    Class<? extends Exception> clazz = exceptionConverter.convert(exception.getClass());
    return responseBeanFactory.newFailInstance(clazz);
  }
}
