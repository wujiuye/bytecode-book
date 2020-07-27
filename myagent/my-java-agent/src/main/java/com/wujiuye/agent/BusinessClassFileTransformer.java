package com.wujiuye.agent;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class BusinessClassFileTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        // 过滤掉不需要插桩的类
        if (className.startsWith("java")
                || className.startsWith("sun")) {
            return null;
        }
        if (!className.startsWith("com/wujiuye")) {
            return null;
        }
        System.err.println(className + "==>" + classBeingRedefined);
        return ClassInstrumentationFactory.modifyClass(classfileBuffer);
    }

}
