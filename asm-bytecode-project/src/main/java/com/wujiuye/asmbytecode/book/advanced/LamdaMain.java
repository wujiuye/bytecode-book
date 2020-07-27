package com.wujiuye.asmbytecode.book.advanced;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LamdaMain {

    static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        //LambdaMetafactory.metafactory()
        executorService.submit(() -> "from lamda!");
    }

}
