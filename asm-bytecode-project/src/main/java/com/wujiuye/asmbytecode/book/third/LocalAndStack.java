package com.wujiuye.asmbytecode.book.third;

public class LocalAndStack {

    public static void mutilParmas(int a, int b, int c, int d, int e, int f) {
        int g = a + b + c + d + e + f;
        b = 20;
        f = 30;
        int[] aaa = new int[]{1, 2, 3};
        String[] bbb = new String[]{"1"};
        aaa[0] = Integer.parseInt(bbb[0]);
    }

    public static void main(String[] args) {
        int a = 10, b = 20;
        int c = b;
        b = a;
    }

}
