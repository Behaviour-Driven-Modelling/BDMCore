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
package com.bdm.factory;

import com.bdm.factory.annotation.AnnotationFactory;
import com.bdm.factory.locators.FactoryLocator;
import com.bdm.factory.method.MethodFactory;
import com.bdm.factory.validators.ValidatorFactory;

public class FactoryProvider {

    @SuppressWarnings("rawtypes")
    public static  IAbstractFactory GetFactory(FactoryLocator choice) {
        switch (choice) {
            case ValidatorFactory:
                return new ValidatorFactory();
            case AnnotationFactory:
                return new AnnotationFactory();
            case MethodFactory:
            return new MethodFactory();
            default:
                return new ValidatorFactory();
        }
    }
}
