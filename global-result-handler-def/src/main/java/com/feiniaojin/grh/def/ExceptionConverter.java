package com.feiniaojin.grh.def;

/**
 * 异常转换器公共接口.
 *
 * @author <a href="mailto:943868899@qq.com">Yujie</a>
 * @version 0.1
 */
public interface ExceptionConverter {
    /**
     * 将异常转为另一种异常.
     *
     * @param inputExceptionClass 待转换的异常类Class
     * @return
     */
    Class<? extends Throwable> convert(Class<? extends Throwable> inputExceptionClass);

    /**
     * 将原始的异常类替换为自定义的异常类
     *
     * @param originExceptionClass
     * @param customExceptionClass
     */
    void register(Class<? extends Throwable> originExceptionClass,
                  Class<? extends Throwable> customExceptionClass);
}
