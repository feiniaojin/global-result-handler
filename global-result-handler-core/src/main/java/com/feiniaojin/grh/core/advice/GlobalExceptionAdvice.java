package com.feiniaojin.grh.core.advice;

import com.feiniaojin.grh.core.config.GlobalResultHandlerConfigProperties;
import com.feiniaojin.grh.core.defaults.DefaultResponseMeta;
import com.feiniaojin.grh.def.ExceptionMapper;
import com.feiniaojin.grh.def.ResponseBean;
import com.feiniaojin.grh.def.ResponseBeanFactory;
import com.feiniaojin.grh.def.ResponseMetaFactory;
import com.feiniaojin.grh.def.ValidationExceptionConverter;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Order(value = 1020)
public class GlobalExceptionAdvice {

  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

  @Resource
  private ValidationExceptionConverter validationExceptionConverter;

  @Resource
  private ResponseMetaFactory responseMetaFactory;

  @Resource
  private ResponseBeanFactory responseBeanFactory;

  @Resource
  private GlobalResultHandlerConfigProperties properties;

  /**
   * 异常处理逻辑.
   *
   * @param exception 业务逻辑抛出的异常
   * @return 统一返回包装后的结果
   */
  @ExceptionHandler({Exception.class})
  @ResponseBody
  public ResponseBean exceptionHandler(Exception exception) {

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(exception.getMessage(), exception);
    }

    //校验异常转自定义异常
    Class<? extends Exception> customExceptionClass = validationExceptionConverter.convert(exception.getClass());

    if (customExceptionClass != null) {
      return generateValidationResponse(exception, customExceptionClass);
    } else {
      customExceptionClass = exception.getClass();
    }

    return responseBeanFactory.newFailInstance(customExceptionClass);
  }

  private ResponseBean generateValidationResponse(Exception exception, Class<? extends Exception> customExceptionClass) {

    ResponseBean response = responseBeanFactory.newInstance();

    //根据异常匹配注解获取异常码和消息
    ExceptionMapper exceptionMapper = customExceptionClass.getAnnotation(ExceptionMapper.class);

    //填充错误码
    if (exceptionMapper != null) {
      response.setCode(exceptionMapper.code());
    } else {
      response.setMsg(responseMetaFactory.fail().getMsg());
    }
    //填充错误信息
    if (!properties.isUseValidationMsg()) {
      response.setMsg(exceptionMapper.msg());
      return response;
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
      response.setMsg(sb.toString());
      return response;
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
      response.setMsg(sb.toString());
      return response;
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
      response.setMsg(sb.toString());
      return response;
    }

    response.setMsg(responseMetaFactory.fail().getMsg());
    return response;
  }
}
