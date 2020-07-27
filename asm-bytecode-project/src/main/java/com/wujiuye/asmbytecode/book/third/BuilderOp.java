package com.wujiuye.asmbytecode.book.third;

import com.wujiuye.asmbytecode.book.first.Builder;
import com.wujiuye.asmbytecode.book.third.service.UserService;

public class BuilderOp {

    public static void testBuilder() {
        Builder builder = new Builder()
                .setA(10)
                .setB(20)
                .setC(30);
    }

    public static void main(String[] args) {
        new UserService().getUser().getName();
    }

}
