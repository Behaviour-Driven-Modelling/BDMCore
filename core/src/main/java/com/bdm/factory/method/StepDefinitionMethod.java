package com.bdm.factory.method;

import com.bdm.AnnotationTypes;
import com.bdm.VDMUtility;
import com.bdm.factory.annotation.IAnnotation;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;

public class StepDefinitionMethod implements IMethod{

    private IAnnotation _annotation;

    public StepDefinitionMethod(IAnnotation annotation) {
        this._annotation = annotation;
    }

    @Override
    public CtMethod Create(String TemporaryObjectName, String functionName, String[] parametersParam, AnnotationTypes annotationType, String annotationValue, CtClass declaringClass, Boolean pending) throws Exception
    {
        ClassFile classFile = declaringClass.getClassFile();
        ConstPool constPool = classFile.getConstPool();
        ClassPool classPool = ClassPool.getDefault();

        AnnotationsAttribute annotationsAttribute = _annotation.Create(constPool, annotationType, annotationValue);
        CtClass[] parameters = new CtClass[parametersParam.length];
        int idx = 0;
        String body = "";
        
        if(pending) {
            body = "throw new io.cucumber.java.PendingException();";
            for (String string : parametersParam) {
                CtClass wrapperClass = classPool.get(VDMUtility.DataTypeJAVAToPackage.get(string));
                parameters[idx] = wrapperClass;
                idx++;
            }
        } else {
            body = String.format("{checkLocalVariable();run(\"%s.%s(\"",TemporaryObjectName,functionName); 
            for (String string : parametersParam) {
                CtClass wrapperClass = classPool.get(VDMUtility.DataTypeJAVAToPackage.get(string));
                body = body + (idx > 0 ? "\",\"" : "") + VDMUtility.ParamHelper(string, idx);
                parameters[idx] = wrapperClass;
                idx++;
            }
            body = body +"\") \");}";
        }
        CtMethod newmethod = CtNewMethod.make(CtClass.voidType, functionName, parameters, null, body, declaringClass);
        newmethod.getMethodInfo().addAttribute(annotationsAttribute);
        return newmethod;
    }
    
}
