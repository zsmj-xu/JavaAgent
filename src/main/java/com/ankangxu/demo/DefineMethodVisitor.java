package com.ankangxu.demo;

import com.ankangxu.demo.util.TransformUtil;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;


public class DefineMethodVisitor extends AdviceAdapter {

    private final String className;
    private final String methodName;
    /**
     * 方法参数标识
     */
    private final String desc;
    private final int isStatic;


    protected DefineMethodVisitor(MethodVisitor methodVisitor, int access, String className, String name, String descriptor) {
        super(Opcodes.ASM9, methodVisitor, access, name, descriptor);
        this.methodName = name;
        this.desc = descriptor;
        this.className = className;
        this.isStatic = (access & Opcodes.ACC_STRICT) | (access & Opcodes.ACC_INTERFACE);
    }

    @Override
    public void visitLdcInsn(Object value) {
        if ("test".equals(value))
            value = "Replace!";
        super.visitLdcInsn(value);
    }

    @Override
    public void visitCode() {
        Type methodType = Type.getMethodType(desc);
        Type[] argumentTypes = methodType.getArgumentTypes();
        for (Type t : argumentTypes) {
            int sort = t.getSort();
            int size = t.getSize();
            String descriptor = t.getDescriptor();

            int opcode = t.getOpcode(ILOAD);
            super.visitVarInsn(opcode, slotIndex);

            if (sort == Type.BOOLEAN) {
                printBoolean();
            }
            else if (sort == Type.CHAR) {
                printChar();
            }
            else if (sort == Type.BYTE || sort == Type.SHORT || sort == Type.INT) {
                printInt();
            }
            else if (sort == Type.FLOAT) {
                printFloat();
            }
            else if (sort == Type.LONG) {
                printLong();
            }
            else if (sort == Type.DOUBLE) {
                printDouble();
            }
            else if (sort == Type.OBJECT && "Ljava/lang/String;".equals(descriptor)) {
                printString();
            }
            else if (sort == Type.OBJECT) {
                printObject();
            }
            else {
                printMessage("No Support");
                if (size == 1) {
                    super.visitInsn(Opcodes.POP);
                }
                else {
                    super.visitInsn(Opcodes.POP2);
                }
            }
            slotIndex += size;
        }
    }



    /**
     * 方法调用执行
     */
    @Override
    protected void onMethodEnter() {
        if("add".equals(methodName)){
            System.out.println(desc);
            Type returnType = Type.getReturnType(this.desc);
            System.out.println(returnType.getClassName());
        }
        if(!"main".equals(methodName)){
            return;
        }

//        loadArgArray();
        push(methodName);
        push(2);
        push(2);
        Type type = Type.getType(DefineMethodVisitor.class);
        invokeStatic(type,getDemo());
    }


    /**
     * 方法结束执行
     * @param opcode one of {@link Opcodes#RETURN}, {@link Opcodes#IRETURN}, {@link Opcodes#FRETURN},
     *     {@link Opcodes#ARETURN}, {@link Opcodes#LRETURN}, {@link Opcodes#DRETURN} or {@link
     *     Opcodes#ATHROW}.
     */
    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
    }


    public static void Demo(String name,int a,int b){
        System.out.println(name + " : invoke Demo");
//        System.out.println(returnObject);
//        System.out.println(thisObject);
//        for (Object parameter : parameters) {
//            System.out.println(parameter);
//        }
        System.out.println(a);
        System.out.println(b);
    }


    public static Method getDemo(){
        try {
            return Method.getMethod(DefineMethodVisitor.class.getDeclaredMethod("Demo",String.class,int.class,int.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
