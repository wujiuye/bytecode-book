package com.wujiuye.asmbytecode.book.third;

import com.wujiuye.asmbytecode.book.third.model.User;

public class BaseArrayMain {

    public static void baseArray() {
        int[] array = new int[2];
        array[1] = 100;
        int a = array[1];
    }

    public static void objArray(){
        User[] users = new User[2];
        users[0] = new User();
        users[1] = users[0];
    }

    public static void base2Array() {
        long[] array = new long[2];
    }

}
