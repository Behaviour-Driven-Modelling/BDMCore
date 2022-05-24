package com.bdm.factory.method;

import com.bdm.factory.IAbstractFactory;
import com.bdm.factory.annotation.AnnotationFactory;
import com.bdm.factory.locators.AnnotationLocator;
import com.bdm.factory.locators.MethodLocator;

public class MethodFactory implements IAbstractFactory<IMethod,MethodLocator>{

    @Override
    public IMethod create(MethodLocator choice) {
        AnnotationFactory annotationFactory = new AnnotationFactory();

        switch (choice) {
            case StepDefinitionMethod:
                return new StepDefinitionMethod(annotationFactory.create(AnnotationLocator.StepDefinitionAnnotation));
            case CreateMethod:
                return new StartMethod(annotationFactory.create(AnnotationLocator.HookAnnotation));
            case SetupMethod:
                return new SetupMethod(annotationFactory.create(AnnotationLocator.HookAnnotation));
            case CheckLocalVariableMethod:
                return new CheckLocalVariableMethod();
            default:
                return new StepDefinitionMethod(annotationFactory.create(AnnotationLocator.StepDefinitionAnnotation));
        }
    }
    
}
