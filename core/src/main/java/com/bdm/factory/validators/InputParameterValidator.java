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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bdm.VDMUtility;



public class InputParameterValidator implements IValidator<String[]> {
    private String _regexCurlyBrackets = "\\{(.+?)\\}";
    private String _regexParenthesis = "\\((.+?)\\)";
    private String[] _returnValues;
    private String _message = "";
    private int _code = 0;

    /* This validator checks if the input parameter types are the same for both the annotation and the operation definition */
    @Override
    public Boolean Validate(String[] args) {
        /* Validates size */
        if (args.length != 2) {
            _message = "Validate function takes two parameters in string array: Annotation string and definition string.";
            _code = 6009;
            return false;
        }
        /* Create identifier pattern for annotation parameters */
        Matcher m1 = Pattern.compile(_regexCurlyBrackets).matcher(args[0]);
        int idx = 0;
        int idxStart = args[1].indexOf("(");
        int idxEnd = args[1].lastIndexOf(")");
        
        /* identifies parameter type and performs convertion to VDM */
        if (m1.find()){
            String[] parameterlist = args[1].substring(idxStart, idxEnd).replace(")", "").replace("(", "").split(", ");
            String[] javaParamList = new String[parameterlist.length];
            javaParamList[idx] = m1.group(1);
            if(! VDMUtility.DataTypeJAVAToVDM.get(m1.group(1)).trim().equals(parameterlist[idx])) {
                _code = 6009;
                _message = "Parameter "+ (idx+1) +" must be of type: "+VDMUtility.DataTypeJAVAToVDM.get(m1.group(1));
                return false;
            }
            idx++;
            while(m1.find()){
                if (idx+1 <= parameterlist.length) {
                    if(VDMUtility.DataTypeJAVAToVDM.get(m1.group(1)).trim().equals(parameterlist[idx])) {
                    }  else {
                        _code = 6009;
                        _message = "Parameter "+ (idx+1) +" must be of type: "+VDMUtility.DataTypeJAVAToVDM.get(m1.group(1));
                        return false;
                    }
                    javaParamList[idx] = m1.group(1);
                } 
                idx++;
            }
            if (idx != parameterlist.length) {
                _code = 6009;
                _message =  "Expected " + idx + " parameters but found "+parameterlist.length;
                return false;
            }
            
            _returnValues = javaParamList;
        } else {
            _returnValues = new String[0];
            /* Create identifier pattern for operation parameters */
            Matcher m2 = Pattern.compile(_regexParenthesis).matcher(args[1]);
            if(m2.find()){
                _code = 6009;
                _message = "Parameterlist must be empty";
                return false;
            }
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
    public String[] ReturnValues() {
       
        return _returnValues;
    }
    
}
