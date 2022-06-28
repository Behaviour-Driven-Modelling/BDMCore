/*******************************************************************************
 *
 *	Copyright (c) 2022 Malthe Dalgaard Jensen & Kristoffer Stampe Villadsen.
 *
 *	Author: Malthe Dalgaard Jensen & Kristoffer Stampe Villadsen
 *
 *	This file is part of BDMCore.
 *
 *	BDMCore is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	BDMCore is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with BDMCore.  If not, see <http://www.gnu.org/licenses/>.
 *	SPDX-License-Identifier: GPL-3.0-or-later
 *
 ******************************************************************************/
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

/* This is the the cleaning operation, This handles the clean-up of legacy operations created through applying BDM*/
public class StepDefinitionCleaner {
    private static final String packageName = "specification.";
    public void Cleanup(TCClassDefinition clazz) {
        /* This tries to get an exsiting class and identifies all annotations and operation definitons*/
        try {
			ClassPool pool = ClassPool.getDefault();
			CtClass ctclass = pool.getCtClass(packageName+clazz.name.getName());
			if (ctclass.isFrozen()) {
				ctclass.defrost();
			}
			TCDefinitionList definitions = clazz.getDefinitions();
            
			List<String> definitionNames = new ArrayList<String>();
            List<String> annotationNames = new ArrayList<String>();
            /* This looks through for each definition */
			for (TCDefinition definition : definitions) {
				definitionNames.add(definition.name.getName());
                TCAnnotationList annotations = definition.annotations;
                if (annotations != null) {
                    /* This looks through for each annotation */
                    for (TCAnnotation annotation : annotations) {
                        if(annotation.args.size() > 1) {
                            annotationNames.add(annotation.args.elementAt(1).toString().replace("\"", "").trim());
                        }
                        
                    }
                }
                
			}
            /* For all of definition and annotations which are legacy found are removed. */
			for (CtMethod method : ctclass.getDeclaredMethods()) {

				RemoveMethodsNotInDefinition(definitionNames,method,ctclass);
                
			}
			ctclass.writeFile(VDMUtility.FindTargetFolder());
		} catch (NotFoundException exc) {
            /* This tries to make a class and identifies any legacy annotations or operation definitons*/
            try {
                ClassPool pool = ClassPool.getDefault();
                CtClass ctclass = pool.makeClass(packageName+clazz.name.getName());
                
                TCDefinitionList definitions = clazz.getDefinitions();

                List<String> definitionNames = new ArrayList<String>();
                List<String> annotationNames = new ArrayList<String>();

                /* This looks through for each definition */
                for (TCDefinition definition : definitions) {
                    definitionNames.add(definition.name.getName());
                    TCAnnotationList annotations = definition.annotations;
                    if (annotations != null) {
                        /* This looks through for each annotation */
                        for (TCAnnotation annotation : annotations) {
                            if(annotation.args.size() > 1) {
                                annotationNames.add(annotation.args.elementAt(1).toString().replace("\"", "").trim());
                            }
                        }
                    }
                }
                /* For all of definition and annotations which are legacy are removed. */
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
    /* This is a helper function which removes any method which is not in the list of definitions, these would be legacy methods */
    private void RemoveMethodsNotInDefinition(List<String> definitionNames, CtMethod method, CtClass ctclass) throws NotFoundException 
    {
        if(method.getName()!= "start" && method.getName()!= "checkLocalVariable" && method.getName() != "setup"){
            ctclass.removeMethod(method);
        }
        
    }

}
