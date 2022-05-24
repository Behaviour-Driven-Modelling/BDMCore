package com.bdm.factory.annotation;

import com.bdm.factory.IAbstractFactory;
import com.bdm.factory.locators.AnnotationLocator;

public class AnnotationFactory implements IAbstractFactory<IAnnotation, AnnotationLocator> {

    @Override
    public IAnnotation create(AnnotationLocator choice) {
        switch (choice) {
            case StepDefinitionAnnotation:
                return new StepDefinitionAnnotation();
            case HookAnnotation:
                return new HookAnnotation();
            default:
                return new StepDefinitionAnnotation();
        }

    }
    
}
