package com.wujiuye.asmbytecode.book.fifth.visit;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FieldWriter implements FieldVisitor {

    private String access;
    private String name;
    private String descriptor;
    private List<String> annotations;

    public FieldWriter(String access, String name, String descriptor) {
        this.access = access;
        this.name = name;
        this.descriptor = descriptor;
        this.annotations = new ArrayList<>();
    }

    @Override
    public void visitAnnotation(String annotation, boolean runtime) {
        this.annotations.add("注解：" + annotation + "，" + runtime);
    }

}
