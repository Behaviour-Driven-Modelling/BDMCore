package com.bdm.factory.method;

import com.bdm.AnnotationTypes;

import javassist.CtClass;
import javassist.CtMethod;

public interface IMethod {
    CtMethod Create(String TemporaryObjectName, String functionName, String[] parametersParam, AnnotationTypes annotationType, String annotationValue, CtClass declaringClass, Boolean pending) throws Exception;
}
