package com.feiniaojin.grh.defaults;


import com.feiniaojin.grh.def.ResponseBean;

import java.util.Collections;
import java.util.Map;

/**
 * 提供默认实现的
 * 包装成统一响应的JavaBean.
 *
 * @author <a href="mailto:943868899@qq.com">Yujie</a>
 * @version 0.1
 * @since 0.1
 */
public class DefaultResponseBean implements ResponseBean {

  private static final Map<Object, Object> DEFAULT_NULL = Collections.emptyMap();

  private int code = DefaultResponseMeta.DEFAULT_SUCCESS.getCode();
  private String msg = DefaultResponseMeta.DEFAULT_SUCCESS.getMsg();
  private Object data = DEFAULT_NULL;

  public DefaultResponseBean() {
  }

  public DefaultResponseBean(Object data) {
    this.data = data;
  }

  @Override
  public void setCode(int code) {
    this.code = code;
  }

  @Override
  public void setMsg(String msg) {
    this.msg = msg;
  }

  @Override
  public void setData(Object obj) {
    this.data = obj;
  }

  @Override
  public int getCode() {
    return code;
  }

  @Override
  public String getMsg() {
    return msg;
  }

  @Override
  public Object getData() {
    return data;
  }
}
