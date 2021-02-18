package com.feiniaojin.grh.defaults;

import com.feiniaojin.grh.def.HttpExceptionConverter;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Http异常转换器，将Http异常转换为自定义异常.
 *
 * @author <a href="mailto:943868899@qq.com">Yujie</a>
 * @version 0.1
 */
public class DefaultHttpExceptionConverter implements HttpExceptionConverter {

    public final Map<Class<? extends Throwable>, Class<? extends Throwable>> mapping
            = new ConcurrentHashMap<>();

    public DefaultHttpExceptionConverter() {
        init();
    }

    /**
     * 设置默认的异常匹配
     */
    private void init() {
        mapping.put(NoHandlerFoundException.class,
                DefaultHttpExceptions.NotFoundException.class);
        mapping.put(HttpRequestMethodNotSupportedException.class,
                DefaultHttpExceptions.MethodNotSupportedException.class);
        mapping.put(HttpMediaTypeNotSupportedException.class,
                DefaultHttpExceptions.MediaTypeNotSupportedException.class);
    }

    @Override
    public Class<? extends Throwable> convert(Class<? extends Throwable> originExceptionClass) {
        return mapping.get(originExceptionClass);
    }

    @Override
    public void register(Class<? extends Throwable> originExceptionClass,
                         Class<? extends Throwable> customExceptionClass) {
        mapping.put(originExceptionClass,
                customExceptionClass);
    }
}
