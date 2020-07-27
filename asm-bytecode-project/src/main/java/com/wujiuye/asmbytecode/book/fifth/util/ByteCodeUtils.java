package com.wujiuye.asmbytecode.book.fifth.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteCodeUtils {

    /**
     * 将字节码转为class文件
     *
     * @throws IOException
     */
    public static void savaToFile(String className, byte[] byteCode) throws IOException {
        File file = new File("/tmp/" + className + ".class");
        if ((!file.exists() || file.delete()) && file.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(byteCode);
            }
        }
    }

    private final static ByteCodeClassLoader byteCodeClassLoader
            = new ByteCodeClassLoader(ByteCodeUtils.class.getClassLoader());

    /**
     * 加载类
     *
     * @param className 类名
     * @param byteCode  字节数组
     * @return
     * @throws ClassNotFoundException
     */
    public static Class loadClass(final String className, final byte[] byteCode) throws ClassNotFoundException {
        ByteCodeHolder holder = new ByteCodeHolder() {
            @Override
            public String getClassName() {
                return className;
            }

            @Override
            public byte[] getByteCode() {
                return byteCode;
            }
        };
        byteCodeClassLoader.add(className,holder);
        return byteCodeClassLoader.loadClass(className);
    }

}
