package com.wujiuye.asmbytecode.book.second.type.cp;

import com.wujiuye.asmbytecode.book.second.type.CpInfo;
import com.wujiuye.asmbytecode.book.second.type.U1;
import com.wujiuye.asmbytecode.book.second.type.U2;

import java.nio.ByteBuffer;

public class CONSTANT_MethodHandle_info extends CpInfo {

    private U1 reference_kind;
    private U2 reference_index;

    public CONSTANT_MethodHandle_info(U1 tag) {
        super(tag);
    }

    @Override
    public void read(ByteBuffer codeBuf) throws Exception {
        reference_kind = new U1(codeBuf.get());
        reference_index = new U2(codeBuf.get(), codeBuf.get());
    }

    public U1 getReference_kind() {
        return reference_kind;
    }

    public U2 getReference_index() {
        return reference_index;
    }

    @Override
    public String toString() {
        return "CONSTANT_MethodHandle_info";
    }
}
