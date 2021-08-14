package com.wujiuye.asmbytecode.book.trycatchinsert;

public class TryCatchInsertTestClass {

    public static void test() {
        try {
            int i = 0;
            System.out.println(i);
            throw new RuntimeException("模拟异常");
        } catch (Throwable throwable) {
            System.out.println("发生异常了");
        }
    }

}
