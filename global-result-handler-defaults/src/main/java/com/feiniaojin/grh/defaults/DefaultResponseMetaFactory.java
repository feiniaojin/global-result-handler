package com.feiniaojin.grh.defaults;


import com.feiniaojin.grh.def.ResponseMeta;
import com.feiniaojin.grh.def.ResponseMetaFactory;

/**
 * 提供的默认的ResponseMetaFactory实现.
 *
 * @author <a href="mailto:943868899@qq.com">Yujie</a>
 * @version 0.1
 */
public class DefaultResponseMetaFactory implements ResponseMetaFactory {
    @Override
    public ResponseMeta defaultSuccess() {
        return DefaultResponseMeta.DEFAULT_SUCCESS;
    }

    @Override
    public ResponseMeta defaultFail() {
        return DefaultResponseMeta.DEFAULT_FAIL;
    }
}
