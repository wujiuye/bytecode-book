package com.wujiuye.asmbytecode.book.sixth.jdk;

import com.wujiuye.asmbytecode.book.sixth.HttpRequest;
import com.wujiuye.asmbytecode.book.sixth.HttpRequestTemplate;
import com.wujiuye.asmbytecode.book.sixth.HttpRequestTemplateImpl;
import com.wujiuye.asmbytecode.book.sixth.HttpResponse;

import java.lang.reflect.Proxy;

public class JdkAop {

    static {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
    }

    public static void main(String[] args) throws Exception {
//        HttpRequestTemplate target = new HttpRequestTemplateImpl();
//        HttpRequestTemplate requestTemplate = (HttpRequestTemplate)
//                Proxy.newProxyInstance(JdkAop.class.getClassLoader(),
//                        new Class[]{HttpRequestTemplate.class},
//                        new HttpRequestInvocationHandler(target));
//        HttpRequest request = new HttpRequest("http://127.0.0.1:8080/book/list", "GET");
//        HttpResponse response = requestTemplate.doPost(request);

        HttpRequestTemplate target = new HttpRequestTemplateImpl();
        HttpRequestTemplate requestTemplate = (HttpRequestTemplate) MyProxy.newProxyInstance(
                new Class[]{HttpRequestTemplate.class},
                new HttpRequestInvocationHandler(target));
        HttpRequest request = new HttpRequest("http://127.0.0.1:8080/book/list", "GET");
        HttpResponse response = requestTemplate.doGet(request);
        System.out.println(response);
    }

}
