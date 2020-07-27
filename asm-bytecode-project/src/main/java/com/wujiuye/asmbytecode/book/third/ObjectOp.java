package com.wujiuye.asmbytecode.book.third;

import com.wujiuye.asmbytecode.book.third.model.User;
import com.wujiuye.asmbytecode.book.third.service.UserService;

public class ObjectOp {


    public static void main(String[] args) {
        UserService service = new UserService();
        User user = service.getUser();
        String name = user.getName();
        System.out.println(name);
    }

}
