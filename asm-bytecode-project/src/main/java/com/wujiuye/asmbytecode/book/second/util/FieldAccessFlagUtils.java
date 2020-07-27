package com.wujiuye.asmbytecode.book.second.util;

import com.wujiuye.asmbytecode.book.second.type.U2;

import java.util.HashMap;
import java.util.Map;

public class FieldAccessFlagUtils {
    private static final Map<Integer, String> fieldAccessFlagMap = new HashMap<>();

    static {
        fieldAccessFlagMap.put(0x0001, "public");
        fieldAccessFlagMap.put(0x0002, "private");
        fieldAccessFlagMap.put(0x0004, "protected");
        fieldAccessFlagMap.put(0x0008, "static");
        fieldAccessFlagMap.put(0x0010, "final");
        fieldAccessFlagMap.put(0x0040, "volatile");
        fieldAccessFlagMap.put(0x0080, "transient");
        fieldAccessFlagMap.put(0x1000, "synthtic");
        fieldAccessFlagMap.put(0x4000, "enum");
    }

    /**
     * 获取16进制对应的访问标志和属性字符串表示 （仅用于类的访问标志）
     *
     * @param flag 字段的访问标志
     * @return
     */
    public static String toFieldAccessFlagsString(U2 flag) {
        final int flagVlaue = flag.toInt();
        StringBuilder flagBuild = new StringBuilder();
        fieldAccessFlagMap.keySet()
                .forEach(key -> {
                    if ((flagVlaue & key) == key) {
                        flagBuild.append(fieldAccessFlagMap.get(key)).append(",");
                    }
                });
        return flagBuild.length() > 0 && flagBuild.charAt(flagBuild.length() - 1) == ',' ?
                flagBuild.substring(0, flagBuild.length() - 1)
                : flagBuild.toString();
    }

}
