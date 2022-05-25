/*******************************************************************************
 *
 *	Copyright (c) 2022 Malthe Dalgaard Jensen & Kristoffer Stampe Villadsen.
 *
 *	Author: Malthe Dalgaard Jensen & Kristoffer Stampe Villadsen
 *
 *	This file is part of BDMCore.
 *
 *	BDMCore is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	BDMCore is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with BDMCore.  If not, see <http://www.gnu.org/licenses/>.
 *	SPDX-License-Identifier: GPL-3.0-or-later
 *
 ******************************************************************************/
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
    /* This operation handles the creation of the Given, When, Then step definitions. */
    @Override
    public CtMethod Create(String TemporaryObjectName, String functionName, String[] parametersParam, AnnotationTypes annotationType, String annotationValue, CtClass declaringClass, Boolean pending) throws Exception
    {
        ClassFile classFile = declaringClass.getClassFile();
        ConstPool constPool = classFile.getConstPool();
        ClassPool classPool = ClassPool.getDefault();
        /* This handles the creation of the attributes for the annotation of the generated operation inside the Java Class Object */
        AnnotationsAttribute annotationsAttribute = _annotation.Create(constPool, annotationType, annotationValue);
        CtClass[] parameters = new CtClass[parametersParam.length];
        int idx = 0;
        String body = "";
        /* If the step definitons is called with a pending condition it means that the type checker has found that the body of the VDM operation has a skip.
        The body of the generated step defintion will then throw a pending exeption */
        if(pending) {
            body = "throw new io.cucumber.java.PendingException();";
            for (String string : parametersParam) {
                CtClass wrapperClass = classPool.get(VDMUtility.DataTypeJAVAToPackage.get(string));
                parameters[idx] = wrapperClass;
                idx++;
            }
        } else {
            /* Else will the body of the step definition check the state of the local variables and use the VDMJ run operation  */
            body = String.format("{checkLocalVariable();run(\"%s.%s(\"",TemporaryObjectName,functionName); 
            for (String string : parametersParam) {
                CtClass wrapperClass = classPool.get(VDMUtility.DataTypeJAVAToPackage.get(string));
                body = body + (idx > 0 ? "\",\"" : "") + VDMUtility.ParamHelper(string, idx);
                parameters[idx] = wrapperClass;
                idx++;
            }
            body = body +"\") \");}";
        }
        /* The constructed Method is then made with Javassist */
        CtMethod newmethod = CtNewMethod.make(CtClass.voidType, functionName, parameters, null, body, declaringClass);
        newmethod.getMethodInfo().addAttribute(annotationsAttribute);
        return newmethod;
    }
    
}
