package com.ankangxu.demo.util;

import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

public class TransformUtil {

    /**
     * 转换访问标识符
     * @param access 访问标识符 具体对应关系看Opcodes
     * @return
     */
    public static String getAccess(int access) {
        List<String> list = new ArrayList<>();
        if ((access & Opcodes.ACC_PUBLIC) != 0) {
            list.add("ACC_PUBLIC");
        }
        else if ((access & Opcodes.ACC_PROTECTED) != 0) {
            list.add("ACC_PROTECTED");
        }
        else if ((access & Opcodes.ACC_PRIVATE) != 0) {
            list.add("ACC_PRIVATE");
        }
        return list.toString();
    }
}
