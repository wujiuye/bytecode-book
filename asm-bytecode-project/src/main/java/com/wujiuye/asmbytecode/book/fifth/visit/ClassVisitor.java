package com.wujiuye.asmbytecode.book.fifth.visit;

public interface ClassVisitor {

    // 设置class文件结构的版本号、类的访问标志、类名
    void visit(int version, String access, String className);

    // 为类添加一个字段
    FieldVisitor visitField(String access, String name, String descriptor);

    // 为类添加一个方法
    MethodVisitor visitMethod(String access, String name, String descriptor);

}
