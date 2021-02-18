package com.feiniaojin.grh.defaults;


import com.feiniaojin.grh.def.*;

import javax.annotation.Resource;

/**
 * 提供的默认的ResponseBeanFactory实现.
 *
 * @author <a href="mailto:943868899@qq.com">Yujie</a>
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
        responseBean.setCode(responseMetaFactory.defaultSuccess().getCode());
        responseBean.setMsg(responseMetaFactory.defaultSuccess().getMsg());
        return responseBean;
    }

    @Override
    public ResponseBean newSuccessInstance(Object data) {
        ResponseBean bean = this.newSuccessInstance();
        bean.setData(data);
        return bean;
    }

    @Override
    public ResponseBean newFailInstance() {
        ResponseBean bean = this.newInstance();
        bean.setCode(responseMetaFactory.defaultFail().getCode());
        bean.setMsg(responseMetaFactory.defaultFail().getMsg());
        return bean;
    }

    @Override
    public ResponseBean newFailInstance(Class clazz) {

        ExceptionMapper exceptionMapper = (ExceptionMapper) clazz.getAnnotation(ExceptionMapper.class);

        if (exceptionMapper == null) {
            return this.newFailInstance();
        }

        ResponseBean bean = this.newInstance();
        bean.setCode(exceptionMapper.code());
        bean.setMsg(exceptionMapper.msg());
        return bean;
    }
}
