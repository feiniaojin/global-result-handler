package com.feiniaojin.grh.exception;

import com.feiniaojin.grh.annotation.ExceptionMapper;

/**
 * 默认处理后包装的http协议异常.
 *
 * @author feiniaojin
 * @version 0.1
 * @since 0.1
 */
public class DefaultHttpExceptions {

  @ExceptionMapper(code = 1404, msg = "Not found！")
  public static class NotFoundException extends RuntimeException {

  }

  @ExceptionMapper(code = 1405, msg = "Method Not Supported！")
  public static class MethodNotSupportedException extends RuntimeException {

  }

  @ExceptionMapper(code = 1415, msg = "Media Type Not Supported！")
  public static class MediaTypeNotSupportedException extends RuntimeException {

  }
}
