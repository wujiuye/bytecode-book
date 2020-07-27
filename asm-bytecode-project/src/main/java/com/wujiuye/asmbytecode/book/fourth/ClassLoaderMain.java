package com.wujiuye.asmbytecode.book.fourth;

import java.net.URLClassLoader;

public class ClassLoaderMain {

    public static void main(String[] args) throws Exception {
        loadClass1();
//        loadClass2();
    }

    public static void loadClass1() throws Exception {
        Class<?> classLoaderTestClass = ClassLoaderMain.class.getClassLoader()
                .loadClass("com.wujiuye.asmbytecode.book.fourth.ClassLoaderTest");
        //classLoaderTestClass.newInstance();
        System.out.println(classLoaderTestClass.getName());
    }

    public static void loadClass2() throws ClassNotFoundException {
        Class<?> classLoaderTestClass = Class.forName("com.wujiuye.asmbytecode.book.fourth.ClassLoaderTest");
        System.out.println(classLoaderTestClass.getName());
        System.out.println(classLoaderTestClass.getClassLoader().getClass().getName());
    }

}
