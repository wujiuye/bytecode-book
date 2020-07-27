package com.wujiuye.asmbytecode.book.second;

import com.wujiuye.asmbytecode.book.second.type.ClassFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class ClassFileAnalysisMain {

    public static ByteBuffer readFile(String classFilePath) throws Exception {
        File file = new File(classFilePath);
        if (!file.exists()) {
            throw new Exception("file not exists!");
        }
        byte[] byteCodeBuf = new byte[4096];
        int lenght;
        try (InputStream in = new FileInputStream(file)) {
            lenght = in.read(byteCodeBuf);
        }
        if (lenght < 1) {
            throw new Exception("not read byte code.");
        }
        return ByteBuffer.wrap(byteCodeBuf, 0, lenght)
                .asReadOnlyBuffer();
    }

    public static void main(String[] args) throws Exception {
        ByteBuffer codeBuf = readFile("/Users/wjy/MyProjects/asm-bytecode-project/build/classes/java/main/com/wujiuye/asmbytecode/book/vmstack/RecursionAlgorithmMain.class");
        ClassFile classFile = ClassFileAnalysiser.analysis(codeBuf);
        System.out.println(classFile.getMagic().toHexString());
        System.out.println(classFile.getMinor_version().toInt());
        System.out.println(classFile.getMagor_version().toInt());
    }

}
