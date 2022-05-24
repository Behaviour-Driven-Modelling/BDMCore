package com.bdm.factory.validator;

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
