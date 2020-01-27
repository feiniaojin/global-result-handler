package cn.gingo.global.result.handler;

import cn.gingo.global.result.handler.annotation.ExceptionMapper;
import cn.gingo.global.result.handler.bean.ResponseBean;

/**
 * 通过异常生成统一响应的工具类.
 *
 * @author qinyujie
 * @version 0.1
 * @since 0.1
 */
public final class ResponseBeans {

  private ResponseBeans() {
  }

  /**
   * 从异常类生成返回结果.
   *
   * @param clazz Throwable的子类Class
   * @return {@code ResponseBean}.
   */
  public static ResponseBean fromException(Class<? extends Throwable> clazz) {

    ExceptionMapper exceptionMapper = clazz.getAnnotation(ExceptionMapper.class);
    return fromExceptionMapper(exceptionMapper);
  }

  /**
   * 从异常类的实例生成返回结果.
   *
   * @param t   Throwable的子类的实例.
   * @param <T> Throwable的子类.
   * @return {@code ResponseBean}.
   */
  public static <T extends Throwable> ResponseBean fromException(T t) {

    return fromException(t.getClass());
  }


  /**
   * 从ExceptionMapper 直接生成响应.
   *
   * @param exceptionMapper {@code ExceptionMapper}
   * @return ResponseBean
   */
  public static ResponseBean fromExceptionMapper(ExceptionMapper exceptionMapper) {

    ResponseBean bean = new ResponseBean();
    bean.setCode(exceptionMapper.code());
    bean.setMsg(exceptionMapper.msg());

    return bean;
  }
}
