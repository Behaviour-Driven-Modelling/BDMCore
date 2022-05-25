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

public class StepDefinitionValidator implements IValidator<TCExpression[]>{
    private TCExpression[] _returnValues;
    private String _message = "";
    private int _code = 0;
    /* This Validator checks the stepdefinition annotation parameter */
    @Override
    public Boolean Validate(TCExpression[] args) {
        if (args.length != 1) {
            _message = "Validate function takes one parameters in string array: temporary object name.";
            _code = 6009;
            return false;
        }
        if (!(args[0] instanceof TCStringLiteralExpression))
        {
            _message = "@StepDefinition argument 1 must be string value.";
            _code = 6010;
        
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
