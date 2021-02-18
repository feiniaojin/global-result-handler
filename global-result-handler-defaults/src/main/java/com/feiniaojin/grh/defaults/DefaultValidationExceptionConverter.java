package com.feiniaojin.grh.defaults;

import com.feiniaojin.grh.def.ValidationExceptionConverter;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的校验异常转换器.
 *
 * @author <a href="mailto:943868899@qq.com">Yujie</a>
 * @version 0.1
 */
public class DefaultValidationExceptionConverter implements ValidationExceptionConverter {
    public final Map<Class<? extends Throwable>, Class<? extends Throwable>> mapping
            = new ConcurrentHashMap<>();

    public DefaultValidationExceptionConverter() {
        init();
    }

    private void init() {
        mapping.put(BindException.class,
                DefaultCustomExceptions.IllegalArgumentException.class);
        mapping.put(MethodArgumentNotValidException.class,
                DefaultCustomExceptions.IllegalArgumentException.class);
        mapping.put(ConstraintViolationException.class,
                DefaultCustomExceptions.IllegalArgumentException.class);
    }

    @Override
    public Class<? extends Throwable> convert(Class<? extends Throwable> inputExceptionClass) {
        return mapping.get(inputExceptionClass);
    }

    @Override
    public void register(Class<? extends Throwable> originExceptionClass,
                         Class<? extends Throwable> customExceptionClass) {
        mapping.put(originExceptionClass, customExceptionClass);
    }
}
