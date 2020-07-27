package com.wujiuye.asmbytecode.book.fourth;

public class PrintClassLoader {

    public static void main(String[] args) {
        ClassLoader classLoader = PrintClassLoader.class.getClassLoader();
        do {
            System.out.println(classLoader);
            classLoader = classLoader.getParent();
        } while (classLoader != null);
    }

}
