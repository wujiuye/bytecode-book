package com.wujiuye.agent;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class MyJavaAgent {

//    public static void premain(String agentOps, Instrumentation instrumentation) {
//        System.out.println("premain function run...");
//        instrumentation.addTransformer(new BusinessClassFileTransformer());
//    }

    public static void agentmain(String agentOps, Instrumentation instrumentation) {
        System.out.println("agentmain function run...");
        BusinessClassFileTransformer transformer = new BusinessClassFileTransformer();
        // 给Instrumentation注册类转换器
        // 第二个:
        //    false: 注册一个不可重转换的转换器；
        //    true: 注册一个可重转换的转换器；
        instrumentation.addTransformer(transformer, true);

        // 获取所有已经加载的类
        Class<?>[] classs = instrumentation.getAllLoadedClasses();
        for (Class<?> cla : classs) {
            // 过滤掉java与sun包下的类
            if (cla.getName().startsWith("java")
                    || cla.getName().startsWith("sun")) {
                continue;
            }
            // 过滤掉与项目无关的一些类
            if (cla.getName().startsWith("com.intellij")
                    || cla.getName().startsWith("org.jetbrains")) {
                continue;
            }
            if (cla.getName().startsWith("[")) {
                continue;
            }
            // 把my-java-agent的包排除掉
            if (cla.getName().startsWith("com.wujiuye.agent")) {
                continue;
            }
            try {
                // 重转换类，重转换类不允许给类添加或移除字段
                instrumentation.retransformClasses(cla);
            } catch (UnmodifiableClassException e) {
                e.printStackTrace();
            }
        }
        // 完成后可将转换器移除
        instrumentation.removeTransformer(transformer);
    }

}
