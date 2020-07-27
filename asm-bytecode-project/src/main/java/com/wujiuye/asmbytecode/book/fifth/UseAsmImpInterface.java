package com.wujiuye.asmbytecode.book.fifth;

import com.wujiuye.asmbytecode.book.fifth.util.ByteCodeUtils;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.io.IOException;

import static org.objectweb.asm.Opcodes.*;

public class UseAsmImpInterface {

    public static void main(String[] args) throws IOException {
        // 创建的类的类名
        String implClassName = SayHelloInterface.class.getName() + "Impl";
        ClassWriter cw = new ClassWriter(0);
        // 设置class文件结构的版本号、类名、类签名、父类、实现的接口
        cw.visit(Opcodes.V1_8, ACC_PUBLIC,
                implClassName.replace(".", "/"),
                null,
                Type.getInternalName(Object.class),
                new String[]{Type.getInternalName(SayHelloInterface.class)});
        // 创建asyHello方法
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "sayHello",
                "()V", null, null);
        // 插入输出"hello word!"的字节码指令
        mv.visitFieldInsn(GETSTATIC,
                Type.getInternalName(System.class),
                "out",
                Type.getDescriptor(System.out.getClass()));
        mv.visitLdcInsn("hello word!");
        mv.visitMethodInsn(INVOKEVIRTUAL,
                Type.getInternalName(System.out.getClass()),
                "println",
                "(Ljava/lang/String;)V", false);
        mv.visitInsn(RETURN);
        // 获取生成的类的字节数组
        byte[] byteCode = cw.toByteArray();
        // 保存到文件
        ByteCodeUtils.savaToFile(implClassName, byteCode);
    }

}
