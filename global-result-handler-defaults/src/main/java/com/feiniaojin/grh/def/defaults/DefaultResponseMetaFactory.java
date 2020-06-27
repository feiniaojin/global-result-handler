package com.feiniaojin.grh.def.defaults;

import com.feiniaojin.grh.def.ResponseMeta;
import com.feiniaojin.grh.def.ResponseMetaFactory;

/**
 * 提供的默认的ResponseMetaFactory实现.
 * @author <a href="mailto:qinyujie@gingo.cn">Yujie</a>
 * @version 0.1
 */
public class DefaultResponseMetaFactory implements ResponseMetaFactory {
  @Override
  public ResponseMeta success() {
    return DefaultResponseMeta.DEFAULT_SUCCESS;
  }

  @Override
  public ResponseMeta fail() {
    return DefaultResponseMeta.DEFAULT_FAIL;
  }
}
