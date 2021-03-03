package com.guang.bean;

import java.lang.reflect.Method;

/**
 * 数据多了用对象，对象多了用集合
 */
public class MvcMethod {

    private Method method;
    private Object obj;

    public MvcMethod() {
    }

    public MvcMethod(Method method, Object obj) {
        this.method = method;
        this.obj = obj;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
