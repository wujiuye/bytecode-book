package com.wujiuye.asmbytecode.book.second.handler;

import com.wujiuye.asmbytecode.book.second.type.ClassFile;

import java.nio.ByteBuffer;

public interface BaseByteCodeHandler {

    /**
     * 排序
     *
     * @return
     */
    int order();

    /**
     * 读取
     *
     * @param codeBuf
     * @param classFile
     */
    void read(ByteBuffer codeBuf, ClassFile classFile) throws Exception;

}
