package com.ankangxu.demo;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class PreMainTraceAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("agentArgs : " + agentArgs);
        System.out.println("start");
        //添加转换类
        inst.addTransformer(new DefineTransformer(), true);
    }

    static class DefineTransformer implements ClassFileTransformer {

        /**
         *
         * @param loader
         * @param className
         * @param classBeingRedefined
         * @param protectionDomain
         * @param classfileBuffer
         * @return
         */
        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                                byte[] classfileBuffer){
            System.out.println("go transform");
//            // 创建Reader读取
            ClassReader classReader  = new ClassReader(classfileBuffer);
            ClassWriter classWriter  = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
            ClassVisitor classVisitor = new DefineClassVisitor(className, classWriter); //自定义的ClassVisitor
            //按照一定顺序执行classVisitor的visitorXXX方法
            classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
            System.out.println("premain load Class:" + className);
            return classWriter.toByteArray();
        }
    }
}
