package com.wujiuye.asmbytecode.book.advanced;

import com.wujiuye.asmbytecode.book.fifth.util.ByteCodeUtils;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.lang.invoke.*;
import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.*;

public class InvokeDynamicMain {

    public static void main(final String[] args) throws Exception {
        final String className = "com/wujiuye/DynamicMain";
        final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        cw.visit(V1_8, ACC_PUBLIC, className, null, "java/lang/Object", null);
        createInitMethod(cw);
        createDynamicMethod(cw, className);
        cw.visitEnd();
//         ByteCodeUtils.savaToFile(className.replace("/", "."), cw.toByteArray());
        Class<?> dm = ByteCodeUtils.loadClass(className.replace("/", "."), cw.toByteArray());
        Method method = dm.getMethod("main", String[].class);
        method.invoke(null, (Object) null);
    }

    public static void createInitMethod(ClassWriter cw) {
        // 创建标准的void构造器
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }

    public static void createDynamicMethod(ClassWriter cw, String className) {
        // 创建标准的main方法
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC,
                "main",
                "([Ljava/lang/String;)V", null, null);
        mv.visitCode();
        MethodType mt = MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class);
        System.out.println(mt.toMethodDescriptorString());
        Handle bootstrap = new Handle(H_INVOKESTATIC,
                Type.getInternalName(InvokeDynamicMain.class),
                "sayHelloBootstrap",
                mt.toMethodDescriptorString(), false);
        mv.visitInvokeDynamicInsn("call", "()V", bootstrap);
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 1);
        mv.visitEnd();
    }

    private static void sayHello() {
        System.out.println("=========> ");
    }

    public static CallSite sayHelloBootstrap(MethodHandles.Lookup caller, String name, MethodType type) throws NoSuchMethodException, IllegalAccessException {
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        // 需要使用lookupClass()，因为这个方法是静态的
        final Class<?> currentClass = lookup.lookupClass();
        final MethodType targetSignature = MethodType.methodType(void.class);
        final MethodHandle targetMethodHandle = lookup.findStatic(currentClass, "sayHello", targetSignature);
        return new ConstantCallSite(targetMethodHandle.asType(type));
    }

}