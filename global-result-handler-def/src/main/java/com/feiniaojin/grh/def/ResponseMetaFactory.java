package com.feiniaojin.grh.def;

/**
 * 响应吗接口.
 *
 * @author <a href="mailto:qinyujie@gingo.cn">Yujie</a>
 * @version 0.1
 */
public interface ResponseMetaFactory {

  /**
   * 获得响应成功的ResponseMeta.
   *
   * @return
   */
  ResponseMeta success();

  /**
   * 获得失败的ResponseMeta.
   *
   * @return
   */
  ResponseMeta fail();

}
