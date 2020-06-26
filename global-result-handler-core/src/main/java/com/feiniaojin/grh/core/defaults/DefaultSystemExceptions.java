package com.feiniaojin.grh.core.defaults;

import com.feiniaojin.grh.def.ExceptionMapper;

/**
 * 默认的系统异常类.
 *
 * @author <a href="mailto:943868899@qq.com">feiniaojin</a>
 */
public class DefaultSystemExceptions {

  @ExceptionMapper(code = 1404, msg = "Not found!")
  public static class NotFoundException extends RuntimeException {
  }

  @ExceptionMapper(code = 1400, msg = "Illegal Argument!")
  public static class IllegalArgumentException extends RuntimeException {
  }
}
