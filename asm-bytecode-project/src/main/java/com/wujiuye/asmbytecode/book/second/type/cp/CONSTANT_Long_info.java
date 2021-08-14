package com.wujiuye.asmbytecode.book.second.type.cp;

import com.wujiuye.asmbytecode.book.second.type.CpInfo;
import com.wujiuye.asmbytecode.book.second.type.U1;
import com.wujiuye.asmbytecode.book.second.type.U4;

import java.nio.ByteBuffer;

public class CONSTANT_Long_info extends CpInfo {

    private U4 hight_bytes;
    private U4 low_bytes;

    public CONSTANT_Long_info(U1 tag) {
        super(tag);
    }

    @Override
    public void read(ByteBuffer codeBuf) throws Exception {
        hight_bytes = new U4(codeBuf.get(), codeBuf.get(), codeBuf.get(), codeBuf.get());
        low_bytes = new U4(codeBuf.get(), codeBuf.get(), codeBuf.get(), codeBuf.get());
    }

    public U4 getHight_bytes() {
        return hight_bytes;
    }

    public U4 getLow_bytes() {
        return low_bytes;
    }

    @Override
    public String toString() {
        return "CONSTANT_Long_info";
    }
}
