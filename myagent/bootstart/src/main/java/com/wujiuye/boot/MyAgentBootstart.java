package com.wujiuye.boot;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAgentBootstart {

    /**
     * 显示当前所有jvm进程
     *
     * @return
     */
    private static Map<Integer, String> showAllJavaProcess() {
        Map<Integer, String> pidMap = new HashMap<>();
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        int rows = 0;
        System.out.println("找到如下Java进程，请选择：");
        for (VirtualMachineDescriptor vmd : list) {
            pidMap.put(++rows, vmd.id());
            System.out.println("[" + rows + "] " + vmd.id() + "\t" + vmd.displayName().split(" ")[0]);
        }
        return pidMap;
    }

    /**
     * 读取选择
     *
     * @return
     * @throws IOException
     */
    private static String readString() throws IOException {
        char[] inputBuf = new char[1024];
        int leng = 0;
        while (true) {
            char ch = (char) System.in.read();
            if (ch == '\r') {
                break;
            }
            if (ch == '\n') {
                break;
            }
            inputBuf[leng++] = ch;
        }
        return new String(inputBuf, 0, leng);
    }

    /**
     * 将my-java-agent的jar包加载到目录进程
     *
     * @param pid jvm进程ID
     * @throws Exception
     */
    private static void attachPid(String pid) throws Exception {
        VirtualMachine vm;
        vm = VirtualMachine.attach(pid);
        System.out.println("attach pid：" + vm.id());
        try {
            vm.loadAgent("/Users/wjy/MyProjects/ASM动态改写字节码从入门到实战/myagent/my-java-agent/target/my-java-agent-1.0-jar-with-dependencies.jar", null);
        } finally {
            vm.detach();
        }
    }

    public static void main(String[] args) throws Exception {
        Map<Integer, String> pidMap = showAllJavaProcess();
        Integer inputId = Integer.parseInt(readString());
        // 获取选择的进程ID
        String targetPid = pidMap.get(inputId);
        attachPid(targetPid);
    }

}
