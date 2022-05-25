package com.bdm.factory.validator;

import com.fujitsu.vdmj.tc.expressions.TCExpression;
import com.fujitsu.vdmj.tc.expressions.TCStringLiteralExpression;


public class AnnotationParameterValidator implements IValidator<TCExpression[]> {
    private TCExpression[] _returnValues;
    private String _message = "";
    private int _code = 0;
    @Override
    public Boolean Validate(TCExpression[] args) {
        if (args.length != 2) {
            _message = "Validate function takes three parameters in string array: Annotation value parameter, annotation name parameter 2 and Annotation type.";
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
