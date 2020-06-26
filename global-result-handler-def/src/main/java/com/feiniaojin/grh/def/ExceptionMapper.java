package com.feiniaojin.grh.def;


import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 异常映射注解.
 *
 * @author <a href="mailto:943868899@qq.com">feiniaojin</a>
 * @version 0.3
 * @since 0.3
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExceptionMapper {

  /**
   * 异常对应的错误码.
   *
   * @return 异常对应的错误码
   */
  int code() default 1;

  /**
   * 异常信息.
   *
   * @return 异常对应的提示信息
   */
  String msg() default "Poor network quality!";
}
