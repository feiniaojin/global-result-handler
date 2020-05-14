package com.feiniaojin.grh.exception;

import com.feiniaojin.grh.annotation.ExceptionMapper;

/**
 * 公共的异常类.
 *
 * @author feiniaojin
 */
public class CommonExceptions {

  @ExceptionMapper(code = 1404, msg = "Not found!")
  public static class NotFoundException extends RuntimeException {
  }

  @ExceptionMapper(code = 1400, msg = "Illegal Argument!")
  public static class IllegalArgumentException extends RuntimeException {
  }
}
