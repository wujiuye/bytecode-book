package com.wujiuye.asmbytecode.book.second.util;

public class HexStringUtils {

    public static String toHexString(byte[] codes) {
        StringBuilder codeStrBuild = new StringBuilder();
        int i=0;
        for (byte code : codes) {
            codeStrBuild.append(toHexString(code))
                    .append(" ");
            if(++i==9){
                i=0;
                codeStrBuild.append("\n");
            }
        }
        return codeStrBuild.toString();
    }

    public static String toHexString(byte value) {
        char[] hexChar = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuilder hexStr = new StringBuilder();
        int v = value & 0xff;
        while (v > 0) {
            int c = v % 16;
            v = v >>> 4;
            hexStr.insert(0, hexChar[c]);
        }
        if (((hexStr.length() & 0x01) == 1)) {
            hexStr.insert(0, '0');
        }
        return "0x" + (hexStr.length() == 0 ? "00" : hexStr.toString());
    }

}
