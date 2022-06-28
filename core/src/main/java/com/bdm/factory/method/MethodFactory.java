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

import com.bdm.factory.IAbstractFactory;
import com.bdm.factory.annotation.AnnotationFactory;
import com.bdm.factory.locators.AnnotationLocator;
import com.bdm.factory.locators.MethodLocator;

public class MethodFactory implements IAbstractFactory<IMethod,MethodLocator>{
    /* This is the factory to contruct any of the different methods used by BDM */
    @Override
    public IMethod create(MethodLocator choice) {
        AnnotationFactory annotationFactory = new AnnotationFactory();

        switch (choice) {
            case StepDefinitionMethod:
                return new StepDefinitionMethod(annotationFactory.create(AnnotationLocator.StepDefinitionAnnotation));
            case CreateMethod:
                return new StartMethod(annotationFactory.create(AnnotationLocator.HookAnnotation));
            case SetupMethod:
                return new SetupMethod(annotationFactory.create(AnnotationLocator.HookAnnotation));
            case CheckLocalVariableMethod:
                return new CheckLocalVariableMethod();
            default:
                return new StepDefinitionMethod(annotationFactory.create(AnnotationLocator.StepDefinitionAnnotation));
        }
    }
    
}
