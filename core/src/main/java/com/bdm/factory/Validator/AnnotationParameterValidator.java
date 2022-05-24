package com.bdm.factory.Validator;

import com.fujitsu.vdmj.ast.lex.LexStringToken;
import com.fujitsu.vdmj.tc.expressions.TCExpression;
import com.fujitsu.vdmj.tc.expressions.TCStringLiteralExpression;


public class AnnotationParameterValidator implements IValidator<TCExpression[]> {
    private TCExpression[] _returnValues;
    private String _message = "";
    private int _code = 0;
    @Override
    public Boolean Validate(TCExpression[] args) {
        if (args.length != 3) {
            _message = "Validate function takes three parameters in string array: Annotation parameter 1, annotation parameter 2 and Annotation type.";
            _code = 6009;
            return false;
        }
        if (!(args[2] instanceof TCStringLiteralExpression)) {
            _code = 6010;
            _message = "Validate function takes a string value as third parameter: Annotation type.";
        }
        TCStringLiteralExpression bdmType = (TCStringLiteralExpression)args[2];
        if (!(args[0] instanceof TCStringLiteralExpression))
        {
            _code = 6010;
            _message = String.format("@%s argument 2 must be a string value",bdmType.value.value);
            return false;
        } 
        

        if (!(args[1] instanceof TCStringLiteralExpression))
        {
            _code = 6010;
            _message = String.format("@%s argument 1 must be a string value",bdmType.value.value);
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
