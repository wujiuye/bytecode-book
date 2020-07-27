package com.wujiuye.asmbytecode.book.second.attribute;

import com.wujiuye.asmbytecode.book.second.ClassFileAnalysisMain;
import com.wujiuye.asmbytecode.book.second.type.AttributeInfo;
import com.wujiuye.asmbytecode.book.second.type.ClassFile;
import com.wujiuye.asmbytecode.book.second.type.FieldInfo;
import com.wujiuye.asmbytecode.book.second.type.U2;
import com.wujiuye.asmbytecode.book.second.type.cp.*;
import com.wujiuye.asmbytecode.book.second.ClassFileAnalysiser;
import org.junit.Test;

import java.nio.ByteBuffer;

public class ConstantValueAttributeTest {

    @Test
    public void testConstantValue() throws Exception {
        ByteBuffer codeBuf = ClassFileAnalysisMain.readFile("/Users/wjy/MyProjects/asm-bytecode-project/build/classes/java/main/com/wujiuye/asmbytecode/book/bytecode/attribute/TestConstantValueInterface.class");
        ClassFile classFile = ClassFileAnalysiser.analysis(codeBuf);
        // 获取所有字段
        FieldInfo[] fieldInfos = classFile.getFields();
        for (FieldInfo fieldInfo : fieldInfos) {
            // 获取字段的所有属性
            AttributeInfo[] attributeInfos = fieldInfo.getAttributes();
            if (attributeInfos == null || attributeInfos.length == 0) {
                continue;
            }
            System.out.println("字段：" + classFile.getConstant_pool()[fieldInfo.getName_index().toInt() - 1]);
            for (AttributeInfo attributeInfo : attributeInfos) {
                // 获取属性的名称
                U2 name_index = attributeInfo.getAttribute_name_index();
                CONSTANT_Utf8_info name_info = (CONSTANT_Utf8_info) classFile.getConstant_pool()[name_index.toInt() - 1];
                String name = new String(name_info.getBytes());
                if (name.equalsIgnoreCase("ConstantValue")) {
                    // 属性二次解析
                    ConstantValue_attribute constantValue = AttributeProcessingFactory.processingConstantValue(attributeInfo);
                    // 说去constantvalue_index，从常量池中取值
                    U2 cv_index = constantValue.getConstantvalue_index();
                    Object cv = classFile.getConstant_pool()[cv_index.toInt() - 1];
                    // 需要判断常量的类型
                    if (cv instanceof CONSTANT_Utf8_info) {
                        System.out.println("ConstantValue：" + cv.toString());
                    } else if (cv instanceof CONSTANT_Integer_info) {
                        System.out.println("ConstantValue：" +
                                ((CONSTANT_Integer_info) cv).getBytes().toInt());
                    } else if (cv instanceof CONSTANT_Float_info) {
                        // todo
                    } else if (cv instanceof CONSTANT_Long_info) {
                        // todo
                    } else if (cv instanceof CONSTANT_Double_info) {
                        // todo
                    }
                }
            }
        }
    }

}
