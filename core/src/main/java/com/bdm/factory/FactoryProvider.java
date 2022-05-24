package com.bdm.factory;

import com.bdm.factory.annotation.AnnotationFactory;
import com.bdm.factory.locators.FactoryLocator;
import com.bdm.factory.method.MethodFactory;
import com.bdm.factory.validator.ValidatorFactory;

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
