package com.feiniaojin.grh;

import com.feiniaojin.grh.config.GlobalResultHandlerAutoConfig;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;


/**
 * 注解启动全局结果处理的入口.
 *
 * @author feiniaojin
 * @version 0.1
 * @since 0.1
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(GlobalResultHandlerAutoConfig.class)
public @interface EnableGlobalResultHandler {
}
