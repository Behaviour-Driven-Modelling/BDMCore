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
    /* This creates the Set up method in the Java Object Class, This is called the init operation. This runs the VDMJ init operation to setup the VDMJ environment for the local variables */
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
