package com.feiniaojin.grh.bean;

import com.feiniaojin.grh.enums.DefaultResponseCode;
import java.util.Collections;
import java.util.Map;
import lombok.Data;

/**
 * 包装成统一响应的JavaBean.
 *
 * @author feiniaojin
 * @version 0.1
 * @since 0.1
 */
@Data
public class ResponseBean {

  private static final Map<Object, Object> DEFAULT_NULL = Collections.emptyMap();

  private int code = DefaultResponseCode.DEFAULT_SUCCESS.getCode();
  private String msg = DefaultResponseCode.DEFAULT_SUCCESS.getMsg();
  private Object data = DEFAULT_NULL;

  public ResponseBean() {
  }

  public ResponseBean(Object data) {
    this.data = data;
  }
}
