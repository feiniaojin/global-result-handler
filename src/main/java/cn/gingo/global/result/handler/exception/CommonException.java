package cn.gingo.global.result.handler.exception;

import cn.gingo.global.result.handler.annotation.ExceptionMapper;

public class CommonException {

    @ExceptionMapper(code = 1404, msg = "Not found！")
    public static class NotFoundException extends RuntimeException {
    }

    @ExceptionMapper(code = 1400, msg = "Illegal Argument！")
    public static class IllegalArgumentException extends RuntimeException {
    }
}
