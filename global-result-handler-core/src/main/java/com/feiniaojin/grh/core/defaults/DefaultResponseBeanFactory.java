package com.feiniaojin.grh.core.defaults;

import com.feiniaojin.grh.def.ExceptionMapper;
import com.feiniaojin.grh.def.ResponseBean;
import com.feiniaojin.grh.def.ResponseBeanFactory;

/**
 * @author <a href="mailto:qinyujie@gingo.cn">Yujie</a>
 * @version 0.1
 */
public class DefaultResponseBeanFactory implements ResponseBeanFactory {

  @Override
  public ResponseBean newInstance() {
    return new DefaultResponseBean();
  }

  @Override
  public ResponseBean newSuccessInstance() {
    DefaultResponseBean responseBean = new DefaultResponseBean();
    responseBean.setCode(DefaultResponseCode.DEFAULT_SUCCESS.getCode());
    responseBean.setMsg(DefaultResponseCode.DEFAULT_SUCCESS.getMsg());
    return responseBean;
  }

  @Override
  public ResponseBean newSuccessInstance(Object o) {
    ResponseBean bean = this.newSuccessInstance();
    bean.setData(o);
    return bean;
  }

  @Override
  public ResponseBean newFailInstance() {
    ResponseBean bean = this.newInstance();
    bean.setCode(DefaultResponseCode.DEFAULT_SUCCESS.getCode());
    bean.setMsg(DefaultResponseCode.DEFAULT_SUCCESS.getMsg());
    return bean;
  }

  @Override
  public ResponseBean newFailInstance(Class clazz) {

    ExceptionMapper exceptionMapper = (ExceptionMapper) clazz.getAnnotation(ExceptionMapper.class);

    if (exceptionMapper != null) {
      ResponseBean bean = this.newInstance();
      bean.setCode(exceptionMapper.code());
      bean.setMsg(exceptionMapper.msg());
      return bean;
    }
    return this.newFailInstance();
  }
}
