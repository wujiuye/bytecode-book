package com.wujiuye.asmbytecode.book.second;

import com.wujiuye.asmbytecode.book.second.type.ClassFile;
import com.wujiuye.asmbytecode.book.second.type.CpInfo;
import org.junit.Test;

import java.nio.ByteBuffer;

public class ConstantPoolHandlerTest {

    @Test
    public void testConstantPoolHandler() throws Exception {
        ByteBuffer codeBuf = ClassFileAnalysisMain.readFile("/Users/wjy/MyProjects/asm-bytecode-project/build/classes/java/main/com/wujiuye/asmbytecode/book/vmstack/RecursionAlgorithmMain.class");
        ClassFile classFile = ClassFileAnalysiser.analysis(codeBuf);
        int cp_info_count = classFile.getConstant_pool_count().toInt();
        System.out.println("常量池中常量项总数：" + cp_info_count);
        CpInfo[] cpInfo = classFile.getConstant_pool();
        for (CpInfo cp : cpInfo) {
            System.out.println(cp.toString());
        }
    }

}
