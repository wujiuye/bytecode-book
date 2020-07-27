package com.wujiuye.asmbytecode.book.advanced;


import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class UserService {

    public String getUserName(int id) {
        return "wujiuye";
    }

    public static void main(String[] args) {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
//        MethodVisitor mv = cw.visitMethod();
//        mv.visitFrame(0,0,null,0,null);
//        mv.visitMaxs();
    }

}
