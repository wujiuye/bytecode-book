package com.wujiuye.asmbytecode.book.second.type.cp;

import java.nio.ByteBuffer;

public interface ConstantInfoHandler {

    /**
     * 读取
     *
     * @param codeBuf
     */
    void read(ByteBuffer codeBuf) throws Exception;

}
