package com.feiniaojin.grh.core.defaults;

import com.feiniaojin.grh.def.ExceptionMapper;
import com.feiniaojin.grh.def.ResponseBean;
import com.feiniaojin.grh.def.ResponseBeanFactory;
import com.feiniaojin.grh.def.ResponseMeta;
import com.feiniaojin.grh.def.ResponseMetaFactory;
import javax.annotation.Resource;

/**
 * @author <a href="mailto:qinyujie@gingo.cn">Yujie</a>
 * @version 0.1
 */
public class DefaultResponseBeanFactory implements ResponseBeanFactory {

  @Resource
  private ResponseMetaFactory responseMetaFactory;

  @Override
  public ResponseBean newInstance() {
    return new DefaultResponseBean();
  }

  @Override
  public ResponseBean newInstance(ResponseMeta responseMeta) {

    ResponseBean bean = this.newInstance();
    bean.setCode(responseMeta.getCode());
    bean.setMsg(responseMeta.getMsg());
    return bean;
  }

  @Override
  public ResponseBean newSuccessInstance() {
    DefaultResponseBean responseBean = new DefaultResponseBean();
    responseBean.setCode(responseMetaFactory.success().getCode());
    responseBean.setMsg(responseMetaFactory.success().getMsg());
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
    bean.setCode(responseMetaFactory.fail().getCode());
    bean.setMsg(responseMetaFactory.fail().getMsg());
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
