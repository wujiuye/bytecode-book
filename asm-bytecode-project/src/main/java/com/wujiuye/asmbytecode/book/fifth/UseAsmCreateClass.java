package com.wujiuye.asmbytecode.book.fifth;

import com.wujiuye.asmbytecode.book.fifth.util.ByteCodeUtils;
import org.objectweb.asm.*;

import java.io.IOException;

import static org.objectweb.asm.Opcodes.*;

public class UseAsmCreateClass {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String className = "com.wujiuye.asmbytecode.book.fifth.AsmGenerateClass";
        String signature = "L" + className.replace(".", "/") + ";";

        ClassWriter classWriter = new ClassWriter(0);
//        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
//        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
//        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

        // 类名、父类名、实现的接口名，以"/"替换'.'，注意，不是填类型签名
        classWriter.visit(Opcodes.V1_8, ACC_PUBLIC,
                className.replace(".", "/"),
                signature,
                "java/lang/Object",
                null);
        // classWriter.visitEnd();


        generateMethod(classWriter);
        generateField(classWriter);


        byte[] byteCode = classWriter.toByteArray();
        Class<?> asmGenerateClass = ByteCodeUtils.loadClass(className, byteCode);
        try {
            asmGenerateClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        ByteCodeUtils.savaToFile(className, byteCode);
    }

    static void generateMethod(ClassWriter classWriter) {
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        // methodVisitor.visitCode();

        // 调用父类构造器
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitMethodInsn(INVOKESPECIAL,
                "java/lang/Object",
                "<init>", "()V", false);
        methodVisitor.visitInsn(RETURN);


        methodVisitor.visitMaxs(1, 1);
//        methodVisitor.visitEnd();
    }

    static void generateField(ClassWriter classWriter) {
//        FieldVisitor fieldVisitor = classWriter.visitField(ACC_PUBLIC | ACC_STATIC | ACC_FINAL,
//                "age", "I", null, 100);
        FieldVisitor fieldVisitor = classWriter.visitField(ACC_PRIVATE,
                "name", "Ljava/lang/String;", null, null);
        fieldVisitor.visitAnnotation("Llombok/Getter;", false);

    }

}
