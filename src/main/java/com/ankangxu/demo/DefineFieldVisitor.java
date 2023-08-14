package com.ankangxu.demo;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

public class DefineFieldVisitor extends FieldVisitor {

    protected DefineFieldVisitor(FieldVisitor fieldVisitor) {
        super(Opcodes.ASM9, fieldVisitor);
    }




}
