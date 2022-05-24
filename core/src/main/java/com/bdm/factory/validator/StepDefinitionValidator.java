package com.bdm.factory.validator;

import com.fujitsu.vdmj.tc.expressions.TCExpression;
import com.fujitsu.vdmj.tc.expressions.TCStringLiteralExpression;

public class StepDefinitionValidator implements IValidator<TCExpression[]>{
    private TCExpression[] _returnValues;
    private String _message = "";
    private int _code = 0;
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
