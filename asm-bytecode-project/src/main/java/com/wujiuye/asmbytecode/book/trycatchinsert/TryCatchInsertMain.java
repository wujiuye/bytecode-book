package com.wujiuye.asmbytecode.book.trycatchinsert;

import com.wujiuye.asmbytecode.book.fifth.util.ByteCodeHolder;
import org.objectweb.asm.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.objectweb.asm.Opcodes.*;

/**
 * 给方法的每个catch块插入一行打印代码
 *
 * @author wujiuye 2021/06/15
 */
public class TryCatchInsertMain {

    private final static TryCatchInsertClassLoader tryCatchInsertClassLoader
            = new TryCatchInsertClassLoader(TryCatchInsertMain.class.getClassLoader());

    public static Class<?> loadClass(final String className, final byte[] byteCode) throws ClassNotFoundException {
        ByteCodeHolder holder = new ByteCodeHolder() {
            @Override
            public String getClassName() {
                return className;
            }

            @Override
            public byte[] getByteCode() {
                return byteCode;
            }
        };
        tryCatchInsertClassLoader.add(className, holder);
        return tryCatchInsertClassLoader.loadClass(className);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String className = "com.wujiuye.asmbytecode.book.trycatchinsert.TryCatchInsertTestClass";
        ClassReader classReader = new ClassReader(className);
        MyClassWriter classWriter = new MyClassWriter(new ClassWriter(0));
        classReader.accept(classWriter, 0);
        byte[] newByteCode = classWriter.toByteArray();
        Class<?> cl = loadClass(className, newByteCode);
        cl.getMethod("test").invoke(null);
    }

    static class MyClassWriter extends ClassVisitor {

        private ClassWriter classWriter;

        public MyClassWriter(ClassWriter classWriter) {
            super(Opcodes.ASM6, classWriter);
            this.classWriter = classWriter;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            if ("test".equals(name)) {
                MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
                return new MyMethodWriter(methodVisitor);
            }
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }

        public byte[] toByteArray() {
            return classWriter.toByteArray();
        }

    }

    static class MyMethodWriter extends MethodVisitor {


        public MyMethodWriter(MethodVisitor methodVisitor) {
            super(Opcodes.ASM6, methodVisitor);
        }

        /**
         * 实现在每个catch块后插入字节码指令
         * 利用访问StackMapTable属性方法插入，利用try{}基本块的结束刚好是catch基本块的开始特性，每个基本块结束都会调用visitFrame生成一个StackMapTable属性
         *
         * @param type
         * @param nLocal
         * @param local
         * @param nStack
         * @param stack
         */
        @Override
        public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
            super.visitFrame(type, nLocal, local, nStack, stack);
            // 栈顶为异常
            if (nStack > 0 && ("java/lang/Exception".equals(stack[nStack - 1])
                    || "java/lang/Throwable".equals(stack[nStack - 1]))) {
                // 在这里插入
                System.out.println("===== visitFrame ======");
                // 将异常存储到局部变量表
                super.visitVarInsn(ASTORE, nLocal + 1);
                // 生成System.out.println(e.getMessage());
                super.visitFieldInsn(GETSTATIC,
                        Type.getInternalName(System.class),
                        "out",
                        Type.getDescriptor(System.out.getClass()));
                //   生成e.getMessage()
                super.visitVarInsn(ALOAD, nLocal + 1);
                super.visitMethodInsn(INVOKEVIRTUAL, Type.getInternalName(Throwable.class), "getMessage", "()Ljava/lang/String;", false);
                super.visitMethodInsn(INVOKEVIRTUAL,
                        Type.getInternalName(System.out.getClass()),
                        "println",
                        "(Ljava/lang/String;)V", false);
                // 将异常从局部变量表恢复到栈顶
                super.visitVarInsn(ALOAD, nLocal + 1);
            }
        }

        @Override
        public void visitMaxs(int maxStack, int maxLocals) {
            // +1
            super.visitMaxs(maxStack, maxLocals + 1);
        }
    }

}
