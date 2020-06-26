package com.feiniaojin.grh.core.defaults;

import com.feiniaojin.grh.def.HttpExceptionConverter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Http异常转换器，将Http异常转换为自定义异常.
 *
 * @author <a href="mailto:qinyujie@gingo.cn">Yujie</a>
 * @version 0.1
 */
public class DefaultHttpExceptionConverter implements HttpExceptionConverter {

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
