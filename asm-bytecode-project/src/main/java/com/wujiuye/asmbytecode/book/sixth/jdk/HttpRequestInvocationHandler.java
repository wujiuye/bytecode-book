package com.wujiuye.asmbytecode.book.sixth.jdk;

import com.wujiuye.asmbytecode.book.sixth.HttpRequestTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HttpRequestInvocationHandler implements InvocationHandler {

    private HttpRequestTemplate target;

    public HttpRequestInvocationHandler(HttpRequestTemplate target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startMs = System.currentTimeMillis();
        try {
            return method.invoke(target, args);
        } finally {
            long cntMs = System.currentTimeMillis() - startMs;
            System.out.println(method.getName() + "方法的执行耗时为" + cntMs + "毫秒");
        }
    }

}
