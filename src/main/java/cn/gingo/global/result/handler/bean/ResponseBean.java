package cn.gingo.global.result.handler.bean;

import cn.gingo.global.result.handler.enums.DefaultResponseCode;
import lombok.Data;

import java.util.Collections;
import java.util.Map;

/**
 * 包装成统一响应的JavaBean.
 * @author qinyujie
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
