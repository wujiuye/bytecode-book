package com.wujiuye.asmbytecode.book.third;

import java.util.ArrayList;
import java.util.List;

public class ForWhileMain {

    public int forDemo() {
        int count = 0;
        for (int i = 1; i <= 10; i++) {
            count += i;
        }
        return count;
    }

    public void forEachDemo(List<String> list) {
        for (String str : list) {
            System.out.println(str);
        }
    }

    public void whileDemo() {
        int count = 10;
        while (count > 0) {
            count--;
        }
    }

    public static void main(String[] args) {
        ForWhileMain forWhileMain = new ForWhileMain();
        forWhileMain.forDemo();
        forWhileMain.forEachDemo(new ArrayList<>());
        forWhileMain.whileDemo();
    }

}
