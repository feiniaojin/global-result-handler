package com.feiniaojin.grh.def;

/**
 * ResponseBean的工厂类，用于生成ResponseBean.
 *
 * @author <a href="mailto:qinyujie@gingo.cn">Yujie</a>
 * @version 0.1
 */
public interface ResponseBeanFactory<T> {


  /**
   * 创建新的空响应.
   *
   * @return
   */
  ResponseBean newInstance();

  /**
   * 创建新的空响应.
   *
   * @param responseMeta 响应元信息.
   * @return
   */
  ResponseBean newInstance(ResponseMeta responseMeta);

  /**
   * 创建新的响应.
   *
   * @return
   */
  ResponseBean newSuccessInstance();

  /**
   * 从数据中创建成功响应.
   *
   * @param t 响应数据.
   * @return
   */
  ResponseBean newSuccessInstance(T t);

  /**
   * 创建新的失败响应.
   *
   * @return
   */
  ResponseBean newFailInstance();

  /**
   * 根据异常类，创建新的失败响应.
   *
   * @param clazz 根据异常的class生成对应的响应.
   * @return
   */
  ResponseBean newFailInstance(Class<? extends Exception> clazz);
}
