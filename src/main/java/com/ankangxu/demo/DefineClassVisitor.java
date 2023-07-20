package com.ankangxu.demo;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefineClassVisitor extends ClassVisitor implements Opcodes {

    private final String className;

    public DefineClassVisitor(String className, ClassVisitor cv) {
        super(Opcodes.ASM9, cv);
        this.className = className;
    }

    /**
     *
     * @param version
     * @param access
     * @param name
     * @param signature
     * @param superName
     * @param interfaces
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
//        String line = String.format("ClassVisitor.visit(%d, %s, %s, %s, %s, %s);",
//                version, getAccess(access), name, signature, superName, Arrays.toString(interfaces));
//        System.out.println(line);
        super.visit(version, access, name, signature, superName, interfaces);
    }

    /**
     *
     * @param access
     * @param name
     * @param descriptor
     * @param signature
     * @param value
     * @return
     */
    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
//        String line = String.format("ClassVisitor.visitField(%s, %s, %s, %s, %s);",
//                getAccess(access), name, descriptor, signature, value);
//        System.out.println(line);

        return super.visitField(access, name, descriptor, signature, value);
    }

    /**
     *
     * @param access
     * @param name
     * @param desc
     * @param signature
     * @param exceptions
     * @return
     */
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, String[] exceptions) {
        String line = String.format("ClassVisitor.visitMethod(%s, %s, %s, %s, %s);",
                getAccess(access), name, desc, signature, Arrays.toString(exceptions));
        System.out.println(line);

        return super.visitMethod(access, name, desc, signature, exceptions);
    }

    /**
     *
     */
    @Override
    public void visitEnd() {
//        String line = String.format("ClassVisitor.visitEnd();");
//        System.out.println(line);
        super.visitEnd();
    }

    private String getAccess(int access) {
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
