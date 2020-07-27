package com.wujiuye.asmbytecode.book.second;

import com.wujiuye.asmbytecode.book.second.type.ClassFile;
import com.wujiuye.asmbytecode.book.second.type.U2;
import com.wujiuye.asmbytecode.book.second.util.ClassAccessFlagUtils;
import org.junit.Test;

import java.nio.ByteBuffer;

public class AccessFlagsHandlerTest {


    @Test
    public void testAccessFlagsHandlerHandler() throws Exception {
        ByteBuffer codeBuf = ClassFileAnalysisMain.readFile("/Users/wjy/MyProjects/asm-bytecode-project/build/classes/java/main/com/wujiuye/asmbytecode/book/vmstack/RecursionAlgorithmMain.class");
        ClassFile classFile = ClassFileAnalysiser.analysis(codeBuf);
        U2 accessFlags = classFile.getAccess_flags();
        System.out.println(ClassAccessFlagUtils.toClassAccessFlagsString(accessFlags));
    }

}
