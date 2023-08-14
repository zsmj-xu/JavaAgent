package com.ankangxu.demo;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;
import java.util.Arrays;

public class DefineTransformer implements ClassFileTransformer {

    private final Instrumentation instrumentation;


    public DefineTransformer(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
        this.instrumentation.addTransformer(this, true);
    }

    /**
     *
     * @param loader                该类是那个类加载器加载的,如果是 Bootstrap ClassLoader 则为null
     * @param className             类名 具体格式如 <code>"java/util/List"</code>.
     * @param classBeingRedefined   如果是重新定义或者重新转换触发的,就为正在定义或者转换的类,如果为类加载就为null
     * @param protectionDomain      正在定义或重新定义的类的保护域
     * @param classfileBuffer       class文件内容
     * @return
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer){
//        System.out.println("premain load Class:" + className);
        if (!className.contains("example")) {
            return classfileBuffer;
        }
        // 创建Reader读取
        ClassReader classReader  = new ClassReader(classfileBuffer);
        ClassWriter classWriter  = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
        ClassVisitor classVisitor = new DefineClassVisitor(className, classWriter); //自定义的ClassVisitor
        //按照一定顺序执行classVisitor的visitorXXX方法
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);

        return classWriter.toByteArray();
    }

    /**
     * hook已经加载的类，或者是回滚已经加载的类
     */
    public void reTransform() {
        Instrumentation instrumentation = this.instrumentation;
        // 获取所有已经加载的class
        Class<?>[] loadedClasses = instrumentation.getAllLoadedClasses();
        for (Class<?> clazz : loadedClasses) {
            // 判断class能不能把修改, 不处理java.lang.invoke.LambdaForm类
            if (instrumentation.isModifiableClass(clazz) && !clazz.getName().startsWith("java.lang.invoke.LambdaForm")) {
                try {
                    instrumentation.retransformClasses(clazz);
                } catch (UnmodifiableClassException e) {
                    System.out.println(Arrays.toString(e.getStackTrace()));
                }
            }
        }
    }
}
