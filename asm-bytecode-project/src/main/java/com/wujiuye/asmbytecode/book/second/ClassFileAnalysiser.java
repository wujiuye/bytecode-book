package com.wujiuye.asmbytecode.book.second;

import com.wujiuye.asmbytecode.book.second.handler.*;
import com.wujiuye.asmbytecode.book.second.type.ClassFile;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClassFileAnalysiser {

    private final static List<BaseByteCodeHandler> handlers = new ArrayList<>();

    static {
        // 添加各项的解析器
        handlers.add(new MagicHandler());
        handlers.add(new VersionHandler());
        handlers.add(new ConstantPoolHandler());
        handlers.add(new AccessFlagsHandler());
        handlers.add(new ThisAndSuperClassHandler());
        handlers.add(new InterfacesHandler());
        handlers.add(new FieldHandler());
        handlers.add(new MethodHandler());
        handlers.add(new AttributesHandler());
        // 解析器排序，要按顺序调用
        handlers.sort((Comparator.comparingInt(BaseByteCodeHandler::order)));
    }

    public static ClassFile analysis(ByteBuffer codeBuf) throws Exception {
        codeBuf.position(0); // 重置ByteBuffer的读指针，从头开始
        ClassFile classFile = new ClassFile();
        // 遍历解析器，调用每个解析器的解析方法 
        for (BaseByteCodeHandler handler : handlers) {
            handler.read(codeBuf, classFile);
        }
        System.out.println("class文件结构解析完成，解析是否正常(剩余未解析的字节数)：" + codeBuf.remaining());
        return classFile;
    }

}
