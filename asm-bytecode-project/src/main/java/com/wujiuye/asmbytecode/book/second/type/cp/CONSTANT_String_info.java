package com.wujiuye.asmbytecode.book.second.type.cp;

import com.wujiuye.asmbytecode.book.second.type.CpInfo;
import com.wujiuye.asmbytecode.book.second.type.U1;
import com.wujiuye.asmbytecode.book.second.type.U2;
import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
public class CONSTANT_String_info extends CpInfo {

    private U2 string_index;

    public CONSTANT_String_info(U1 tag) {
        super(tag);
    }

    @Override
    public void read(ByteBuffer codeBuf) throws Exception {
        string_index = new U2(codeBuf.get(), codeBuf.get());
    }
    @Override
    public String toString() {
        return "CONSTANT_String_info";
    }
}
