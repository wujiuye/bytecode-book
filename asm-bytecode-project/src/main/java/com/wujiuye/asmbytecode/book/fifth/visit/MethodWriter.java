package com.wujiuye.asmbytecode.book.fifth.visit;

import lombok.Getter;

@Getter
public class MethodWriter implements MethodVisitor {

    private String access;
    private String name;
    private String descriptor;
    private int maxStackSize;
    private int maxLocalSize;

    public MethodWriter(String access, String name, String descriptor) {
        this.access = access;
        this.name = name;
        this.descriptor = descriptor;
    }

    @Override
    public void visitMaxs(int maxStackSize, int maxLocalSize) {
        this.maxLocalSize = maxLocalSize;
        this.maxStackSize = maxStackSize;
    }

}
