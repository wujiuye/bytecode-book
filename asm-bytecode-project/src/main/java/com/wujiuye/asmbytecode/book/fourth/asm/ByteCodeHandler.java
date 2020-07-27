package com.wujiuye.asmbytecode.book.fourth.asm;

/**
 * 字节码处理器
 *
 * @author wujiuye
 * @version 1.0 on 2019/11/24 {描述：}
 */
public interface ByteCodeHandler {

    /**
     * 获取类名
     *
     * @return
     */
    String getClassName();

    /**
     * 获取类的字节码
     *
     * @return
     */
    byte[] getByteCode();

}
