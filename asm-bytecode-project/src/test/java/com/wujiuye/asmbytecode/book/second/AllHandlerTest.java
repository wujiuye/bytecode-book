package com.wujiuye.asmbytecode.book.second;

import com.wujiuye.asmbytecode.book.second.type.ClassFile;
import org.junit.Test;

import java.nio.ByteBuffer;

public class AllHandlerTest {

    @Test
    public void test() throws Exception {
        ByteBuffer codeBuf = ClassFileAnalysisMain.readFile("/Users/wjy/MyProjects/asm-bytecode-project/build/classes/java/main/com/wujiuye/asmbytecode/book/vmstack/RecursionAlgorithmMain.class");
        ClassFile classFile = ClassFileAnalysiser.analysis(codeBuf);
    }

}
