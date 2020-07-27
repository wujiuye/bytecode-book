package com.wujiuye.asmbytecode.book.second;

import com.wujiuye.asmbytecode.book.second.type.ClassFile;
import com.wujiuye.asmbytecode.book.second.type.U2;
import com.wujiuye.asmbytecode.book.second.type.cp.CONSTANT_Class_info;
import com.wujiuye.asmbytecode.book.second.type.cp.CONSTANT_Utf8_info;
import org.junit.Test;

import java.nio.ByteBuffer;

public class ThisAndSuperHandlerTest {

    @Test
    public void testThisAndSuperHandlerHandler() throws Exception {
        ByteBuffer codeBuf = ClassFileAnalysisMain.readFile("/Users/wjy/MyProjects/asm-bytecode-project/build/classes/java/main/com/wujiuye/asmbytecode/book/vmstack/RecursionAlgorithmMain.class");
        ClassFile classFile = ClassFileAnalysiser.analysis(codeBuf);

        U2 this_class = classFile.getThis_class();
        CONSTANT_Class_info this_class_cpInfo = (CONSTANT_Class_info) classFile.getConstant_pool()[this_class.toInt() - 1];
        CONSTANT_Utf8_info this_class_name_index = (CONSTANT_Utf8_info) classFile.getConstant_pool()[this_class_cpInfo.getName_index().toInt()-1];
        System.out.println(this_class_name_index);

        U2 super_class = classFile.getSuper_class();
        CONSTANT_Class_info super_class_cpInfo = (CONSTANT_Class_info) classFile.getConstant_pool()[super_class.toInt() - 1];
        CONSTANT_Utf8_info supor_class_name_index = (CONSTANT_Utf8_info) classFile.getConstant_pool()[super_class_cpInfo.getName_index().toInt()-1];
        System.out.println(supor_class_name_index);
    }

}
