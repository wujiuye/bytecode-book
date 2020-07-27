package com.wujiuye.asmbytecode.book.first;

import java.io.IOException;

/**
 * @author wujiuye 2020/03/31
 */
public class RecursionAlgorithmMain {

    private static volatile int value = 0;

    /**
     * current 'n' value is 8710
     * Exception in thread "Thread-0" java.lang.StackOverflowError
     * ....
     * at com.wujiuye.asmbytecode.book.vmstack.RecursionAlgorithmMain.sigma(RecursionAlgorithmMain.java:17)
     *
     * @param n
     * @return
     */
    static int sigma(int n) {
        value = n;
//        int i = 0, j = i, a = i, b = i, c = i, r = i, g = 0;
//        int[] arr = new int[]{i, j, a, b, c, r, g};
//        for (int x = 0; x < arr.length; x++) {
//            System.out.println(arr[x]);
//        }
         System.out.println("current 'n' value is " + n);
        return n + sigma(n + 1);
    }

    public static void main(String[] args) throws IOException {
        new Thread(() -> sigma(1)).start();
        System.in.read();
        System.out.println(value);
    }

}
