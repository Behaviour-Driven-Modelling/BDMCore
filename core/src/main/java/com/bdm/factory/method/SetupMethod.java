package com.bdm.factory.method;

import com.bdm.AnnotationTypes;
import com.bdm.factory.annotation.IAnnotation;

import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;

public class SetupMethod implements IMethod{

    private IAnnotation _annotation;

    public SetupMethod(IAnnotation annotation) {
        this._annotation = annotation;
    }

    @Override
    public CtMethod Create(String TemporaryObjectName, String functionName, String[] parametersParam,
            AnnotationTypes annotationType, String annotationValue, CtClass declaringClass, Boolean pending)
            throws Exception {
        ClassFile classFile = declaringClass.getClassFile();
        ConstPool constPool = classFile.getConstPool();

        AnnotationsAttribute annotationsAttribute = _annotation.Create(constPool, annotationType, annotationValue);
        String body = String.format("{init();}");
        CtMethod newMethod = CtNewMethod.make(CtClass.voidType, "setup", null, null, body, declaringClass);
        newMethod.getMethodInfo().addAttribute(annotationsAttribute);
        newMethod.setModifiers(AccessFlag.setPublic(Modifier.STATIC));

        return newMethod;
    }
    
}
