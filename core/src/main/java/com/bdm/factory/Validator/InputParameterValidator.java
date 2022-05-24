package com.bdm.factory.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bdm.VDMUtility;



public class InputParameterValidator implements IValidator<String[]> {
    private String _regexCurlyBrackets = "\\{(.+?)\\}";
    private String _regexParenthesis = "\\((.+?)\\)";
    private String[] _returnValues;
    private String _message = "";
    private int _code = 0;

    @Override
    public Boolean Validate(String[] args) {

        if (args.length != 2) {
            _message = "Validate function takes two parameters in string array: Annotation string and definition string.";
            _code = 6009;
            return false;
        }

        Matcher m1 = Pattern.compile(_regexCurlyBrackets).matcher(args[0]);
        int idx = 0;
        int idxStart = args[1].indexOf("(");
        int idxEnd = args[1].lastIndexOf(")");
        
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
                if (idx+1 < parameterlist.length) {
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
