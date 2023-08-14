# JavaAgent

premain方法是 采用--agent方式插入的入口方法

agentmain方法是通过attach方式插入的入口方法

主要在于instrumentation.addTransformer()方法

添加转换器 这里使用的ASM来修改字节码

# ASM转换

ASM提供ClassVisitor 这个抽象类来访问class文件 这里的class代表class文件,一个class文件中可能存在多个
