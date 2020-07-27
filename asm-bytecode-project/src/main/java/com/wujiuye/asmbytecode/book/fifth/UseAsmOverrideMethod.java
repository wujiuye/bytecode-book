package com.wujiuye.asmbytecode.book.fifth;

import com.wujiuye.asmbytecode.book.fifth.util.ByteCodeUtils;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.io.IOException;
import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.*;

public class UseAsmOverrideMethod {

    public static void main(String[] args) throws IOException {
        // 创建的类的类名
        String subClassName = BaseClass.class.getName()
                .replace("Base", "Sub");
        ClassWriter cw = new ClassWriter(0);

        // 设置class文件结构的版本号、类名、类签名、父类、实现的接口
        cw.visit(Opcodes.V1_8, ACC_PUBLIC,
                subClassName.replace(".", "/"),
                null,
                Type.getInternalName(BaseClass.class),
                null);

        // 生成初始化方法
        generateInitMethod(cw);

        // 创建sayHello方法
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "sayHello",
                "()V", null, null);

        // 调用父类的方法
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL,
                Type.getInternalName(BaseClass.class),
                "sayHello",
                "()V", false);

        // 插入输出"SubClass sayHello"的字节码指令
        mv.visitFieldInsn(GETSTATIC,
                Type.getInternalName(System.class),
                "out",
                Type.getDescriptor(System.out.getClass()));
        mv.visitLdcInsn("SubClass sayHello");
        mv.visitMethodInsn(INVOKEVIRTUAL,
                Type.getInternalName(System.out.getClass()),
                "println",
                "(Ljava/lang/String;)V", false);

        mv.visitInsn(RETURN);
        // 设置局部变量表和操作数栈的大小
        mv.visitMaxs(2, 1);

        // 获取生成的类的字节数组
        byte[] byteCode = cw.toByteArray();
        // 保存到文件
        ByteCodeUtils.savaToFile(subClassName, byteCode);

        try {
            Class<?> asmGenerateClass = ByteCodeUtils.loadClass(subClassName, byteCode);
            Object obj = asmGenerateClass.newInstance();
            Method method = asmGenerateClass.getMethod("sayHello");
            method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void generateInitMethod(ClassWriter classWriter) {
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        // 调用父类构造器
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitMethodInsn(INVOKESPECIAL,
                Type.getInternalName(BaseClass.class),
                "<init>", "()V", false);
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(1, 1);
    }

}
