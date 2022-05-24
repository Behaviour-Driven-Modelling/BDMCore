package com.bdm.factory.annotation;



import com.bdm.AnnotationTypes;
import com.bdm.BDMUtility;

import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;

public class HookAnnotation implements IAnnotation {

    @Override
    public AnnotationsAttribute Create(ConstPool constPool, AnnotationTypes type, String value) {
        AnnotationsAttribute annotationsAttribute = new AnnotationsAttribute(constPool,AnnotationsAttribute.visibleTag);
        Annotation annotation = new Annotation(BDMUtility.BDMType(type), constPool);
        annotationsAttribute.addAnnotation(annotation);
        return annotationsAttribute;
    }

}
