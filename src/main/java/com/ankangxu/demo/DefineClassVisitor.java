package com.ankangxu.demo;

import com.ankangxu.demo.util.TransformUtil;
import org.objectweb.asm.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefineClassVisitor extends ClassVisitor {

    private final String className;

    public DefineClassVisitor(String className, ClassVisitor cv) {
        super(Opcodes.ASM9, cv);
        this.className = className;
    }

    /**
     * 访问类
     * @param version class 版本号
     * @param access 类的访问标识 public
     * @param name 类名称 org/example/Main
     * @param signature 类签名（非泛型为NUll）
     * @param superName 类的父类
     * @param interfaces 类实现的接口
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        String line = String.format("ClassVisitor.visit(%d, %s, %s, %s, %s, %s);",
        version, TransformUtil.getAccess(access), name, signature, superName, Arrays.toString(interfaces));
//        System.out.println(line);
        super.visit(version, access, name, signature, superName, interfaces);
    }

    /**
     * 访问源码文件
     * @param source 编译该类的源文件的名称。可能为空
     * @param debug 用于计算类的源元素和编译元素之间的对应关系的附加调试信息。可能为空。
     */
    @Override
    public void visitSource(String source, String debug) {
        super.visitSource(source, debug);
    }


    /**
     *
     * @param access 字段的访问标志（请参阅Opcodes）。此参数还指示该字段是合成的还是已弃用的。
     * @param name 字段的名称。
     * @param descriptor 字段的描述符（参见Type）。 也就是字段类型
     * @param signature 该字段的签名。如果字段的类型不使用泛型类型，则可能为 null。
     * @param value 字段的初始值。 该参数仅用于静态字段
     * @return
     */
    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        String line = String.format("ClassVisitor.visitField(%s, %s, %s, %s, %s);",
                TransformUtil.getAccess(access), name, descriptor, signature, value);
//        System.out.println(line);

        return super.visitField(access, name, descriptor, signature, value);
    }



    /**
     * 访问类的方法 第一个访问的是默认构造方法
     * @param access 方法的访问标志（请参阅Opcodes）。此参数还指示该方法是合成的还是已弃用的。
     * @param name 方法的名称。
     * @param desc 这里是方法的描述符
     * @param signature 方法的签名。如果方法参数、返回类型和异常不使用泛型类型，则可能为 null。
     * @param exceptions 方法的异常类的内部名称（请参阅Type.getInternalName()）。可能为空。
     * @return 用于访问该方法的字节代码的对象，如果此类访问者对访问该方法的代码不感兴趣，则返回 null。
     */
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        String line = String.format("ClassVisitor.visitMethod(%s, %s, %s, %s, %s);",
                TransformUtil.getAccess(access), name, desc, signature, Arrays.toString(exceptions));
//        System.out.println(line);

        return new DefineMethodVisitor(mv,access, className,name, desc);
    }

    /**
     * 访问结束方法
     * 所有字段及其方法都访问完了
     */
    @Override
    public void visitEnd() {
        String line = String.format("ClassVisitor.visitEnd();");
//        System.out.println(line);
        super.visitEnd();
    }





}
