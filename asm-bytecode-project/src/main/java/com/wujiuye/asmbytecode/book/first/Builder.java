package com.wujiuye.asmbytecode.book.first;

public class Builder {

    private int a;
    private int b;
    private int c;

    public Builder setA(int a) {
        this.a = a;
        return this;
    }

    public Builder setB(int b) {
        this.b = b;
        return this;
    }

    public Builder setC(int c) {
        this.c = c;
        return this;
    }

    public static void main(String[] args) {
        Builder builder = new Builder()
                .setA(1)
                .setB(2)
                .setC(4);
    }
}
