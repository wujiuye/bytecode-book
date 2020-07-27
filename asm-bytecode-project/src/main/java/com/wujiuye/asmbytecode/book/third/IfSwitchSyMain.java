package com.wujiuye.asmbytecode.book.third;

public class IfSwitchSyMain {

    public int ifFunc(int type) {
        if (type == 1) {

        } else if (type == 2) {
            return 1000;
        } else {
            return 0;
        }
        return 100;
    }

    public int ifFunc2(int type) {
        if (type == 1) {
            type = 2;
        } else {
            type = 3;
        }
        return type;
    }

    public int switchFunc(int stat) {
        int a = 0;
        switch (stat) {
            case 5:
                a = 0;
                break;
            case 6:
            case 8:
                a = 1;
                break;
        }
        return a;
    }

    public int switch2Func(int stat) {
        int a = 0;
        switch (stat) {
            case 1:
                a = 0;
                break;
            case 100:
                a = 1;
                break;
        }
        return a;
    }

    public int syFunc(boolean sex) {
        return sex ? 1 : 0;
    }

}
