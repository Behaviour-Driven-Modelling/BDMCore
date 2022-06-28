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
package com.bdm.factory.annotation;



import com.bdm.AnnotationTypes;
import com.bdm.BDMUtility;

import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;

public class HookAnnotation implements IAnnotation {
    /* This is the HookAnnotation class which creates the hook annotations such as @BeforeAll */
    @Override
    public AnnotationsAttribute Create(ConstPool constPool, AnnotationTypes type, String value) {
        AnnotationsAttribute annotationsAttribute = new AnnotationsAttribute(constPool,AnnotationsAttribute.visibleTag);
        Annotation annotation = new Annotation(BDMUtility.BDMType(type), constPool);
        annotationsAttribute.addAnnotation(annotation);
        return annotationsAttribute;
    }

}
