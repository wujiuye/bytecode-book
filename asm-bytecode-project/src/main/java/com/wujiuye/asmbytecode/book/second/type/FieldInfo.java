package com.wujiuye.asmbytecode.book.second.type;

public class FieldInfo {

    private U2 access_flags;
    private U2 name_index;
    private U2 descriptor_index;
    private U2 attributes_count;
    private AttributeInfo[] attributes;

    public U2 getAccess_flags() {
        return access_flags;
    }

    public void setAccess_flags(U2 access_flags) {
        this.access_flags = access_flags;
    }

    public U2 getName_index() {
        return name_index;
    }

    public void setName_index(U2 name_index) {
        this.name_index = name_index;
    }

    public U2 getDescriptor_index() {
        return descriptor_index;
    }

    public void setDescriptor_index(U2 descriptor_index) {
        this.descriptor_index = descriptor_index;
    }

    public U2 getAttributes_count() {
        return attributes_count;
    }

    public void setAttributes_count(U2 attributes_count) {
        this.attributes_count = attributes_count;
    }

    public AttributeInfo[] getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributeInfo[] attributes) {
        this.attributes = attributes;
    }
}
