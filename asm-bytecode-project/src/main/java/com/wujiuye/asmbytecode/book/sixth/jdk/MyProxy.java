package com.wujiuye.asmbytecode.book.sixth.jdk;

import com.wujiuye.asmbytecode.book.fifth.util.ByteCodeUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.util.concurrent.atomic.AtomicInteger;

public class MyProxy {

    protected InvocationHandler h;

    private final static AtomicInteger PROXY_CNT = new AtomicInteger(0);

    private MyProxy() {

    }

    protected MyProxy(InvocationHandler h) {
        this.h = h;
    }

    public static Object newProxyInstance(Class<?>[] interfaces, InvocationHandler h) throws Exception {
        String proxyClassName = "com/sun/proxy/$Proxy" + PROXY_CNT.getAndIncrement();
        byte[] proxyClassByteCode = MyProxyFactory.createProxyClass(proxyClassName, interfaces);
        Class<?> proxyClass = ByteCodeUtils.loadClass(proxyClassName, proxyClassByteCode);
        Constructor<?> constructor = proxyClass.getConstructor(InvocationHandler.class);
        return constructor.newInstance(h);
    }

}
