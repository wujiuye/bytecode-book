package com.wujiuye.asmbytecode.book.second.type;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttributeInfo {
    private U2 attribute_name_index;
    private U4 attribute_length;
    private byte[] info;
}
