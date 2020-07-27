package com.wujiuye.asmbytecode.book.second.attribute;

import com.wujiuye.asmbytecode.book.second.type.AttributeInfo;
import com.wujiuye.asmbytecode.book.second.type.U2;
import com.wujiuye.asmbytecode.book.second.type.U4;

import java.nio.ByteBuffer;

/**
 * 属性加工厂，即对属性进行二次解析
 */
public class AttributeProcessingFactory {

    public static ConstantValue_attribute processingConstantValue(AttributeInfo attributeInfo) {
        ConstantValue_attribute attribute = new ConstantValue_attribute();
        attribute.setAttribute_name_index(attributeInfo.getAttribute_name_index());
        attribute.setAttribute_length(attributeInfo.getAttribute_length());
        attribute.setConstantvalue_index(new U2(attributeInfo.getInfo()[0], attributeInfo.getInfo()[1]));
        return attribute;
    }

    public static Code_attribute processingCode(AttributeInfo attributeInfo) {
        Code_attribute code = new Code_attribute();
        ByteBuffer body = ByteBuffer.wrap(attributeInfo.getInfo());
        // 操作数栈大小
        code.setMax_stack(new U2(body.get(),body.get()));
        // 局部变量表大小
        code.setMax_locals(new U2(body.get(),body.get()));
        // 字节码数组长度
        code.setCode_length(new U4(body.get(),body.get(),body.get(),body.get()));
        // 解析获取字节码
        byte[] byteCode = new byte[code.getCode_length().toInt()];
        body.get(byteCode,0,byteCode.length);
        code.setCode(byteCode);
        // 其它暂时不做解析
        body.clear();
        return code;
    }

}
