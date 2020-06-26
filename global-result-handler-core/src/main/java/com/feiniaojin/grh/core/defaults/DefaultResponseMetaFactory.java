package com.feiniaojin.grh.core.defaults;

import com.feiniaojin.grh.def.ResponseMeta;
import com.feiniaojin.grh.def.ResponseMetaFactory;

/**
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
