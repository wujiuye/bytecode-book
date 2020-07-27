package com.wujiuye.asmbytecode.book.sixth;

public interface HttpRequestTemplate {

    HttpResponse doGet(HttpRequest httpRequest);

    HttpResponse doPost(HttpRequest httpRequest);

}
