package com.wujiuye.asmbytecode.book.advanced;

import com.wujiuye.asmbytecode.book.fifth.util.ByteCodeUtils;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.objectweb.asm.Opcodes.*;

public class TMain {

    public static void main(String[] args) throws IOException {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cw.visit(V1_8, ACC_PUBLIC, "com/wujiuye/Main",
                null,
                Type.getInternalName(Object.class),
                null);
        createInitMethod(cw);
        createMainMethod(cw);
        ByteCodeUtils.savaToFile("com.wujiuye.Main", cw.toByteArray());
    }

    private static void createInitMethod(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, Type.getInternalName(Object.class), "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitFrame(0, 0, null, 0, null);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    private static void createMainMethod(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC,
                "main",
                "([Ljava/lang/String;)V",
                null, null);
        mv.visitCode();
        // List list = new ArrayList<>;
        mv.visitTypeInsn(NEW, Type.getInternalName(ArrayList.class));
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, Type.getInternalName(ArrayList.class), "<init>", "()V", false);
        mv.visitVarInsn(ASTORE, 1);

        // list.add("hello word!")
        mv.visitVarInsn(ALOAD, 1);
        mv.visitLdcInsn("hello word!");
        mv.visitMethodInsn(INVOKEINTERFACE, Type.getInternalName(List.class), "add", "(Ljava/lang/String;)V", true);

        // String str = list.get(0);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitInsn(ICONST_0);
        mv.visitMethodInsn(INVOKEINTERFACE, Type.getInternalName(List.class), "get", "(I)Ljava/lang/String;", true);
        mv.visitTypeInsn(CHECKCAST, Type.getInternalName(String.class));
        mv.visitVarInsn(ASTORE, 2);

        mv.visitInsn(RETURN);
        mv.visitFrame(0, 0, null, 0, null);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }


}
