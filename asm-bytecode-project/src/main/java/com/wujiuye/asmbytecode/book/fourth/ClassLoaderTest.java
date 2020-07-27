package com.wujiuye.asmbytecode.book.fourth;

public class ClassLoaderTest {

    final static int testIntStaticField = 123;

    static {
        System.out.println("my name is ClassLoaderTest!");
    }

}
