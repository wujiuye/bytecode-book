package com.wujiuye.asmbytecode.book.second;

import com.wujiuye.asmbytecode.book.second.type.ClassFile;
import com.wujiuye.asmbytecode.book.second.type.MethodInfo;
import com.wujiuye.asmbytecode.book.second.type.U2;
import com.wujiuye.asmbytecode.book.second.type.cp.CONSTANT_Utf8_info;
import com.wujiuye.asmbytecode.book.second.util.FieldAccessFlagUtils;
import org.junit.Test;

import java.nio.ByteBuffer;

public class MethodHandlerTest {

    private static String getName(U2 name_index, ClassFile classFile) {
        CONSTANT_Utf8_info name_info = (CONSTANT_Utf8_info) classFile.getConstant_pool()[name_index.toInt() - 1];
        return name_info.toString();
    }

    @Test
    public void testMethodHandlerHandler() throws Exception {
        ByteBuffer codeBuf = ClassFileAnalysisMain.readFile("/Users/wjy/MyProjects/asm-bytecode-project/build/classes/java/main/com/wujiuye/asmbytecode/book/vmstack/Builder.class");
        ClassFile classFile = ClassFileAnalysiser.analysis(codeBuf);
        System.out.println("方法总数:" + classFile.getMethods_count().toInt());
        System.out.println();
        MethodInfo[] methodInfos = classFile.getMethods();
        for (MethodInfo methodInfo : methodInfos) {
            System.out.println("访问标志和属性：" +
                    FieldAccessFlagUtils.toFieldAccessFlagsString(methodInfo.getAccess_flags()));
            System.out.println("方法名：" + getName(methodInfo.getName_index(), classFile));
            System.out.println("方法描述符：" + getName(methodInfo.getDescriptor_index(), classFile));
            System.out.println("属性总数：" + methodInfo.getAttributes_count().toInt());
            System.out.println();
        }
    }

}
