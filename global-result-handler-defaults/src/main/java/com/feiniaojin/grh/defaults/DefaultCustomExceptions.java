package com.feiniaojin.grh.defaults;


import com.feiniaojin.grh.def.ExceptionMapper;

/**
 * 默认的自定义异常类.
 *
 * @author <a href="mailto:943868899@qq.com">Yujie</a>
 */
public class DefaultCustomExceptions {

    @ExceptionMapper(code = 1404, msg = "Not found!")
    public static class NotFoundException extends RuntimeException {
    }

    @ExceptionMapper(code = 1400, msg = "Illegal Argument!")
    public static class IllegalArgumentException extends RuntimeException {
    }
}
