package com.wujiuye.asmbytecode.book.second.type.cp;

import com.wujiuye.asmbytecode.book.second.type.CpInfo;
import com.wujiuye.asmbytecode.book.second.type.U1;
import com.wujiuye.asmbytecode.book.second.type.U2;

import java.nio.ByteBuffer;

public class CONSTANT_NameAndType_info extends CpInfo {

    private U2 name_index;
    private U2 descriptor_index;

    public CONSTANT_NameAndType_info(U1 tag) {
        super(tag);
    }

    @Override
    public void read(ByteBuffer codeBuf) throws Exception {
        name_index = new U2(codeBuf.get(), codeBuf.get());
        descriptor_index = new U2(codeBuf.get(), codeBuf.get());
    }

    public U2 getName_index() {
        return name_index;
    }

    public U2 getDescriptor_index() {
        return descriptor_index;
    }

    @Override
    public String toString() {
        return "CONSTANT_NameAndType_info";
    }

}
