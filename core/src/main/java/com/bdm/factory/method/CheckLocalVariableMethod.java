package com.bdm.factory.method;

import com.bdm.AnnotationTypes;

import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

public class CheckLocalVariableMethod implements IMethod{

    @Override
    public CtMethod Create(String TemporaryObjectName, String functionName, String[] parametersParam,
            AnnotationTypes annotationType, String annotationValue, CtClass declaringClass, Boolean pending)
            throws Exception {

        String bodyHelper = String.format("{try { run(\"%s\"); } catch (Exception e) {if(e.getMessage().contains(\"Error 3182:\")) {create(\"%s\",\"%s\");}}}",TemporaryObjectName,TemporaryObjectName,"new "+functionName+"()");
        CtMethod newMethod = CtNewMethod.make(CtClass.voidType, "checkLocalVariable", null, null, bodyHelper, declaringClass);
        return newMethod;
    }
    
}
