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
package com.bdm.factory.validators;

import com.bdm.factory.IAbstractFactory;
import com.bdm.factory.locators.ValidatorLocator;

@SuppressWarnings("rawtypes")
public class ValidatorFactory implements IAbstractFactory<IValidator,ValidatorLocator>{

    @Override
    public IValidator create(ValidatorLocator choice) {
        switch (choice) {
            case InputValidator:
                return new InputParameterValidator();
            case AnnotationValidator:
                return new AnnotationParameterValidator();
            case StepDefinitionValidator:
                return new StepDefinitionValidator();
            default:
                return new InputParameterValidator();
        }
    }
    
}
