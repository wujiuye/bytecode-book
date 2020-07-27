package com.wujiuye.asmbytecode.book.third.service;

import com.wujiuye.asmbytecode.book.third.dao.UserDao;
import com.wujiuye.asmbytecode.book.third.model.User;

public class UserService extends BaseService {

    private UserDao userDao = new UserDao();

    public UserService() {

    }

    public UserService(UserDao userDao) {
        this();
        this.userDao = userDao;
    }

    public User findUser(String username) {
        return userDao.getUserByName(username);
    }

    public void onInit() {
        this.userDao = new UserDao();
    }

    @Override
    public void testInvokeSuperMethod() {
        super.testInvokeSuperMethod();
    }

    public User getUser() {
        return new User()
                .setAge(25)
                .setE_mail("419611821@qq.com")
                .setName("wujiuye")
                .setPhone("88888888");
    }

}
