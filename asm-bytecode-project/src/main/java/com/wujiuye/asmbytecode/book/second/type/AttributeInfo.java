package com.wujiuye.asmbytecode.book.second.type;

public class AttributeInfo {

    private U2 attribute_name_index;
    private U4 attribute_length;
    private byte[] info;

    public U2 getAttribute_name_index() {
        return attribute_name_index;
    }

    public void setAttribute_name_index(U2 attribute_name_index) {
        this.attribute_name_index = attribute_name_index;
    }

    public U4 getAttribute_length() {
        return attribute_length;
    }

    public void setAttribute_length(U4 attribute_length) {
        this.attribute_length = attribute_length;
    }

    public byte[] getInfo() {
        return info;
    }

    public void setInfo(byte[] info) {
        this.info = info;
    }

}
