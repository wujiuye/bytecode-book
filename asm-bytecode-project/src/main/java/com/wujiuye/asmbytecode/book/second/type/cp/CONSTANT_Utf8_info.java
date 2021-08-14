package com.wujiuye.asmbytecode.book.second.type.cp;

import com.wujiuye.asmbytecode.book.second.type.CpInfo;
import com.wujiuye.asmbytecode.book.second.type.U1;
import com.wujiuye.asmbytecode.book.second.type.U2;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class CONSTANT_Utf8_info extends CpInfo {

    private U2 length;
    private byte[] bytes;

    public CONSTANT_Utf8_info(U1 tag) {
        super(tag);
    }

    @Override
    public void read(ByteBuffer codeBuf) throws Exception {
        length = new U2(codeBuf.get(), codeBuf.get());
        bytes = new byte[length.toInt()];
        codeBuf.get(bytes, 0, length.toInt());
    }

    public U2 getLength() {
        return length;
    }

    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public String toString() {
        return super.toString() +
                ",length=" + length.toInt() +
                ",str=" + new String(bytes, StandardCharsets.UTF_8);
    }

}
