package com.wujiuye.asmbytecode.book.sixth;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class HttpRequest {

    private String url;
    private Map<String, String> header = new HashMap<>();
    private String method;
    private String body;

    public HttpRequest(String url, String method) {
        this.url = url;
        this.method = method;
    }

}
