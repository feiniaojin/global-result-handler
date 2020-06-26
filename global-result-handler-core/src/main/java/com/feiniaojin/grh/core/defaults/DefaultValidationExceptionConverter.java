package com.feiniaojin.grh.core.defaults;

import com.feiniaojin.grh.def.ValidationExceptionConverter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 默认的校验异常转换器.
 *
 * @author <a href="mailto:qinyujie@gingo.cn">Yujie</a>
 * @version 0.1
 */
public class DefaultValidationExceptionConverter implements ValidationExceptionConverter {
  public final Map<Class<? extends Exception>, Class<? extends Exception>> mapping
      = new ConcurrentHashMap<>();

  {
    mapping.put(NoHandlerFoundException.class, DefaultHttpExceptions.NotFoundException.class);
  }

  @Override
  public Class<? extends Exception> convert(Class<? extends Exception> inputExceptionClass) {
    return mapping.get(inputExceptionClass);
  }
}
