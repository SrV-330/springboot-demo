package com.springboot.demo.exception;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component("errorException")
public class ErrorExceptionBeanFactory implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        return new ErrorException("999999", "error");
    }

    @Override
    public Class<?> getObjectType() {
        return ErrorException.class;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
