package com.wujiuye.asmbytecode.book.sixth.cglib;

import java.lang.reflect.Method;

public class HttpRequestMyMethodInterceptor implements MyMethodInterceptor {

    @Override
    public Object intercept(Object obj, Method methodProxy, String methodName, Object[] objects) throws Throwable {
        long startMs = System.currentTimeMillis();
        try {
            // 使用代理类的代理方法调用父类的方法
            return methodProxy.invoke(obj, objects);
        } finally {
            long cntMs = System.currentTimeMillis() - startMs;
            System.out.println(methodName + "方法的执行耗时为" + cntMs + "毫秒");
        }
    }

}
