package com.wujiuye.asmbytecode.book.third.dao;

import com.wujiuye.asmbytecode.book.third.model.User;

public class UserDao {

    public User getUserByName(String name) {
        return new User().setName(name);
    }

}
