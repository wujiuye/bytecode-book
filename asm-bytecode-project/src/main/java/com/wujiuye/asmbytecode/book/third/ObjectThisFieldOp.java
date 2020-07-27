package com.wujiuye.asmbytecode.book.third;

import com.wujiuye.asmbytecode.book.third.model.User;
import com.wujiuye.asmbytecode.book.third.service.UserService;

public class ObjectThisFieldOp {

    public static void main(String[] args) {
        User user = new UserService().findUser("wujiuye");
        System.out.println(user);
    }

}
