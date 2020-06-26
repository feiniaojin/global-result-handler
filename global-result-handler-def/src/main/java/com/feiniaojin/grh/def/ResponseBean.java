package com.feiniaojin.grh.def;

/**
 * 响应体接口，响应的数据应实现该接口.
 *
 * @author <a href="mailto:qinyujie@gingo.cn">Yujie</a>
 * @version 0.1
 */
public interface ResponseBean {
  /**
   * 设置响应码.
   *
   * @param code 设置的响应码.
   */
  void setCode(int code);

  /**
   * 设置响应提示信息.
   *
   * @param msg 设置响应提示信息.
   */
  void setMsg(String msg);

  /**
   * 设置响应数据.
   *
   * @param obj 设置的响应数据.
   */
  void setData(Object obj);

}
