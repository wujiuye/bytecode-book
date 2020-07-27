package com.wujiuye.asmbytecode.book.sixth.cglib;

import com.wujiuye.asmbytecode.book.sixth.HttpRequest;
import com.wujiuye.asmbytecode.book.sixth.HttpRequestTemplateImpl;
import net.sf.cglib.core.DebuggingClassWriter;

public class CglibAop {

    static {
        // 代理类class文件存入本地磁盘
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/tmp");
    }

    public static void main(String[] args) {
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(HttpRequestTemplateImpl.class);
//        enhancer.setCallback(new HttpRequestMethodInterceptor());
//        HttpRequestTemplateImpl requestTemplate = (HttpRequestTemplateImpl) enhancer.create();
//        HttpRequest request = new HttpRequest("http://127.0.0.1:8080/book/list", "GET");
//        requestTemplate.doGet(request);

        MyEnhancer enhancer = new MyEnhancer();
        enhancer.setSuperclass(HttpRequestTemplateImpl.class);
        enhancer.setCallback(new HttpRequestMyMethodInterceptor());
        HttpRequestTemplateImpl proxyObj = (HttpRequestTemplateImpl) enhancer.create();
        HttpRequest request = new HttpRequest("http://127.0.0.1:8080/book/list", "GET");
        proxyObj.doGet(request);
    }

}
