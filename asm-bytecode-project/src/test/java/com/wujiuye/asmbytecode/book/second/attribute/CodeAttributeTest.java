package com.wujiuye.asmbytecode.book.second.attribute;

import com.wujiuye.asmbytecode.book.second.ClassFileAnalysisMain;
import com.wujiuye.asmbytecode.book.second.type.*;
import com.wujiuye.asmbytecode.book.second.type.cp.*;
import com.wujiuye.asmbytecode.book.second.ClassFileAnalysiser;
import com.wujiuye.asmbytecode.book.second.util.HexStringUtils;
import org.junit.Test;

import java.nio.ByteBuffer;

public class CodeAttributeTest {

    @Test
    public void testCodeAttribute() throws Exception {
        ByteBuffer codeBuf = ClassFileAnalysisMain
                .readFile("/Users/wjy/MyProjects/asm-bytecode-project/build/classes/java/main/com/wujiuye/asmbytecode/book/vmstack/RecursionAlgorithmMain.class");
        ClassFile classFile = ClassFileAnalysiser.analysis(codeBuf);
        // 获取所有方法
        MethodInfo[] methodInfos = classFile.getMethods();
        for (MethodInfo methodInfo : methodInfos) {
            // 获取方法的所有属性
            AttributeInfo[] attributeInfos = methodInfo.getAttributes();
            if (attributeInfos == null || attributeInfos.length == 0) {
                continue;
            }
            System.out.println("方法：" + classFile.getConstant_pool()[methodInfo.getName_index().toInt() - 1]);
            for (AttributeInfo attributeInfo : attributeInfos) {
                // 获取属性的名称
                U2 name_index = attributeInfo.getAttribute_name_index();
                CONSTANT_Utf8_info name_info = (CONSTANT_Utf8_info) classFile.getConstant_pool()[name_index.toInt() - 1];
                String name = new String(name_info.getBytes());
                if (name.equalsIgnoreCase("Code")) {
                    // 属性二次解析
                    Code_attribute code = AttributeProcessingFactory.processingCode(attributeInfo);
                    System.out.println("操作数栈大小：" + code.getMax_stack().toInt());
                    System.out.println("局部变量表大小：" + code.getMax_locals().toInt());
                    System.out.println("字节码数组长度：" + code.getCode_length().toInt());
                    System.out.println("字节码：\n" + HexStringUtils.toHexString(code.getCode()));
                    System.out.println("\n");
                }
            }
        }
    }

}
