package com.wujiuye.asmbytecode.book.second.attribute;

import com.wujiuye.asmbytecode.book.second.type.AttributeInfo;
import com.wujiuye.asmbytecode.book.second.type.U2;
import com.wujiuye.asmbytecode.book.second.type.U4;

public class Code_attribute {

    private U2 attribute_name_index;
    private U4 attribute_length;
    private U2 max_stack;
    private U2 max_locals;
    private U4 code_length;
    private byte[] code;
    private U4 exception_table_length;

    public static class Exception {
        private U2 start_pc;
        private U2 end_pc;
        private U2 handler_pc;
        private U2 catch_type;

        public U2 getStart_pc() {
            return start_pc;
        }

        public void setStart_pc(U2 start_pc) {
            this.start_pc = start_pc;
        }

        public U2 getEnd_pc() {
            return end_pc;
        }

        public void setEnd_pc(U2 end_pc) {
            this.end_pc = end_pc;
        }

        public U2 getHandler_pc() {
            return handler_pc;
        }

        public void setHandler_pc(U2 handler_pc) {
            this.handler_pc = handler_pc;
        }

        public U2 getCatch_type() {
            return catch_type;
        }

        public void setCatch_type(U2 catch_type) {
            this.catch_type = catch_type;
        }
    }

    private Exception[] exception_table;
    private U2 attributes_count;
    private AttributeInfo[] attributes;


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

    public U2 getMax_stack() {
        return max_stack;
    }

    public void setMax_stack(U2 max_stack) {
        this.max_stack = max_stack;
    }

    public U2 getMax_locals() {
        return max_locals;
    }

    public void setMax_locals(U2 max_locals) {
        this.max_locals = max_locals;
    }

    public U4 getCode_length() {
        return code_length;
    }

    public void setCode_length(U4 code_length) {
        this.code_length = code_length;
    }

    public byte[] getCode() {
        return code;
    }

    public void setCode(byte[] code) {
        this.code = code;
    }

    public U4 getException_table_length() {
        return exception_table_length;
    }

    public void setException_table_length(U4 exception_table_length) {
        this.exception_table_length = exception_table_length;
    }

    public Exception[] getException_table() {
        return exception_table;
    }

    public void setException_table(Exception[] exception_table) {
        this.exception_table = exception_table;
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
