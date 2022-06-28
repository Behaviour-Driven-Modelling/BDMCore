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

import com.fujitsu.vdmj.tc.expressions.TCExpression;
import com.fujitsu.vdmj.tc.expressions.TCStringLiteralExpression;


public class AnnotationParameterValidator implements IValidator<TCExpression[]> {
    // Return Values are not used by this validator
    private TCExpression[] _returnValues;
    private String _message = "";
    private int _code = 0;

    /* Based on the type checker expression this validator checks the size of the annotations parameterlist and does type checking on the parameterlist*/
    @Override
    public Boolean Validate(TCExpression[] args) {
        if (args.length != 2) {
            _message = "Validate function takes three parameters in string array: Annotation value parameter, annotation name parameter and Annotation type.";
            _code = 6009;
            return false;
        }
       
        if (!(args[0] instanceof TCStringLiteralExpression))
        {
            _code = 0;
            _message = String.format("Argument 2 must be a string value");
            return false;
        } 
        

        if (!(args[1] instanceof TCStringLiteralExpression))
        {
            _code = 1;
            _message = String.format("Argument 1 must be a string value");
            return false;
        } 

        return true;
    }

    @Override
    public String Message() {
        return _message;
    }

    @Override
    public int Code() {
        return _code;
    }

    @Override
    public TCExpression[] ReturnValues() {
        return _returnValues;
    }
    
}
