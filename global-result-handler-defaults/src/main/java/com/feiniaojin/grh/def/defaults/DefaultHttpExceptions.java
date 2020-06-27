package com.feiniaojin.grh.def.defaults;

import com.feiniaojin.grh.def.ExceptionMapper;

/**
 * 默认处理后包装的http协议异常.
 *
 * @author <a href="mailto:943868899@qq.com">feiniaojin</a>
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
