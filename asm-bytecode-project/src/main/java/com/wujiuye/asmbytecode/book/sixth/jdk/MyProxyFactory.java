package com.wujiuye.asmbytecode.book.sixth.jdk;

import org.objectweb.asm.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.*;

public class MyProxyFactory {

    private static String[] getInternalNames(Class<?>[] classes) {
        String[] names = new String[classes.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = Type.getInternalName(classes[i]);
        }
        return names;
    }

    public static byte[] createProxyClass(String className, Class<?>[] interfaces) {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        cw.visit(Opcodes.V1_8, ACC_PUBLIC, className, null,
                Type.getInternalName(MyProxy.class),
                getInternalNames(interfaces));
        // 添加带参构造方法，调用父类MyProxy的带参构造方法
        createInitMethod(cw);
        // 实现接口的方法
        for (Class<?> interfaceClass : interfaces) {
            implInterfaceMethod(cw, className, interfaceClass);
        }
        // 添加静态代码块，获取method
        addStaticBlock(cw, className, interfaces);
        cw.visitEnd();
        return cw.toByteArray();
    }

    private static void addStaticBlock(ClassWriter cw, String className, Class<?>[] interfaces) {
        // 给<clinit>方法添加static访问标志。对应java代码的静态代码块
        MethodVisitor mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V",
                null, null);
        mv.visitCode();
        // 遍历所有接口
        for (Class cla : interfaces) {
            // 遍历接口的所有方法
            Method[] methods = cla.getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];

                // 调用Class的forName方法获取一个Class实例
                String fieldName = "_" + cla.getSimpleName() + "_" + i;
                mv.visitLdcInsn(cla.getName());
                mv.visitMethodInsn(INVOKESTATIC, Type.getInternalName(Class.class),
                        "forName",
                        "(Ljava/lang/String;)Ljava/lang/Class;",
                        false);

                // 调用Class的getMethod方法，方法需要两个参数，一个是方法名称，一个是方法参数类型数组
                // 参数1
                mv.visitLdcInsn(method.getName());
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
        }
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }

    private static void createInitMethod(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>",
                "(Ljava/lang/reflect/InvocationHandler;)V",
                null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKESPECIAL,
                Type.getInternalName(MyProxy.class),
                "<init>",
                "(Ljava/lang/reflect/InvocationHandler;)V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }

    private static void implInterfaceMethod(ClassWriter cw, String className, Class<?> interfaceClass) {
        Method[] methods = interfaceClass.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            // 给代理类添加静态字段
            cw.visitField(ACC_PRIVATE | ACC_STATIC, "_" + interfaceClass.getSimpleName() + "_" + i,
                    Type.getDescriptor(Method.class),
                    null, null);

            // 给实现类实现该接口方法
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, method.getName(),
                    Type.getMethodDescriptor(method),
                    null,
                    new String[]{Type.getInternalName(Exception.class)});
            mv.visitCode();

            Label from = new Label();
            Label to = new Label();
            Label target = new Label();

            mv.visitLabel(from);

            // 获取父类的字段，字段名为h，类型为InvocationHandler
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD,
                    Type.getInternalName(MyProxy.class),
                    "h",
                    Type.getDescriptor(InvocationHandler.class));

            // 准备调用InvocationHandler的invoke方法的三个参数
            // 第一个参数
            mv.visitVarInsn(ALOAD, 0);
            // 第二个参数。获取静态字段
            mv.visitFieldInsn(GETSTATIC,
                    className,
                    "_" + interfaceClass.getSimpleName() + "_" + i,
                    Type.getDescriptor(Method.class));
            // 第三个参数，将当前方法的参数构造成数组
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

            // 调用InvocationHandler的invoke方法
            mv.visitMethodInsn(INVOKEINTERFACE,
                    Type.getInternalName(InvocationHandler.class),
                    "invoke",
                    "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;",
                    true);

            // 添加return指令
            addReturnInstruc(mv, method.getReturnType());

            mv.visitLabel(to);
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

