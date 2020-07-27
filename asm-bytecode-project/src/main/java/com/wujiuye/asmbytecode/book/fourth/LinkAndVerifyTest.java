package com.wujiuye.asmbytecode.book.fourth;

import java.lang.reflect.Method;

public class LinkAndVerifyTest {

    public static void main(String[] args) throws Exception {
        Class<?> clz = LinkAndVerifyTest.class.getClassLoader()
                .loadClass("com.wujiuye.asmbytecode.book.fourth.VerifyTest2");
        System.out.println(clz);
        try {
            Object target = clz.newInstance();
            Method method = clz.getMethod("getId");
            System.out.println("return value:" + method.invoke(target));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
