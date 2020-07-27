package com.wujiuye.asmbytecode.book.second.handler;

import com.wujiuye.asmbytecode.book.second.type.ClassFile;
import com.wujiuye.asmbytecode.book.second.type.U2;

import java.nio.ByteBuffer;

public class InterfacesHandler implements BaseByteCodeHandler {

    @Override
    public int order() {
        return 5;
    }

    @Override
    public void read(ByteBuffer codeBuf, ClassFile classFile) throws Exception {
        classFile.setInterfaces_count(new U2(codeBuf.get(), codeBuf.get()));
        int interfaces_count = classFile.getInterfaces_count().toInt();
        if (interfaces_count > 0) {
            U2[] interfaces = new U2[interfaces_count];
            classFile.setInterfaces(interfaces);
            for (int i = 0; i < interfaces_count; i++) {
                interfaces[i] = new U2(codeBuf.get(), codeBuf.get());
            }
        }
    }

}
