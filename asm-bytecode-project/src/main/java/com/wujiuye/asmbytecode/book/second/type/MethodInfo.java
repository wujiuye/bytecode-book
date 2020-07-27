package com.wujiuye.asmbytecode.book.second.type;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MethodInfo {

    private U2 access_flags;
    private U2 name_index;
    private U2 descriptor_index;
    private U2 attributes_count;
    private AttributeInfo[] attributes;

}