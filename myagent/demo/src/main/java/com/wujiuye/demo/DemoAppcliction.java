package com.wujiuye.demo;


import java.util.Map;

/**
 * -javaagent:/Users/wjy/MyProjects/ASM动态改写字节码从入门到实战/myagent/my-java-agent/target/my-java-agent-1.0-jar-with-dependencies.jar
 */
public class DemoAppcliction {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("main function runing...");
        UserService userService = new UserService();
        while (!Thread.interrupted()) {
            Map<String, Object> user = userService.queryUser("wujiuye", 25);
            System.out.println(user);
            Thread.sleep(10000);
        }
    }

}
