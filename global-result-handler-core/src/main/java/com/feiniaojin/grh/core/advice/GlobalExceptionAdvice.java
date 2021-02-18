package com.feiniaojin.grh.core.advice;

import com.feiniaojin.grh.def.ResponseBean;
import com.feiniaojin.grh.def.ResponseBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 全局异常处理.
 *
 * @author <a href="mailto:943868899@qq.com">Yujie</a>
 * @version 0.1
 * @since 0.1
 */
@ControllerAdvice
@Order(value = 1200)
public class GlobalExceptionAdvice {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    @Resource
    private ResponseBeanFactory responseBeanFactory;

    /**
     * 异常处理逻辑.
     *
     * @param exception 业务逻辑抛出的异常
     * @return 统一返回包装后的结果
     */
    @ExceptionHandler({Throwable.class})
    @ResponseBody
    public ResponseBean exceptionHandler(Exception exception) {

        if (logger.isDebugEnabled()) {
            logger.debug(exception.getMessage(), exception);
        }

        //校验异常转自定义异常
        Class<? extends Exception> customExceptionClass = exception.getClass();
        return responseBeanFactory.newFailInstance(customExceptionClass);
    }
}
