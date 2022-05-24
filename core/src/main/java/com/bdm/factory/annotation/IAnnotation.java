package com.bdm.factory.annotation;

import com.bdm.AnnotationTypes;

import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;

public interface IAnnotation {
    AnnotationsAttribute Create(ConstPool constPool, AnnotationTypes type, String value);
}
