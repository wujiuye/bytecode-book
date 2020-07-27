package com.wujiuye.asmbytecode.book.third;

import com.wujiuye.asmbytecode.book.third.model.User;

public class ReturnOpcode {

    public static int getInt(){
        return 1000000000;
    }

    public static long getLong(){
        return 1000000000000000000L;
    }

    public static User getObject(){
        return new User();
    }

    public static int[] getArray(){
        int[] array = new int[]{};
        return array;
    }

    public static User[] getObjArray(){
        User[] userArray = new User[]{};
        return userArray;
    }

}
