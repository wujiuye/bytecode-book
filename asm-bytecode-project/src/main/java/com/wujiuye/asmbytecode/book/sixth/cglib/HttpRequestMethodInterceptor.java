package com.wujiuye.asmbytecode.book.sixth.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class HttpRequestMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        long startMs = System.currentTimeMillis();
        try {
            return methodProxy.invokeSuper(obj,objects);
        } finally {
            long cntMs = System.currentTimeMillis() - startMs;
            System.out.println(method.getName() + "方法的执行耗时为" + cntMs + "毫秒");
        }
    }

}
