package com.wujiuye.asmbytecode.book.fifth;

import com.wujiuye.asmbytecode.book.fifth.util.ByteCodeUtils;
import org.objectweb.asm.*;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.objectweb.asm.Opcodes.*;

public class UseAsmModifyClass {

//    public static void main(String[] args) throws IOException {
//        String className = "com.wujiuye.asmbytecode.book.fifth.UseAsmModifyClass";
//        ClassReader classReader = new ClassReader(className);
//        ClassWriter classWriter = new ClassWriter(0);
//        classReader.accept(classWriter, 0);
//        generateField(classWriter);
//        byte[] newByteCode = classWriter.toByteArray();
//        ByteCodeUtils.savaToFile(className, newByteCode);
//    }
//
//    static void generateField(ClassWriter classWriter) {
//        FieldVisitor fieldVisitor = classWriter.visitField(ACC_PRIVATE,
//                "name", "Ljava/lang/String;", null, null);
//        fieldVisitor.visitAnnotation("Llombok/Getter;", false);
//    }

//    static void generateMethod(ClassWriter classWriter) {
//        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "main",
//                "([Ljava/lang/String;)V",
//                null, null);
//        methodVisitor.visitCode();
//        methodVisitor.visitEnd();
//    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String className = "com.wujiuye.asmbytecode.book.fifth.UseAsmModifyClass";
        ClassReader classReader = new ClassReader(className);
        MyClassWriter classWriter = new MyClassWriter(new ClassWriter(0));
        classReader.accept(classWriter, 0);

        byte[] newByteCode = classWriter.toByteArray();
        ByteCodeUtils.savaToFile(className, newByteCode);
    }

    static class MyClassWriter extends ClassVisitor {

        private ClassWriter classWriter;

        public MyClassWriter(ClassWriter classWriter) {
            super(Opcodes.ASM6, classWriter);
            this.classWriter = classWriter;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            if ("main".equals(name)) {
                MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
                return new MainMethodWriter(methodVisitor);
            }
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }

        public byte[] toByteArray() {
            return classWriter.toByteArray();
        }

    }

    static class MainMethodWriter extends MethodVisitor {

        private MethodVisitor methodVisitor;

        public MainMethodWriter(MethodVisitor methodVisitor) {
            super(Opcodes.ASM6, methodVisitor);
            this.methodVisitor = methodVisitor;
        }

        @Override
        public void visitCode() {
            super.visitCode();
            methodVisitor.visitFieldInsn(GETSTATIC,
                    Type.getInternalName(System.class),
                    "out",
                    Type.getDescriptor(System.out.getClass()));
            methodVisitor.visitLdcInsn("hello word!");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL,
                    Type.getInternalName(System.out.getClass()),
                    "println",
                    "(Ljava/lang/String;)V", false);
        }

//        @Override
//        public void visitInsn(int opcode) {
//            if (opcode == RETURN) {
//                methodVisitor.visitFieldInsn(GETSTATIC,
//                        Type.getInternalName(System.class),
//                        "out",
//                        Type.getDescriptor(System.out.getClass()));
//                methodVisitor.visitLdcInsn("hello word!");
//                methodVisitor.visitMethodInsn(INVOKEVIRTUAL,
//                        Type.getInternalName(System.out.getClass()),
//                        "println",
//                        "(Ljava/lang/String;)V", false);
//            }
//            super.visitInsn(opcode);
//        }

    }

}
