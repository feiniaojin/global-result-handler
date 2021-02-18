package com.feiniaojin.grh.def;

/**
 * 响应吗接口.
 * @author <a href="mailto:943868899@qq.com">Yujie</a>
 * @version 0.1
 */
public interface ResponseMeta {

  /**
   * 获得响应码.
   *
   * @return
   */
  int getCode();

  /**
   * 获得响应信息.
   *
   * @return
   */
  String getMsg();
}
