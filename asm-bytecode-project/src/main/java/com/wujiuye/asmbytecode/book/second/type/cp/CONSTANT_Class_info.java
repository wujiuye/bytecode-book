package com.wujiuye.asmbytecode.book.second.type.cp;

import com.wujiuye.asmbytecode.book.second.type.CpInfo;
import com.wujiuye.asmbytecode.book.second.type.U1;
import com.wujiuye.asmbytecode.book.second.type.U2;

import java.nio.ByteBuffer;

public class CONSTANT_Class_info extends CpInfo {

    private U2 name_index;

    public CONSTANT_Class_info(U1 tag) {
        super(tag);
    }

    @Override
    public void read(ByteBuffer codeBuf) throws Exception {
        this.name_index = new U2(codeBuf.get(), codeBuf.get());
    }

    public U2 getName_index() {
        return name_index;
    }

    @Override
    public String toString() {
        return "CONSTANT_Class_info{" +
                "name_index=" + name_index.toInt() +
                '}';
    }

}
