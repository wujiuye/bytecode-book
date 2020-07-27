package com.wujiuye.asmbytecode.book.second.handler;

import com.wujiuye.asmbytecode.book.second.type.AttributeInfo;
import com.wujiuye.asmbytecode.book.second.type.ClassFile;
import com.wujiuye.asmbytecode.book.second.type.U2;
import com.wujiuye.asmbytecode.book.second.type.U4;

import java.nio.ByteBuffer;

public class AttributesHandler implements BaseByteCodeHandler {

    @Override
    public int order() {
        return 8;
    }

    @Override
    public void read(ByteBuffer codeBuf, ClassFile classFile) throws Exception {
        classFile.setAttributes_count(new U2(codeBuf.get(), codeBuf.get()));
        // 获取属性总数
        int len = classFile.getAttributes_count().toInt();
        if (len == 0) {
            return;
        }
        // 创建属性表
        AttributeInfo[] attributeInfos = new AttributeInfo[len];
        classFile.setAttributes(attributeInfos);
        for (int i = 0; i < len; i++) {
            // 创建属性
            AttributeInfo attributeInfo = new AttributeInfo();
            attributeInfos[i] = attributeInfo;
            // 解析属性
            attributeInfo.setAttribute_name_index(new U2(codeBuf.get(), codeBuf.get()));
            attributeInfo.setAttribute_length(new U4(codeBuf.get(), codeBuf.get(), codeBuf.get(), codeBuf.get()));
            int attr_len = attributeInfo.getAttribute_length().toInt();
            if (attr_len == 0) {
                continue;
            }
            // 解析属性的info项
            byte[] bytes = new byte[attr_len];
            attributeInfo.setInfo(bytes);
            codeBuf.get(bytes, 0, bytes.length);
        }
    }

}
