package com.wujiuye.agent;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Opcodes.ACC_SYNTHETIC;

public class MyClassAdapter extends ClassVisitor {

    private String className;

    public MyClassAdapter(String className, ClassWriter classWriter) {
        super(ASM6, classWriter);
        this.className = className;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        // 不对私有方法注入
        if ((access & ACC_PRIVATE) != 0) {
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }
        // 不对抽象、native等方法注入
        if ((access & ACC_ABSTRACT) != 0
                || (access & ACC_NATIVE) != 0
                || (access & ACC_BRIDGE) != 0
                || (access & ACC_SYNTHETIC) != 0) {
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }
        // 不对类实例初始化方法注入
        if ("<init>".equals(name) || "<clinit>".equals(name)) {
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        return new MyMethodAdapter(className, access, name, descriptor, mv);
    }

}
