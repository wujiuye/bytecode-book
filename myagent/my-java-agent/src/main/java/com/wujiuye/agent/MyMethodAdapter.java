package com.wujiuye.agent;


import com.wujiuye.agent.utils.ByteCodeUtils;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

public class MyMethodAdapter extends MethodVisitor {

    private String className;
    private boolean isStaticMethod = false;
    private String methodName;
    private String descriptor;
    private String[] paramDescriptors;

    public MyMethodAdapter(String className,
                           int access,
                           String methodName,
                           String descriptor,
                           MethodVisitor methodVisitor) {
        super(ASM6, methodVisitor);
        // 根据方法的访问标志判断是否是静态方法
        if ((access & ACC_STATIC) == ACC_STATIC) {
            isStaticMethod = true;
        }
        this.className = className;
        this.methodName = methodName;
        this.descriptor = descriptor;
        // 根据方法描述符获取参数类型描述符
        this.paramDescriptors = ByteCodeUtils.getParamDescriptors(descriptor);
    }

    private Label from = new Label(),
            to = new Label(),
            target = new Label();

    @Override
    public void visitCode() {
        super.visitCode();
        // 插入埋点代码，调用CallLogAspect的before方法
        this.visitLdcInsn(this.className);
        this.visitLdcInsn(this.methodName);
        this.visitLdcInsn(this.descriptor);
        if (paramDescriptors == null) {
            this.visitInsn(ACONST_NULL);
        } else {
            // 数组的大小
            if (paramDescriptors.length >= 4) {
                mv.visitVarInsn(BIPUSH, paramDescriptors.length);
            } else {
                switch (paramDescriptors.length) {
                    case 1:
                        mv.visitInsn(ICONST_1);
                        break;
                    case 2:
                        mv.visitInsn(ICONST_2);
                        break;
                    case 3:
                        mv.visitInsn(ICONST_3);
                        break;
                    default:
                        mv.visitInsn(ICONST_0);
                }
            }
            // 创建Object数组
            mv.visitTypeInsn(ANEWARRAY, Type.getInternalName(Object.class));

            // 方法第一个参数在局部变量表中的位置
            // 非静态方法排除this，即从1开始
            int localIndex = isStaticMethod ? 0 : 1;
            // 2. 给数组赋值
            for (int i = 0; i < paramDescriptors.length; i++) {
                // dup一份数组引用
                mv.visitInsn(DUP);
                // 访问数组的索引
                switch (i) {
                    case 0:
                        mv.visitInsn(ICONST_0);
                        break;
                    case 1:
                        mv.visitInsn(ICONST_1);
                        break;
                    case 2:
                        mv.visitInsn(ICONST_2);
                        break;
                    case 3:
                        mv.visitInsn(ICONST_3);
                        break;
                    default:
                        mv.visitVarInsn(BIPUSH, i);
                }
                // 将基本数据类型转为Object类型
                // 调用基本数据类型对应的包装类型的valueOf静态方法
                String type = paramDescriptors[i];
                if ("Z".equals(type)) {
                    mv.visitVarInsn(ILOAD, localIndex++);
                    mv.visitMethodInsn(INVOKESTATIC,
                            Type.getInternalName(Boolean.class),
                            "valueOf",
                            "(Z)Ljava/lang/Boolean;", false);
                } else if ("C".equals(type)) {
                    mv.visitVarInsn(ILOAD, localIndex++);
                    mv.visitMethodInsn(INVOKESTATIC,
                            Type.getInternalName(Character.class),
                            "valueOf",
                            "(C)Ljava/lang/Character;", false);
                } else if ("B".equals(type)) {
                    mv.visitVarInsn(ILOAD, localIndex++);
                    mv.visitMethodInsn(INVOKESTATIC,
                            Type.getInternalName(Byte.class),
                            "valueOf",
                            "(B)Ljava/lang/Byte;", false);
                } else if ("S".equals(type)) {
                    mv.visitVarInsn(ILOAD, localIndex++);
                    mv.visitMethodInsn(INVOKESTATIC,
                            Type.getInternalName(Short.class),
                            "valueOf",
                            "(S)Ljava/lang/Short;", false);
                } else if ("I".equals(type)) {
                    mv.visitVarInsn(ILOAD, localIndex++);
                    mv.visitMethodInsn(INVOKESTATIC,
                            Type.getInternalName(Integer.class),
                            "valueOf",
                            "(I)Ljava/lang/Integer;", false);
                } else if ("F".equals(type)) {
                    mv.visitVarInsn(FLOAD, localIndex++);
                    mv.visitMethodInsn(INVOKESTATIC,
                            Type.getInternalName(Float.class),
                            "valueOf",
                            "(F)Ljava/lang/Float;", false);
                } else if ("J".equals(type)) {
                    // long类型占两个slot
                    mv.visitVarInsn(LLOAD, localIndex);
                    localIndex += 2;
                    mv.visitMethodInsn(INVOKESTATIC,
                            Type.getInternalName(Long.class),
                            "valueOf",
                            "(J)Ljava/lang/Long;", false);
                } else if ("D".equals(type)) {
                    // double类型占两个slot
                    localIndex += 2;
                    mv.visitVarInsn(DLOAD, localIndex);
                    mv.visitMethodInsn(INVOKESTATIC,
                            Type.getInternalName(Double.class),
                            "valueOf",
                            "(D)Ljava/lang/Double;", false);
                } else {
                    // 数组或对象
                    mv.visitVarInsn(ALOAD, localIndex++);
                }
                // 给数组指定下标元素赋值
                mv.visitInsn(AASTORE);
            }
        }
        this.visitMethodInsn(INVOKESTATIC,
                Type.getInternalName(CallLogAspect.class),
                "before",
                "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V",
                false);

        // 设置try代码块的开始
        this.visitLabel(from);
    }

    private int nextLocalIndex = 0;

    @Override
    public void visitVarInsn(int opcode, int var) {
        super.visitVarInsn(opcode, var);
        if (opcode == ILOAD
                || opcode == FLOAD
                || opcode == ALOAD
                || opcode == ISTORE
                || opcode == FSTORE
                || opcode == ASTORE) {
            if (var > nextLocalIndex) {
                nextLocalIndex = var + 1;
            }
        } else if (opcode == LLOAD
                || opcode == DLOAD
                || opcode == LSTORE
                || opcode == DSTORE) {
            if (var + 1 > nextLocalIndex) {
                nextLocalIndex = var + 2;
            }
        }
    }

    /**
     * 在覆写visitInsn方法时，注意不要造成死递归调用。
     *
     * @param opcode
     */
    @Override
    public void visitInsn(int opcode) {
        int li = nextLocalIndex;
        switch (opcode) {
            case RETURN:
                // 方法无返回值
                this.visitLdcInsn(this.className);
                this.visitLdcInsn(this.methodName);
                this.visitLdcInsn(this.descriptor);
                // null入栈
                this.visitInsn(ACONST_NULL);
                this.visitMethodInsn(INVOKESTATIC,
                        Type.getInternalName(CallLogAspect.class),
                        "after",
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V",
                        false);
                break;
            case IRETURN:
                this.visitInsn(DUP);
                this.visitVarInsn(ISTORE, li);

                this.visitLdcInsn(this.className);
                this.visitLdcInsn(this.methodName);
                this.visitLdcInsn(this.descriptor);
                // 将返回值由int类型转为Integer类型
                this.visitVarInsn(ILOAD, li);
                this.visitMethodInsn(INVOKESTATIC,
                        Type.getInternalName(Integer.class),
                        "valueOf",
                        "(J)Ljava/lang/Integer;", false);
                // 调用埋点方法
                this.visitMethodInsn(INVOKESTATIC,
                        Type.getInternalName(CallLogAspect.class),
                        "after",
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V",
                        false);
                break;
            case FRETURN:
                this.visitInsn(DUP);
                this.visitVarInsn(FSTORE, li);

                this.visitLdcInsn(this.className);
                this.visitLdcInsn(this.methodName);
                this.visitLdcInsn(this.descriptor);

                this.visitVarInsn(FLOAD, li);
                this.visitMethodInsn(INVOKESTATIC,
                        Type.getInternalName(Float.class),
                        "valueOf",
                        "(J)Ljava/lang/Float;", false);
                this.visitMethodInsn(INVOKESTATIC,
                        Type.getInternalName(CallLogAspect.class),
                        "after",
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V",
                        false);
                break;
            case LRETURN:
                // long占两个slot
                this.visitInsn(DUP2);
                this.visitVarInsn(LSTORE, li);

                this.visitLdcInsn(this.className);
                this.visitLdcInsn(this.methodName);
                this.visitLdcInsn(this.descriptor);

                this.visitVarInsn(LLOAD, li);
                this.visitMethodInsn(INVOKESTATIC,
                        Type.getInternalName(Long.class),
                        "valueOf",
                        "(J)Ljava/lang/Long;", false);
                this.visitMethodInsn(INVOKESTATIC,
                        Type.getInternalName(CallLogAspect.class),
                        "after",
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V",
                        false);
                break;
            case DRETURN:
                // double占两个slot
                this.visitInsn(DUP2);
                this.visitVarInsn(DSTORE, li);

                this.visitLdcInsn(this.className);
                this.visitLdcInsn(this.methodName);
                this.visitLdcInsn(this.descriptor);

                this.visitVarInsn(DLOAD, li);
                this.visitMethodInsn(INVOKESTATIC,
                        Type.getInternalName(Double.class),
                        "valueOf",
                        "(J)Ljava/lang/Double;", false);
                this.visitMethodInsn(INVOKESTATIC,
                        Type.getInternalName(CallLogAspect.class),
                        "after",
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V",
                        false);
                break;
            case ARETURN:
                // 先将返回值dup出一份，将一份保存到局部变量表
                // 还有一份在操作数栈，留给ARETURN指令的
                this.visitInsn(DUP);
                this.visitVarInsn(ASTORE, li);

                //  插入埋点代码，调用CallLogAspect的after方法
                this.visitLdcInsn(this.className);
                this.visitLdcInsn(this.methodName);
                this.visitLdcInsn(this.descriptor);
                // 从局部变量表加载返回值
                this.visitVarInsn(ALOAD, li);
                this.visitMethodInsn(INVOKESTATIC,
                        Type.getInternalName(CallLogAspect.class),
                        "after",
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V",
                        false);
                break;
        }
        super.visitInsn(opcode);
    }

    /**
     * 由于我们在创建ClassWriter对象时，添加了COMPUTE_MAXS标志，
     * 在ClassWriter的visitMethod方法的实现中，会创建一个MethodWriter，
     * 会将COMPUTE_MAXS标志传递给这个MethodWriter对象，
     * 而在MethodWriter的visitMaxs方法中，如果有COMPUTE_MAXS标志，
     * 则会自动帮我们计算出新的局部变量表和操作数栈的大小。因此，在覆写visitMaxs方法时，
     * 不必修改局部变量表和操作数栈的大小。
     * <p>
     * 我们需要在visitMaxs中插入catch块代码，需要添加一个局部变量来保存catch住的异常，
     * 在执行完埋点方法后，将catch住的异常从局部变量表中加载到操作数栈顶，
     * 以执行athrow指令将异常抛出。
     *
     * @param maxStack
     * @param maxLocals
     */
    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        this.visitLabel(to);
        this.visitLabel(target);

        // catch块要做的事情
        // 将当前栈顶的Throwable对象存储到局部变量表，执行完埋点后需要抛出
        this.visitVarInsn(ASTORE, maxLocals + 1);

        // 插入埋点代码，调用CallLogAspect的error方法
        this.visitLdcInsn(this.className);
        this.visitLdcInsn(this.methodName);
        this.visitLdcInsn(this.descriptor);
        this.visitVarInsn(ALOAD, maxLocals + 1);
        this.visitMethodInsn(INVOKESTATIC,
                Type.getInternalName(CallLogAspect.class),
                "error",
                "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V",
                false);

        // 抛出Throwable异常
        this.visitVarInsn(ALOAD, maxLocals + 1);
        this.visitInsn(ATHROW);

        this.visitTryCatchBlock(from, to, target, Type.getInternalName(Throwable.class));
        super.visitMaxs(maxStack, maxLocals);
    }

}
