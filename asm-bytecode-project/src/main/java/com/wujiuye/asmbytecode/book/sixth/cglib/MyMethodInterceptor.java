package com.wujiuye.asmbytecode.book.sixth.cglib;

import java.lang.reflect.Method;

public interface MyMethodInterceptor {

    /**
     * 方法拦截
     *
     * @param obj        代理类实例
     * @param method     代理类提供给拦截器调用父类方法的Method，使用该Method调用父类的方法。
     *                   注意：methodProxy.getName获取到的方法是代理方法名称
     * @param methodName 被代理的方法名称
     * @param objects    方法执行参数
     * @return
     * @throws Throwable
     */
    Object intercept(Object obj, Method method, String methodName, Object[] objects) throws Throwable;

}
