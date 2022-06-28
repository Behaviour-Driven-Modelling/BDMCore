package com.bdm;

import java.util.ArrayList;
import java.util.List;

import com.fujitsu.vdmj.tc.annotations.TCAnnotation;
import com.fujitsu.vdmj.tc.annotations.TCAnnotationList;
import com.fujitsu.vdmj.tc.definitions.TCClassDefinition;
import com.fujitsu.vdmj.tc.definitions.TCDefinition;
import com.fujitsu.vdmj.tc.definitions.TCDefinitionList;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class StepDefinitionCleaner {
    private static final String packageName = "specification.";
    public void Cleanup(TCClassDefinition clazz) {
        try {
			ClassPool pool = ClassPool.getDefault();
			CtClass ctclass = pool.getCtClass(packageName+clazz.name.getName());
			if (ctclass.isFrozen()) {
				ctclass.defrost();
			}
			TCDefinitionList definitions = clazz.getDefinitions();
            
			List<String> definitionNames = new ArrayList<String>();
            List<String> annotationNames = new ArrayList<String>();
			for (TCDefinition definition : definitions) {
				definitionNames.add(definition.name.getName());
                TCAnnotationList annotations = definition.annotations;
                if (annotations != null) {
                    for (TCAnnotation annotation : annotations) {
                        if(annotation.args.size() > 1) {
                            annotationNames.add(annotation.args.elementAt(1).toString().replace("\"", "").trim());
                        }
                        
                    }
                }
                
			}
			for (CtMethod method : ctclass.getDeclaredMethods()) {

				RemoveMethodsNotInDefinition(definitionNames,method,ctclass);
                
			}
			ctclass.writeFile(VDMUtility.FindTargetFolder());
		} catch (NotFoundException exc) {
            try {
                ClassPool pool = ClassPool.getDefault();
                CtClass ctclass = pool.makeClass(packageName+clazz.name.getName());
                
                TCDefinitionList definitions = clazz.getDefinitions();

                List<String> definitionNames = new ArrayList<String>();
                List<String> annotationNames = new ArrayList<String>();

                for (TCDefinition definition : definitions) {
                    definitionNames.add(definition.name.getName());
                    TCAnnotationList annotations = definition.annotations;
                    if (annotations != null) {
                        for (TCAnnotation annotation : annotations) {
                            if(annotation.args.size() > 1) {
                                annotationNames.add(annotation.args.elementAt(1).toString().replace("\"", "").trim());
                            }
                        }
                    }
                }
            for (CtMethod method : ctclass.getDeclaredMethods()) {
				RemoveMethodsNotInDefinition(definitionNames,method,ctclass);

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
        if(method.getName()!= "start" && method.getName()!= "checkLocalVariable" && method.getName() != "setup"){
            ctclass.removeMethod(method);
        }
    }

}
