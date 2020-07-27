package com.wujiuye.asmbytecode.book.fourth;

import com.wujiuye.asmbytecode.book.fourth.asm.ByteCodeHandler;
import com.wujiuye.asmbytecode.book.fourth.cload.ByteCodeClassLoader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Opcodes.RETURN;

public class VerifyMain {

    public static class VerifyTestByteCodeHandler implements ByteCodeHandler {

        private ClassWriter classWriter;

        public VerifyTestByteCodeHandler() {
            /**
             * 当你创建一个ClassWriter对象的时候，你可以指定自动计算这些：
             *
             * 使用‘new ClassWriter(0)’构造函数，不会自动计算这些属性。你必须自己计算帧的大小、本地变量的大小和操作栈的大小。
             * 使用‘new ClassWriter(ClassWriter.COMPUTE_MAXS)’构造函数，本地变量的大小和操作栈的大小会被自动计算。你仍然需要调用‘visitMax’方法，但是你可以传递任意参数：ASM会忽略这些参数，并重新计算它们的大小。使用该选项，你必须计算帧的大小。
             * 使用‘new ClassWriter(ClassWriter.COMPUTE_FRAMES)’构造函数，所有的属性都会被自动计算。你不必调用‘visitFrame’方法，但你仍然需要调用‘visitMax’方法(ASM会忽略这些参数，并重新计算它们的大小)。
             */
            this.classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        }

        @Override
        public String getClassName() {
            return "com/wujiuye/asmbytecode/book/fourth/VerifyTestNew";
        }

        private void voidConstructor() {
            // 生成<init>方法
            MethodVisitor methodVisitor = this.classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            methodVisitor.visitCode();

            // 调用父类构造器
//            methodVisitor.visitVarInsn(ALOAD, 0);
//            methodVisitor.visitMethodInsn(INVOKESPECIAL, Object.class.getName().replace(".", "/"),
//                    "<init>", "()V", false);

            methodVisitor.visitInsn(RETURN);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }

        private void longTypeReturnMethod() {
            MethodVisitor methodVisitor = this.classWriter.visitMethod(ACC_PUBLIC, "longTypeReturnMethod", "()J", null, null);
            methodVisitor.visitCode();

            methodVisitor.visitMethodInsn(INVOKESTATIC, Type.getInternalName(System.class), "nanoTime", "()J", false);
            methodVisitor.visitMethodInsn(INVOKESTATIC, Type.getInternalName(Long.class), "valueOf", "(J)Ljava/lang/Long;", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type.getInternalName(Long.class), "longValue", "()J", false);
            methodVisitor.visitInsn(LRETURN);

            methodVisitor.visitMaxs(2, 3);
            methodVisitor.visitEnd();
        }

        private void invokeLongTypeParamMethod() {
            MethodVisitor methodVisitor = this.classWriter.visitMethod(ACC_PUBLIC, "invokeLongTypeParamMethod", "(J)V", null, null);
            methodVisitor.visitCode();

            methodVisitor.visitVarInsn(LLOAD, 1);
            methodVisitor.visitMethodInsn(INVOKESTATIC, Type.getInternalName(VerifyMain.class), "longTypeParamMethod", "(Ljava/lang/Long;)V", false);

            methodVisitor.visitInsn(RETURN);

            //methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
            methodVisitor.visitMaxs(2, 3);
            methodVisitor.visitEnd();
        }

        @Override
        public byte[] getByteCode() {
            this.classWriter.visit(Opcodes.V1_8, ACC_PUBLIC, getClassName(), null,
                    Object.class.getName().replace(".", "/"), null);
            voidConstructor();
            longTypeReturnMethod();
            //invokeLongTypeParamMethod();
            this.classWriter.visitEnd();
            return this.classWriter.toByteArray();
        }

    }

    public static void longTypeParamMethod(Long argv) {
        System.out.println("longTypeParamMethod:" + argv);
    }

    // -noverify
    public static void main(String[] args) throws Exception {
        ByteCodeClassLoader loader = new ByteCodeClassLoader(ClassLoader.getSystemClassLoader());
        String cName = "com/wujiuye/asmbytecode/book/fourth/VerifyTestNew";
        loader.add(cName, new VerifyTestByteCodeHandler());
        Class<?> clz = loader.loadClass(cName);
        System.out.println(clz);
        try {
            Object target = clz.newInstance();
            Method method = clz.getMethod("longTypeReturnMethod");
            System.out.println("return value:" + method.invoke(target));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
