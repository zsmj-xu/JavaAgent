package com.ankangxu.demo;

import org.objectweb.asm.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;
import java.util.Arrays;

public class PreMainTraceAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        //添加转换类
        DefineTransformer defineTransformer = new DefineTransformer(inst);
//        defineTransformer.reTransform();

    }

    public static void main(String[] args) throws Exception {
        String demo = Type.getMethodDescriptor(DefineMethodVisitor.class.getDeclaredMethod("Demo", String.class, int.class, int.class));
        Type methodType = Type.getMethodType(demo);
        String descriptor = methodType.getDescriptor();
        Type[] argumentTypes = methodType.getArgumentTypes();
        Type returnType = methodType.getReturnType();

        System.out.println("Descriptor: " + descriptor);
        System.out.println("Argument Types:");
        for (Type t : argumentTypes) {
            System.out.println("    " + t.getClassName());
        }
        System.out.println("Return Type: " + returnType.getClassName());
    }
}
