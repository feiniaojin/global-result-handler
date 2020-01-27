package cn.gingo.global.result.handler.bean;

import cn.gingo.global.result.handler.DefaultResponseCodeEnums;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * 包装成统一响应的JavaBean.
 * @author qinyujie
 * @version 0.1
 * @since 0.1
 */
@Data
public class ResponseBean {

  private static final Map<Object, Object> DEFAULT_NULL = new HashMap<>();

  private int code = DefaultResponseCodeEnums.DEFAULT_SUCCESS.getCode();
  private String msg = DefaultResponseCodeEnums.DEFAULT_SUCCESS.getMsg();
  private Object data = DEFAULT_NULL;

  public ResponseBean() {
  }

  public ResponseBean(Object data) {
    this.data = data;
  }
}
