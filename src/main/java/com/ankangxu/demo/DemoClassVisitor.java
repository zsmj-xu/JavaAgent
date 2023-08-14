package com.ankangxu.demo;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class DemoClassVisitor extends ClassVisitor {

    protected DemoClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM9, classVisitor);
    }

    @Override
    public void visitEnd() {
        System.out.println("DemoVisitor end");
        super.visitEnd();
    }
}
