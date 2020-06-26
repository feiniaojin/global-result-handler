package com.feiniaojin.grh.def;

/**
 * 异常转换器，将异常类转换为自定义异常类.
 *
 * @author <a href="mailto:qinyujie@gingo.cn">Yujie</a>
 * @version 0.1
 */
public interface ExceptionConverter {

  /**
   * 将异常转为另一种异常.
   *
   * @param inputExceptionClass 待转换的异常类Class
   * @return
   */
  Class<? extends Exception> convert(Class<? extends Exception> inputExceptionClass);
}
