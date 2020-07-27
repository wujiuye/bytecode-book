package com.wujiuye.asmbytecode.book.second;

import com.wujiuye.asmbytecode.book.second.type.ClassFile;
import com.wujiuye.asmbytecode.book.second.type.U2;
import com.wujiuye.asmbytecode.book.second.type.cp.CONSTANT_Class_info;
import com.wujiuye.asmbytecode.book.second.type.cp.CONSTANT_Utf8_info;
import org.junit.Test;

import java.nio.ByteBuffer;

public class InterfacesHandlerTest {

    @Test
    public void testInterfacesHandlerHandler() throws Exception {
        ByteBuffer codeBuf = ClassFileAnalysisMain.readFile("/Users/wjy/MyProjects/asm-bytecode-project/build/classes/java/main/com/wujiuye/asmbytecode/book/bytecode/handler/InterfacesHandler.class");
        ClassFile classFile = ClassFileAnalysiser.analysis(codeBuf);
        System.out.println("接口总数:" + classFile.getInterfaces_count().toInt());
        if (classFile.getInterfaces_count().toInt() == 0) {
            return;
        }
        U2[] interfaces = classFile.getInterfaces();
        for (U2 interfacesIndex : interfaces) {
            CONSTANT_Class_info interfaces_class_info = (CONSTANT_Class_info) classFile.getConstant_pool()[interfacesIndex.toInt() - 1];
            CONSTANT_Utf8_info interfaces_class_name_index = (CONSTANT_Utf8_info) classFile.getConstant_pool()[interfaces_class_info.getName_index().toInt() - 1];
            System.out.println(interfaces_class_name_index);
        }
    }

}
