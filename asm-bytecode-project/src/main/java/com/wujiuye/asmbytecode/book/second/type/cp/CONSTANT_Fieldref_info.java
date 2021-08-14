package com.wujiuye.asmbytecode.book.second.type.cp;

import com.wujiuye.asmbytecode.book.second.type.CpInfo;
import com.wujiuye.asmbytecode.book.second.type.U1;
import com.wujiuye.asmbytecode.book.second.type.U2;

import java.nio.ByteBuffer;

public class CONSTANT_Fieldref_info extends CpInfo {

    private U2 class_index;
    private U2 name_and_type_index;

    public CONSTANT_Fieldref_info(U1 tag) {
        super(tag);
    }

    @Override
    public void read(ByteBuffer codeBuf) throws Exception {
        class_index = new U2(codeBuf.get(), codeBuf.get());
        name_and_type_index = new U2(codeBuf.get(), codeBuf.get());
    }

    public U2 getClass_index() {
        return class_index;
    }

    public U2 getName_and_type_index() {
        return name_and_type_index;
    }

    @Override
    public String toString() {
        return "CONSTANT_Fieldref_info";
    }

}
