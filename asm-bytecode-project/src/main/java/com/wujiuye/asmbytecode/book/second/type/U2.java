package com.wujiuye.asmbytecode.book.second.type;

public class U2 {

    private byte[] value;

    public U2(byte b1, byte b2) {
        value = new byte[]{b1, b2};
    }

    public Integer toInt() {
        return (value[0] & 0xff) << 8 | (value[1] & 0xff);
    }

    public String toHexString() {
        char[] hexChar = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuilder hexStr = new StringBuilder();
        for (int i = 1; i >= 0; i--) {
            int v = value[i] & 0xff;
            while (v > 0) {
                int c = v % 16;
                v = v >>> 4;
                hexStr.insert(0, hexChar[c]);
            }
            if (((hexStr.length() & 0x01) == 1)) {
                hexStr.insert(0, '0');
            }
        }
        return "0x" + (hexStr.length() == 0 ? "00" : hexStr.toString());
    }

}
