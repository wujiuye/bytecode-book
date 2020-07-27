package com.wujiuye.asmbytecode.book.second;

import com.wujiuye.asmbytecode.book.second.type.ClassFile;
import com.wujiuye.asmbytecode.book.second.type.FieldInfo;
import com.wujiuye.asmbytecode.book.second.type.U2;
import com.wujiuye.asmbytecode.book.second.type.cp.CONSTANT_Utf8_info;
import com.wujiuye.asmbytecode.book.second.util.FieldAccessFlagUtils;
import org.junit.Test;

import java.nio.ByteBuffer;

public class FieldHandlerTest {

    private static String getName(U2 name_index, ClassFile classFile) {
        CONSTANT_Utf8_info name_info = (CONSTANT_Utf8_info) classFile.getConstant_pool()[name_index.toInt() - 1];
        return name_info.toString();
    }

    @Test
    public void testFieldHandlerHandler() throws Exception {
        ByteBuffer codeBuf = ClassFileAnalysisMain.readFile("/Users/wjy/MyProjects/asm-bytecode-project/build/classes/java/main/com/wujiuye/asmbytecode/book/vmstack/Builder.class");
        ClassFile classFile = ClassFileAnalysiser.analysis(codeBuf);
        System.out.println("字段总数:" + classFile.getFields_count().toInt());
        System.out.println();
        FieldInfo[] fieldInfos = classFile.getFields();
        for (FieldInfo fieldInfo : fieldInfos) {
            System.out.println("访问标志和属性：" +
                    FieldAccessFlagUtils.toFieldAccessFlagsString(fieldInfo.getAccess_flags()));
            System.out.println("字段名：" + getName(fieldInfo.getName_index(), classFile));
            System.out.println("类型描述符：" + getName(fieldInfo.getDescriptor_index(), classFile));
            System.out.println("属性总数：" + fieldInfo.getAttributes_count().toInt());
            System.out.println();
        }
    }

}
