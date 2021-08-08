package com.iflytek.msc;

import jdk.internal.org.objectweb.asm.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Description: ...
 * @Author: Robin-Li
 * @DateTime: 2021-08-08 15:57
 */
public class MSCRecreate {

    public static void main(String[] args) throws IOException {

        System.out.println("ddddddd");

        ClassReader cr = new ClassReader(MSC.class.getName());
        int i = 0;

        ClassWriter cw = new ClassWriter(0);
        MyClassVisitor cv = new MyClassVisitor(cw);
        cr.accept(cv, i);

        // output
        String projectPath = new File("").getAbsolutePath();
        String filePath = projectPath + File.separator + "out";
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        FileOutputStream out = new FileOutputStream(filePath + File.separator + "MSC.class");
        out.write(cw.toByteArray());
        out.close();
    }

    static class MyClassVisitor extends ClassVisitor {

        public MyClassVisitor(ClassVisitor var2) {
            super(327680, var2);
        }

        @Override
        public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
            if("loadLibrary".equals(s)){
                MethodVisitor method = super.visitMethod(i, s, s1, s2, strings);
                MyMethodVsitor mymv = new MyMethodVsitor(method);
                return mymv;
            }
            return super.visitMethod(i, s, s1, s2, strings);
        }
    }


    static class MyMethodVsitor extends MethodVisitor {

        public MyMethodVsitor(MethodVisitor methodVisitor) {
            super(327680, methodVisitor);
        }

//        @Override
//        public void visitTryCatchBlock(Label label, Label label1, Label label2, String s) {
//            super.visitTryCatchBlock(label, label1, label2, s);
//        }
//
//        @Override
//        public void visitFieldInsn(int i, String s, String s1, String s2) {
//            super.visitFieldInsn(i, s, s1, s2);
//        }
//
//        @Override
//        public void visitParameter(String s, int i) {
//            super.visitParameter(s, i);
//        }
//
        @Override
        public void visitAttribute(Attribute attribute) {
            super.visitAttribute(attribute);
        }

        @Override
        public void visitFrame(int i, int i1, Object[] objects, int i2, Object[] objects1) {
            super.visitFrame(i, i1, objects, i2, objects1);
        }

        @Override
        public void visitMethodInsn(int i, String s, String s1, String s2, boolean b) {
            super.visitMethodInsn(i, s, s1, s2, b);
        }

        @Override
        public void visitLocalVariable(String s, String s1, String s2, Label label, Label label1, int i) {
            super.visitLocalVariable(s, s1, s2, label, label1, i);
        }
//
        @Override
        public void visitLineNumber(int i, Label label) {
            super.visitLineNumber(i, label);
        }
//
//        @Override
//        public void visitLabel(Label label) {
//            super.visitLabel(label);
//        }
//
//        @Override
//        public void visitMultiANewArrayInsn(String s, int i) {
//            super.visitMultiANewArrayInsn(s, i);
//        }
//
//        @Override
//        public void visitTableSwitchInsn(int i, int i1, Label label, Label... labels) {
//            super.visitTableSwitchInsn(i, i1, label, labels);
//        }

        @Override
        public void visitLdcInsn(Object o) {
            if(o.toString().equals("user.dir")){
                super.visitLdcInsn("my.speech.dir");
                return;
            }
            super.visitLdcInsn(o);
        }
    }

}
