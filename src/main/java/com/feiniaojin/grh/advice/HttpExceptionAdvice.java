package com.feiniaojin.grh.advice;

import com.feiniaojin.grh.ResponseBeans;
import com.feiniaojin.grh.bean.ResponseBean;
import com.feiniaojin.grh.exception.DefaultHttpExceptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Http异常的处理.
 *
 * @author <a href="mailto:943868899@qq.com">feiniaojin</a>
 * @version 0.1
 * @since 0.1
 */
@ControllerAdvice
@Slf4j
@Order(value = 1010)
public class HttpExceptionAdvice {

  /**
   * NoHandlerFoundException 404 异常处理.
   *
   * @param exception Http异常.
   * @return ResponseBean
   */
  @ExceptionHandler(value = NoHandlerFoundException.class)
  @ResponseBody
  public ResponseBean handleNoHandlerFoundException(NoHandlerFoundException exception) {
    return ResponseBeans.fromException(DefaultHttpExceptions.NotFoundException.class);
  }

  /**
   * HttpRequestMethodNotSupportedException 405 异常处理.
   *
   * @param exception Http异常.
   * @return ResponseBean
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseBody
  public ResponseBean handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException exception) {
    return ResponseBeans.fromException(DefaultHttpExceptions.MethodNotSupportedException.class);
  }

  /**
   * HttpMediaTypeNotSupportedException 415 异常处理.
   *
   * @param exception Http异常.
   * @return 统一响应
   */
  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  @ResponseBody
  public ResponseBean handleHttpMediaTypeNotSupportedException(
      HttpMediaTypeNotSupportedException exception) {
    return ResponseBeans.fromException(DefaultHttpExceptions.MediaTypeNotSupportedException.class);
  }
}
