package com.wujiuye.asmbytecode.book.fifth.visit;

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

    public String getAccess() {
        return access;
    }

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public int getMaxLocalSize() {
        return maxLocalSize;
    }

}
