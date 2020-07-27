package com.wujiuye.agent;

import com.wujiuye.agent.utils.ByteCodeUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;

import static org.objectweb.asm.Opcodes.*;

public class ClassInstrumentationFactory {

    public static byte[] modifyClass(byte[] classfileBuffer) {
        ClassReader classReader = new ClassReader(classfileBuffer);
        // 过滤接口
        if ((classReader.getAccess() & ACC_INTERFACE) == ACC_INTERFACE) {
            return null;
        }
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS
                | ClassWriter.COMPUTE_FRAMES);
        MyClassAdapter classAdapter = new MyClassAdapter(
                classReader.getClassName(), classWriter);
        classReader.accept(classAdapter, 0);
        byte[] bytes = classWriter.toByteArray();
        try {
            ByteCodeUtils.savaToFile(classReader.getClassName().replace("/", "."), bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

}
