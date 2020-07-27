package com.wujiuye.asmbytecode.book.fifth.util;

/**
 * 字节码处理器
 *
 * @author wujiuye
 * @version 1.0 on 2019/11/24 {描述：}
 */
public interface ByteCodeHolder {

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
