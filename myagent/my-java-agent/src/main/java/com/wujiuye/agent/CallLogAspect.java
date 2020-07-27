package com.wujiuye.agent;

import com.alibaba.fastjson.JSON;

public class CallLogAspect {

    public static void before(String className, String methodName,
                              String descriptor, Object[] params) {
        System.out.println("方法开始执行时间：" + System.currentTimeMillis());
        System.out.println("类名：" + className);
        System.out.println("方法名：" + methodName);
        System.out.println("方法描述符：" + descriptor);
        System.out.println("参数：" + JSON.toJSONString(params));
    }

    public static void error(String className, String methodName,
                                 String descriptor, Throwable throwable) {
        System.out.println("方法执行出现异常时间：" + System.currentTimeMillis());
        System.out.println("类名：" + className);
        System.out.println("方法名：" + methodName);
        System.out.println("方法描述符：" + descriptor);
        System.out.println("异常信息：" + throwable.getMessage());
    }

    public static void after(String className, String methodName,
                             String descriptor, Object returnValue) {
        System.out.println("方法执行完成时间：" + System.currentTimeMillis());
        System.out.println("类名：" + className);
        System.out.println("方法名：" + methodName);
        System.out.println("方法描述符：" + descriptor);
        System.out.println("返回值：" + JSON.toJSONString(returnValue));
    }

}
