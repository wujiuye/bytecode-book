package com.wujiuye.asmbytecode.book.sixth.cglib;

import com.wujiuye.asmbytecode.book.fifth.util.ByteCodeUtils;
import org.objectweb.asm.Type;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class MyEnhancer {

    private Class<?> superclass;
    private MyMethodInterceptor interceptor;

    public void setSuperclass(Class<?> superclass) {
        if (superclass.isInterface()) {
            throw new RuntimeException("父类不能是接口！");
        }
        if (superclass == Object.class) {
            throw new RuntimeException("不能代理Object类！");
        }
        if ((superclass.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
            throw new RuntimeException("final类不允许继承！");
        }
        this.superclass = superclass;
    }

    public void setCallback(MyMethodInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    public Object create() {
        if (superclass == null) {
            throw new RuntimeException("未设置父类！");
        }
        try {
            // 如果拦截器为空，无需创建代理类
            if (interceptor == null) {
                return superclass.newInstance();
            }
            String subclassName = Type.getInternalName(superclass) + "$Proxy";
            byte[] subclassByteCode = SubclassProxyFactory.createProxyClass(subclassName, superclass);
            Class<?> subclass = ByteCodeUtils.loadClass(subclassName, subclassByteCode);
            Constructor<?> constructor = subclass.getConstructor(MyMethodInterceptor.class);
            return constructor.newInstance(interceptor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
