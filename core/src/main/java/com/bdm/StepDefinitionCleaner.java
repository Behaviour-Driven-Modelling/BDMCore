package com.bdm;

import java.util.ArrayList;
import java.util.List;

import com.bdm.factory.FactoryProvider;
import com.bdm.factory.locators.FactoryLocator;
import com.bdm.factory.locators.MethodLocator;
import com.bdm.factory.locators.ValidatorLocator;
import com.bdm.factory.method.IMethod;
import com.bdm.factory.method.MethodFactory;
import com.bdm.factory.validator.IValidator;
import com.bdm.factory.validator.ValidatorFactory;
import com.fujitsu.vdmj.ast.lex.LexStringToken;
import com.fujitsu.vdmj.tc.annotations.TCAnnotation;
import com.fujitsu.vdmj.tc.annotations.TCAnnotationList;
import com.fujitsu.vdmj.tc.definitions.TCClassDefinition;
import com.fujitsu.vdmj.tc.definitions.TCDefinition;
import com.fujitsu.vdmj.tc.definitions.TCDefinitionList;
import com.fujitsu.vdmj.tc.expressions.TCExpression;
import com.fujitsu.vdmj.tc.expressions.TCStringLiteralExpression;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.DuplicateMemberException;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;

public class StepDefinitionCleaner {
    public void Cleanup(TCClassDefinition clazz) {
        try {
			ClassPool pool = ClassPool.getDefault();
			CtClass ctclass = pool.getCtClass(clazz.name.getName());
			if (ctclass.isFrozen()) {
				ctclass.defrost();
			}
			TCDefinitionList definitions = clazz.getDefinitions();
            
			List<String> definitionNames = new ArrayList<String>();
            List<String> annotationNames = new ArrayList<String>();
            
			for (TCDefinition definition : definitions) {
				definitionNames.add(definition.name.getName());
                TCAnnotationList annotations = definition.annotations;
                for (TCAnnotation annotation : annotations) {
                    annotationNames.add(annotation.args.elementAt(1).toString().replace("\"", "").trim());
                }
			}
           
			for (CtMethod method : ctclass.getDeclaredMethods()) {
				RemoveMethodsNotInDefinition(definitionNames,method,ctclass);
                RemoveMethodsOnAnnotation(BDMUtility.BDMType(AnnotationTypes.Given), annotationNames, method, ctclass);
                RemoveMethodsOnAnnotation(BDMUtility.BDMType(AnnotationTypes.When), annotationNames, method, ctclass);
                RemoveMethodsOnAnnotation(BDMUtility.BDMType(AnnotationTypes.Then), annotationNames, method, ctclass);
			}

			ctclass.writeFile(VDMUtility.FindTargetFolder());
		} catch (NotFoundException exc) {
            try {
                ClassPool pool = ClassPool.getDefault();
                CtClass ctclass = pool.makeClass(clazz.name.getName());
                
                TCDefinitionList definitions = clazz.getDefinitions();

                List<String> definitionNames = new ArrayList<String>();
                List<String> annotationNames = new ArrayList<String>();

                for (TCDefinition definition : definitions) {
                    definitionNames.add(definition.name.getName());
                    TCAnnotationList annotations = definition.annotations;
                    for (TCAnnotation annotation : annotations) {
                        annotationNames.add(annotation.args.elementAt(1).toString().replace("\"", "").trim());
                    }
                }
            for (CtMethod method : ctclass.getDeclaredMethods()) {
				RemoveMethodsNotInDefinition(definitionNames,method,ctclass);
                RemoveMethodsOnAnnotation(BDMUtility.BDMType(AnnotationTypes.Given), annotationNames, method, ctclass);
                RemoveMethodsOnAnnotation(BDMUtility.BDMType(AnnotationTypes.When), annotationNames, method, ctclass);
                RemoveMethodsOnAnnotation(BDMUtility.BDMType(AnnotationTypes.Then), annotationNames, method, ctclass);
			}
                ctclass.writeFile(VDMUtility.FindTargetFolder());
            } catch (Exception e) {
                System.out.printf("exception thrown: %s %s\n", e.getMessage(),this);
            }
           
        }       
        catch (Exception e) {
			System.out.printf("exception thrown: %s %s\n", e.getMessage(),this);
		}
    }

    private void RemoveMethodsNotInDefinition(List<String> definitionNames, CtMethod method, CtClass ctclass) throws NotFoundException 
    {
        if(!definitionNames.contains(method.getName()))
        {
            if(method.getName()!= "start" && method.getName()!= "checkLocalVariable" && method.getName() != "setup"){
                ctclass.removeMethod(method);
            }
            
        }
    }

    private void RemoveMethodsOnAnnotation(String annotation,List<String> annotationNames, CtMethod method, CtClass ctclass) throws NotFoundException
    {
        if (method.hasAnnotation(annotation)) {
            MethodInfo minfo = method.getMethodInfo();
            AnnotationsAttribute attr = (AnnotationsAttribute)minfo.getAttribute(AnnotationsAttribute.visibleTag);
            
            Annotation an = attr.getAnnotation(annotation);
            String s = ((StringMemberValue) an.getMemberValue("value")).getValue().toString();
            if(!annotationNames.contains(s.trim())){
                ctclass.removeMethod(method);

            }
        }
    }

}
