package com.wujiuye.asmbytecode.book.advanced;

import java.util.ArrayList;

public class MyArrayList<T extends Number> extends ArrayList<T> {

    @Override
    public boolean add(T t) {
        return super.add(t);
    }

}
