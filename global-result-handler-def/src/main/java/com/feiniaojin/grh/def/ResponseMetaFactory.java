package com.feiniaojin.grh.def;

/**
 * 响应吗接口.
 *
 * @author <a href="mailto:943868899@qq.com">Yujie</a>
 * @version 0.1
 */
public interface ResponseMetaFactory {

    /**
     * 获得响应成功的ResponseMeta.
     *
     * @return
     */
    ResponseMeta defaultSuccess();

    /**
     * 获得失败的ResponseMeta.
     *
     * @return
     */
    ResponseMeta defaultFail();

}
