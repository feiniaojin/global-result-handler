package com.feiniaojin.grh.advice;

import com.feiniaojin.grh.annotation.ExceptionMapper;
import com.feiniaojin.grh.bean.ResponseBean;
import com.feiniaojin.grh.config.GlobalResultHandlerConfigProperties;
import com.feiniaojin.grh.enums.DefaultResponseCode;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理.
 *
 * @author <a href="mailto:943868899@qq.com">feiniaojin</a>
 * @version 0.1
 * @since 0.1
 */
@ControllerAdvice
@Slf4j
@Order(value = 1020)
public class  GlobalExceptionAdvice {

  @Resource
  private GlobalResultHandlerConfigProperties config;

  /**
   * 异常处理逻辑.
   *
   * @param exception 业务逻辑抛出的异常
   * @return 统一返回包装后的结果
   */
  @ExceptionHandler({Throwable.class})
  @ResponseBody
  public ResponseBean exceptionHandler(Exception exception) {

    if (log.isDebugEnabled()) {
      log.debug(exception.getMessage(), exception);
    }

    ResponseBean bean = new ResponseBean();

    //根据异常匹配注解获取异常码和消息
    ExceptionMapper exceptionMapper = exception.getClass().getAnnotation(ExceptionMapper.class);
    if (exceptionMapper != null) {
      bean.setCode(exceptionMapper.code());
      bean.setMsg(exceptionMapper.msg());
      return bean;
    }

    //参数校验时的异常
    if (exception instanceof BindException) {
      BindException exs = (BindException) exception;
      List<ObjectError> errors = exs.getAllErrors();
      StringBuilder sb = new StringBuilder("");
      for (ObjectError error : errors) {
        sb.append(error.getDefaultMessage());
        sb.append("|");
      }
      sb.deleteCharAt(sb.lastIndexOf("|"));
      bean.setMsg(sb.toString());
      bean.setCode(DefaultResponseCode.DEFAULT_FAIL.getCode());
      return bean;
    }

    if (exception instanceof MethodArgumentNotValidException) {
      MethodArgumentNotValidException exs = (MethodArgumentNotValidException) exception;
      List<ObjectError> allErrors = exs.getBindingResult().getAllErrors();
      StringBuilder sb = new StringBuilder("");
      for (ObjectError oe : allErrors) {
        sb.append(oe.getDefaultMessage());
        sb.append("|");
      }
      sb.deleteCharAt(sb.lastIndexOf("|"));
      bean.setMsg(sb.toString());
      bean.setCode(DefaultResponseCode.DEFAULT_FAIL.getCode());
      return bean;
    }

    if (exception instanceof ConstraintViolationException) {
      ConstraintViolationException exs = (ConstraintViolationException) exception;
      Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
      StringBuilder sb = new StringBuilder("");
      for (ConstraintViolation<?> item : violations) {
        sb.append(item.getMessage());
        sb.append("|");
      }
      sb.deleteCharAt(sb.lastIndexOf("|"));
      bean.setMsg(sb.toString());
      bean.setCode(DefaultResponseCode.DEFAULT_FAIL.getCode());
      return bean;
    }

    //给不按套路写代码的同事准备的
    bean.setCode(DefaultResponseCode.DEFAULT_FAIL.getCode());
    bean.setMsg(DefaultResponseCode.DEFAULT_FAIL.getMsg());
    return bean;
  }
}
