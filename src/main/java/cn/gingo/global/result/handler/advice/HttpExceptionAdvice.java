package cn.gingo.global.result.handler.advice;

import cn.gingo.global.result.handler.ResponseBeans;
import cn.gingo.global.result.handler.bean.ResponseBean;
import cn.gingo.global.result.handler.exception.DefaultHttpExceptions;
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
 * @author qinyujie
 * @version 0.1
 * @since 0.1
 */
@ControllerAdvice
@Slf4j
@Order(value = 10)
public class HttpExceptionAdvice {

  /**
   * NoHandlerFoundException 404 异常处理.
   *
   * @param exception Http异常.
   * @return ResponseBean
   */
  @ExceptionHandler(value = NoHandlerFoundException.class)
  @ResponseBody
  public ResponseBean handlerNoHandlerFoundException(NoHandlerFoundException exception) {
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
  public ResponseBean handlerHttpRequestMethodNotSupportedException(
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
  public ResponseBean handlerHttpMediaTypeNotSupportedException(
      HttpMediaTypeNotSupportedException exception) {
    return ResponseBeans.fromException(DefaultHttpExceptions.MediaTypeNotSupportedException.class);
  }
}
