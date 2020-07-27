package com.wujiuye.asmbytecode.book.sixth.cglib;

import org.objectweb.asm.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Opcodes.RETURN;

public class SubclassProxyFactory {

    // 不拦截的方法
    private static final List<String> EXCLUDE_METHOD = Arrays.asList("wait", "equals",
            "toString", "hashCode", "getClass", "notify", "notifyAll");

    public static byte[] createProxyClass(String className, Class<?> superclass) {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        cw.visit(Opcodes.V1_8, ACC_PUBLIC, className, null,
                Type.getInternalName(superclass),
                null);
        // 创建构造方法
        createInitMethod(cw, className, superclass);
        // 获取需要拦截的方法
        Method[] methods = getProxyMethod(superclass);
        if (methods.length > 0) {
            // 添加静态代码块，生成通过反射获取Method的字节码
            addStaticBlock(cw, className, superclass, methods);
            // 覆写父类的方法
            overrideMethods(cw, className, superclass, methods);
        }
        cw.visitEnd();
        return cw.toByteArray();
    }

    /**
     * 生成类的实例初始化方法，要求传递一个MyMethodInterceptor类的参数，
     * 先调用父类的无参实例初始化方法，再为子类添加一个类型为MyMethodInterceptor且名称为h的字段，
     * 将参数赋值给h字段。
     *
     * @param cw
     * @param className
     * @param superclass
     */
    private static void createInitMethod(ClassWriter cw, String className, Class<?> superclass) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>",
                "(" + Type.getDescriptor(MyMethodInterceptor.class) + ")V",
                null, null);
        mv.visitCode();

        // 调用父类的无参实例初始化方法
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL,
                Type.getInternalName(superclass),
                "<init>", "()V", false);

        // 添加一个字段，字段名：h，字段类型为：MyMethodInterceptor
        cw.visitField(ACC_PRIVATE, "h", Type.getDescriptor(MyMethodInterceptor.class),
                null, null);

        // 为字段赋值
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitFieldInsn(PUTFIELD, className, "h",
                Type.getDescriptor(MyMethodInterceptor.class));

        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }

    /**
     * 获取允许覆写的方法
     *
     * @param superclass
     * @return
     */
    private static Method[] getProxyMethod(Class<?> superclass) {
        // 获取superclass类的所有public方法，包括superclass的父类中的public方法
        Method[] methods = superclass.getMethods();
        List<Method> methodList = new ArrayList<>(methods.length);
        for (Method method : methods) {
            // 过滤不需要覆写的方法
            if (EXCLUDE_METHOD.contains(method.getName())) {
                continue;
            }
            // 跳过final修饰的方法
            if ((method.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
                continue;
            }
            methodList.add(method);
        }
        return methodList.toArray(new Method[]{});
    }

    /**
     * 添加静态代码块，为代理类添加静态字段。
     *
     * @param cw
     * @param className
     * @param superclass
     * @param methods
     */
    private static void addStaticBlock(ClassWriter cw, String className,
                                       Class<?> superclass, Method[] methods) {
        // 给<clinit>方法添加static访问标志。对应java代码的静态代码块
        MethodVisitor mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V",
                null, null);
        mv.visitCode();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];

            // 字段名取方法名称+i，避免重载方法的字段名相同的情况
            String fieldName = "_" + method.getName() + "_" + i;
            // 添加静态字段
            cw.visitField(ACC_PRIVATE | ACC_STATIC, fieldName, Type.getDescriptor(Method.class), null, null);

            // 生成一个调用父类方法的代理方法
            addCallSuperclassMethod(cw, superclass, method.getName() + "_" + i, method);

            // 调用Class的forName方法获取this的Class实例
            mv.visitLdcInsn(className.replace("/", "."));
            mv.visitMethodInsn(INVOKESTATIC, Type.getInternalName(Class.class),
                    "forName",
                    "(Ljava/lang/String;)Ljava/lang/Class;",
                    false);

            // 调用Class的getMethod方法，方法需要两个参数，一个是方法名称，一个是方法参数类型数组
            // 参数1
            mv.visitLdcInsn(method.getName() + "_" + i);
            // 参数2
            // 创建数组，并为数组的每个元素赋值
            Class[] methodParamTypes = method.getParameterTypes();
            if (methodParamTypes.length == 0) {
                mv.visitInsn(ACONST_NULL);
            } else {
                // 数组大小
                switch (methodParamTypes.length) {
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
                        mv.visitVarInsn(BIPUSH, methodParamTypes.length);
                }
                mv.visitTypeInsn(ANEWARRAY, Type.getInternalName(Class.class));
                // 为数组元素赋值，数组元素类型为java.lang.Class
                for (int index = 0; index < methodParamTypes.length; index++) {
                    mv.visitInsn(DUP);
                    // 数组元素下标
                    switch (index) {
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
                            break;
                    }
                    mv.visitLdcInsn(methodParamTypes[index].getName());
                    // 调用forName获取参数的Class实例
                    mv.visitMethodInsn(INVOKESTATIC, Type.getInternalName(Class.class),
                            "forName",
                            "(Ljava/lang/String;)Ljava/lang/Class;",
                            false);
                    // 存储到数组
                    mv.visitInsn(AASTORE);
                }
            }
            // 参数准备完毕，调用Class的getMethod方法获取Method
            mv.visitMethodInsn(INVOKEVIRTUAL, Type.getInternalName(Class.class),
                    "getMethod",
                    "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
            // 为静态字段赋值
            mv.visitFieldInsn(PUTSTATIC, className, fieldName, Type.getDescriptor(Method.class));
        }
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }

    /**
     * 生成调用父类方法的子类代理方法
     *
     * @param cw
     * @param superclass
     * @param methodName
     * @param method
     */
    private static void addCallSuperclassMethod(ClassWriter cw, Class<?> superclass, String methodName, Method method) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_FINAL, methodName,
                Type.getMethodDescriptor(method),
                null, null);
        mv.visitCode();
        // this
        mv.visitVarInsn(ALOAD, 0);
        // 参数入栈
        Class<?>[] paramTypes = method.getParameterTypes();
        if (paramTypes.length > 0) {
            for (int i = 0; i < paramTypes.length; i++) {
                Class<?> paramType = paramTypes[i];
                if (paramType == int.class) {
                    mv.visitVarInsn(ILOAD, i + 1);
                } else if (paramType == long.class) {
                    mv.visitVarInsn(LLOAD, i + 1);
                }
                //....
                else {
                    mv.visitVarInsn(ALOAD, i + 1);
                }
            }
        }
        // 调用父类的方法
        mv.visitMethodInsn(INVOKESPECIAL, Type.getInternalName(superclass),
                method.getName(), Type.getMethodDescriptor(method), false);
        // 生成return指令
        addReturnInstruc(mv, method.getReturnType());

        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }

    /**
     * 覆写父类的方法，拦截方法的执行，交给方法拦截器处理
     *
     * @param cw
     * @param className
     * @param superclass
     * @param methods
     */
    private static void overrideMethods(ClassWriter cw, String className,
                                        Class<?> superclass, Method[] methods) {
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            // 覆写父类的方法
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, method.getName(),
                    Type.getMethodDescriptor(method),
                    null,
                    new String[]{Type.getInternalName(Exception.class)});
            mv.visitCode();

            Label from = new Label();
            Label to = new Label();
            Label target = new Label();

            // try开始
            mv.visitLabel(from);

            // 获取字段，字段名为h，类型为MyMethodInterceptor
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD,
                    className,
                    "h",
                    Type.getDescriptor(MyMethodInterceptor.class));

            // 准备调用MyMethodInterceptor的intercept方法的三个参数
            // 第一个参数
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, Type.getInternalName(superclass));
            // 第二个参数。获取静态字段
            mv.visitFieldInsn(GETSTATIC,
                    className,
                    "_" + method.getName() + "_" + i,
                    Type.getDescriptor(Method.class));

            // 第三个参数
            mv.visitLdcInsn(method.getName());

            // 第四个参数，将当前方法的参数构造成数组
            int paramCount = method.getParameterCount();
            if (paramCount == 0) {
                mv.visitInsn(ACONST_NULL);
            } else {
                // 数组大小
                switch (paramCount) {
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
                        mv.visitVarInsn(BIPUSH, paramCount);
                }
                // 创建数组
                mv.visitTypeInsn(ANEWARRAY, Type.getInternalName(Object.class));
                // 为数组元素赋值
                for (int index = 1; index <= paramCount; index++) {
                    mv.visitInsn(DUP);
                    switch (index - 1) {
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
                            mv.visitVarInsn(BIPUSH, i - 1);
                            break;
                    }
                    // 暂不考虑参数类型为基本数据类型的情况
                    mv.visitVarInsn(ALOAD, index);
                    mv.visitInsn(AASTORE);
                }
            }

            // 调用MyMethodInterceptor的intercept方法
            mv.visitMethodInsn(INVOKEINTERFACE,
                    Type.getInternalName(MyMethodInterceptor.class),
                    "intercept",
                    "(Ljava/lang/Object;Ljava/lang/reflect/Method;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;",
                    true);

            // 添加return指令
            addReturnInstruc(mv, method.getReturnType());

            // try结束
            mv.visitLabel(to);
            // catch开始
            mv.visitLabel(target);
            // 抛出异常
            mv.visitInsn(ATHROW);
            // 添加TryCatch代码块
            mv.visitTryCatchBlock(from, to, target, Type.getInternalName(Exception.class));

            mv.visitFrame(F_FULL, 0, null, 0, null);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
    }

    private static void addReturnInstruc(MethodVisitor mv, Class returnType) {
        if (returnType == void.class) {
            mv.visitInsn(RETURN);
        } else if (returnType == int.class) {
            // 先将InvocationHandler的invoke方法返回值由Object类型转为Integer类型
            mv.visitTypeInsn(CHECKCAST, Type.getInternalName(Integer.class));
            // 调用Integer的intValue方法
            mv.visitMethodInsn(INVOKEVIRTUAL, Type.getInternalName(Integer.class),
                    "intValue",
                    "()I", false);
            mv.visitInsn(IRETURN);
        }
        // ....
        else {
            mv.visitTypeInsn(CHECKCAST, Type.getInternalName(returnType));
            mv.visitInsn(ARETURN);
        }
    }

}
