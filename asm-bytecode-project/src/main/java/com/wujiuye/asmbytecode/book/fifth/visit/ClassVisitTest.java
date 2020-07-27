package com.wujiuye.asmbytecode.book.fifth.visit;

public class ClassVisitTest {

    public static void main(String[] args) {
        ClassWriter classWriter = new ClassWriter();
        classWriter.visit(52, "public", "com.wujiuye.User");
        FieldVisitor fieldVisitor = classWriter
                .visitField("private", "name", "Ljava/lang/String;");
        fieldVisitor.visitAnnotation("@Getter", true);
        MethodVisitor methodVisitor = classWriter
                .visitMethod("public", "getName", "(Ljava/lang/String)V");
        methodVisitor.visitMaxs(1, 1);
        classWriter.showClass();
    }

}
