package com.wujiuye.asmbytecode.book.third;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class TryCatchMain {

    public int tryCatchDemo() {
        try {
            int n = 100;
            int m = 0;
            return n / m;
        } catch (ArithmeticException e) {
            return -1;
        }
    }

    public int tryCatchFinalDemo() {
        try {
            int n = 100;
            int m = 0;
            return n / m;
        } catch (ArithmeticException e) {
            return -1;
        } finally {
            System.out.println("finally");
        }
    }


    public void tryWithResource() {
        try (InputStream in = new FileInputStream("/tmp/com.wujiuye.asmbytecode.book.fifth.UseAsmModifyClass.class")) {

        } catch (Exception e) {

        }
    }

    public static void main(String[] args) {
        TryCatchMain tryCatchMain = new TryCatchMain();
        tryCatchMain.tryCatchDemo();
        tryCatchMain.tryCatchFinalDemo();
        tryCatchMain.tryWithResource();
    }

}
