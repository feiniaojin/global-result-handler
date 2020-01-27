package cn.gingo.global.result.handler;

/**
 * <p>
 * 默认的响应码枚举类，通常用于成功和失败的场合.
 * </p>
 * <p>
 * 成功：{@code DEFAULT_SUCCESS}
 * </p>
 * <p>
 * 失败：{@code DEFAULT_FAIL}.
 * </p>
 *
 * @author qinyujie
 * @version 0.1
 * @since 0.1
 */
public enum DefaultResponseCodeEnums {

  /**
   * {@code DEFAULT_SUCCESS} 请求处理成功.
   */
  DEFAULT_SUCCESS(0, "Success"),

  /**
   * {@code DEFAULT_FAIL} 请求处理失败.
   */
  DEFAULT_FAIL(1, "Poor network quality！");

  /**
   * 响应码.
   */
  private int code;

  /**
   * 响应信息.
   */
  private String msg;

  /**
   * 通过响应码和响应信息构造枚举.
   *
   * @param code 响应码
   * @param msg  响应信息
   */
  DefaultResponseCodeEnums(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
