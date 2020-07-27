package com.wujiuye.asmbytecode.book.advanced;

import java.util.ArrayList;
import java.util.List;

public class ListMain {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("hello word!");
        String one = list.get(0);
        test();
        test2();
    }

    public static void test() {
        MyArrayList<Number> numbers = new MyArrayList<>();
        numbers.add(1);
        Number v = numbers.get(0);
    }

    public static void test2(){
        List list = new ArrayList();
        list.add("asa");
        list.add(121);
        System.out.println(list.get(1));
    }

}
