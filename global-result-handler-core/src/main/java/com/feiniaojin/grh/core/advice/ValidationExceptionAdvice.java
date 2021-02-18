package com.feiniaojin.grh.core.advice;

import com.feiniaojin.grh.def.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

/**
 * 针对参数校验异常进行处理
 *
 * @author: <a href=mailto:943868899@qq.com>Yujie</a>
 */

@ControllerAdvice
@Order(value = 1100)
public class ValidationExceptionAdvice {

    private final Logger logger = LoggerFactory.getLogger(ValidationExceptionAdvice.class);

    @Resource
    private ValidationExceptionConverter validationExceptionConverter;

    @Resource
    private ResponseMetaFactory responseMetaFactory;

    @Resource
    private ResponseBeanFactory responseBeanFactory;

    /**
     * 参数异常转换成系统自定义异常.
     *
     * @param throwable Http异常.
     * @return ResponseBean
     */
    @ExceptionHandler(value = {BindException.class,
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class})
    @ResponseBody
    public ResponseBean handleValidationException(Throwable throwable) throws Throwable {

        Class<? extends Throwable> customExceptionClass = validationExceptionConverter.convert(throwable.getClass());
        if (customExceptionClass == null) {
            throw throwable;
        }

        if (logger.isDebugEnabled()) {
            logger.debug(throwable.getMessage(), throwable);
        }

        //处理校验异常逻辑
        return generateValidationResponse(throwable, customExceptionClass);
    }

    private ResponseBean generateValidationResponse(Throwable exception,
                                                    Class<? extends Throwable> customExceptionClass) {

        ResponseBean response = responseBeanFactory.newInstance();

        //根据异常匹配注解获取异常码和消息
        ExceptionMapper exceptionMapper = customExceptionClass.getAnnotation(ExceptionMapper.class);

        //填充错误码
        if (exceptionMapper != null) {
            response.setCode(exceptionMapper.code());
        } else {
            response.setCode(responseMetaFactory.defaultFail().getCode());
        }

        //参数校验时的异常
        if (exception instanceof BindException) {
            StringJoiner stringJoiner = new StringJoiner("|");
            BindException exs = (BindException) exception;
            List<ObjectError> errors = exs.getAllErrors();
            errors.forEach(e -> stringJoiner.add(e.getDefaultMessage()));
            response.setMsg(stringJoiner.toString());
            return response;
        } else if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exs = (MethodArgumentNotValidException) exception;
            List<ObjectError> errors = exs.getBindingResult().getAllErrors();
            StringJoiner stringJoiner = new StringJoiner("|");
            errors.forEach(e -> stringJoiner.add(e.getDefaultMessage()));
            response.setMsg(stringJoiner.toString());
            return response;
        } else if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) exception;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            StringJoiner stringJoiner = new StringJoiner("|");
            violations.forEach(e -> stringJoiner.add(e.getMessage()));
            response.setMsg(stringJoiner.toString());
            return response;
        }

        response.setMsg(responseMetaFactory.defaultFail().getMsg());
        return response;
    }
}
