package com.wujiuye.agent.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ByteCodeUtils {

    /**
     * 将字节码转为class文件
     *
     * @throws IOException
     */
    public static void savaToFile(String className, byte[] byteCode) throws IOException {
        File file = new File("/tmp/" + className + ".class");
        if ((!file.exists() || file.delete()) && file.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(byteCode);
            }
        }
    }

    /**
     * 根据方法描述符获取方法参数
     *
     * @param methodDescriptor 方法描述符
     * @return
     */
    public static String[] getParamDescriptors(String methodDescriptor) {
        List<String> paramDescriptors = new ArrayList<>();
        Matcher matcher = Pattern.compile("(L.*?;|\\[{0,2}L.*?;|[ZCBSIFJD]|\\[{0,2}[ZCBSIFJD]{1})")
                .matcher(methodDescriptor.substring(0, methodDescriptor.lastIndexOf(')') + 1));
        while (matcher.find()) {
            paramDescriptors.add(matcher.group(1));
        }
        if (paramDescriptors.size() == 0) {
            return null;
        }
        return paramDescriptors.toArray(new String[0]);
    }

}
