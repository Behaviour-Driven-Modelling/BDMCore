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

import java.io.Console;

import com.bdm.factory.FactoryProvider;
import com.bdm.factory.locators.FactoryLocator;
import com.bdm.factory.locators.MethodLocator;
import com.bdm.factory.locators.ValidatorLocator;
import com.bdm.factory.method.IMethod;
import com.bdm.factory.method.MethodFactory;
import com.bdm.factory.validators.IValidator;
import com.bdm.factory.validators.ValidatorFactory;
import com.fujitsu.vdmj.tc.definitions.TCDefinition;
import com.fujitsu.vdmj.tc.expressions.TCExpression;
import com.fujitsu.vdmj.tc.expressions.TCStringLiteralExpression;
  
import javassist.ClassPool;
import javassist.CtClass;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.DuplicateMemberException;

/* This class handles the manipulation of Java Class Objects this is done by using the Javassist library. */
public class StepDefinitionBuilder {
    private static final String packageName = "specification.";
    /* This operation builds a class based on a VDM++ class annotationed with the BDM @stepdefinition annotation*/
    public void BuildStepDefinitionClass(String className, TCExpression nameArgument)
    {
        ValidatorFactory validatorFactory = (ValidatorFactory)FactoryProvider.GetFactory(FactoryLocator.ValidatorFactory);
        IValidator<TCExpression[]> stepDefinitionValidator = (IValidator<TCExpression[]>)validatorFactory.create(ValidatorLocator.StepDefinitionValidator);

        TCExpression[] args = new TCExpression[1];
        args[0] = nameArgument;

        /* This validates that the parameter for the annotation is correct */
        if(stepDefinitionValidator.Validate(args)) {
            TCStringLiteralExpression temporaryObjectName = (TCStringLiteralExpression)args[0];
            /* This tries to get the class if it already exists */
            try {
                ClassPool pool = ClassPool.getDefault();
                CtClass ctclass = pool.getCtClass(packageName+className);
                /* The file writer function is called to manipulated with the Java Class Object */
                StepDefinitionClassWriter(ctclass,pool,temporaryObjectName.value.value);
            } catch (NotFoundException e) {
                /* This tries to make the class given that it do not already exists */
                try {
                    ClassPool pool = ClassPool.getDefault();
                    CtClass ctclass = pool.makeClass(packageName+className);
                    /* The file writer function is called to manipulated with the Java Class Object */
                    StepDefinitionClassWriter(ctclass,pool,temporaryObjectName.value.value);
                } catch (Exception exception) {
                    System.out.printf("exception thrown: %s %s\n", exception.getMessage(),this);
                }
            } catch (Exception exc) {
                System.out.printf("exception thrown: %s %s\n", exc.getMessage(),this);
            }
        } else {
            /* If the parameter of the annotation is incorrect an error is reported */
            args[0].report(stepDefinitionValidator.Code(), stepDefinitionValidator.Message());
        }

        
    }
    /* This operation builds the different VDM operation as a java operation. The VDM operations needs to be annotated in a VDM++ class with the BDM @Given, @When, @Then annotations*/
    public void BuildStepDefinitionMethod(TCDefinition def, AnnotationTypes annotationType, TCExpression valueArgument, TCExpression nameArgument ){
        ValidatorFactory validatorFactory = (ValidatorFactory)FactoryProvider.GetFactory(FactoryLocator.ValidatorFactory);
        IValidator<TCExpression[]> annotationValidator = (IValidator<TCExpression[]>)validatorFactory.create(ValidatorLocator.AnnotationValidator);
        IValidator<String[]>  inputParameterValidator = (IValidator<String[]>)validatorFactory.create(ValidatorLocator.InputValidator);
        TCExpression[] args = new TCExpression[2];
        args[0] = valueArgument;
        args[1] = nameArgument;
        /* This validates the parameters on the annotations for each of the annotated operations */
        if(annotationValidator.Validate(args)) {
            String[] inputArgs = new String[2];
            /* This is the input parameter which is validated for its types */
            TCStringLiteralExpression valueArgumentSL = (TCStringLiteralExpression)valueArgument;
            /* This is the temporary name of the java class object */
            TCStringLiteralExpression nameArgumentSL = (TCStringLiteralExpression)nameArgument;
            
            inputArgs[0] = valueArgumentSL.value.value;
            inputArgs[1] = def.name.toString();
            /* This tries to get the method if it already exists and manipluated it */
            try {
                /* Validates the input parameter types */
                if(inputParameterValidator.Validate(inputArgs)) {
                    ClassPool pool = ClassPool.getDefault();
                    CtClass ctclass = pool.getCtClass(packageName+def.name.getModule());
                    /* The file method writer function is called to manipulated with the Java Class Object */
                    StepDefinintionMethodWriter(def,ctclass,nameArgumentSL.value.value, inputParameterValidator.ReturnValues(), annotationType, valueArgumentSL.value.value);
                } else {
                    def.report(inputParameterValidator.Code(), inputParameterValidator.Message());
                }
                
            } catch (NotFoundException exce) {
                /* If the method do not exist this makes it  */
                try {
                    if(inputParameterValidator.Validate(inputArgs)) {
                        ClassPool pool = ClassPool.getDefault();
                        CtClass ctclass = pool.makeClass(packageName+def.name.getModule());
                        /* The file method writer function is called to manipulated with the Java Class Object */
                        StepDefinintionMethodWriter(def,ctclass,nameArgumentSL.value.value, inputParameterValidator.ReturnValues(), annotationType, valueArgumentSL.value.value);
                    } else {
                        def.report(inputParameterValidator.Code(), inputParameterValidator.Message());
                    }
                } catch (Exception e) {
                    System.out.printf("exception thrown: %s %s\n", e.getMessage(),this);
                }
                
                
            } catch (Exception exception) {
                System.out.printf("exception thrown: %s %s\n", exception.getMessage(),this);
            }
        } else {
            args[annotationValidator.Code()].report(6010, annotationValidator.Message());
        }

    }

    /* This is a helper function which handles the writing methods to a java object class*/
    private void StepDefinintionMethodWriter(TCDefinition def, CtClass ctclass, String temporaryObjectName, String[] inputParameters, AnnotationTypes annotationType, String annotationValue) throws Exception{
        MethodFactory methodFactory = (MethodFactory)FactoryProvider.GetFactory(FactoryLocator.MethodFactory);
        IMethod stepDefinitionMethod = methodFactory.create(MethodLocator.StepDefinitionMethod);

        if (ctclass.isFrozen()) {
            ctclass.defrost();
        }
        /* This looks at the body of the function to determine if the body contains a skip. */
        try {
            int idxStartBody = def.toString().lastIndexOf("=");
            if(def.toString().substring(idxStartBody+1).trim().contains("skip")) {
                /* If the body contains a skip the builder creates a pending exeception as per the Cucumber standard. */
                ctclass.addMethod(stepDefinitionMethod.Create(temporaryObjectName, def.name.getName(), inputParameters, annotationType, annotationValue, ctclass, true));
            } else {
                /* Else it adds the method to the java class object. */
                ctclass.addMethod(stepDefinitionMethod.Create(temporaryObjectName, def.name.getName(), inputParameters, annotationType, annotationValue, ctclass, false));
            }
        } catch (DuplicateMemberException e) {
        }
        ctclass.writeFile(VDMUtility.FindTargetFolder());
    }

    /* This is a helper function which handles the writing of a java class object class */
    private void StepDefinitionClassWriter(CtClass ctclass, ClassPool pool, String temporaryObjectName)throws Exception {
        MethodFactory methodFactory = (MethodFactory)FactoryProvider.GetFactory(FactoryLocator.MethodFactory);
        IMethod startMethod = methodFactory.create(MethodLocator.CreateMethod);
        IMethod setupMethod = methodFactory.create(MethodLocator.SetupMethod);
        IMethod helperMethod = methodFactory.create(MethodLocator.CheckLocalVariableMethod);

        if (ctclass.isFrozen()) {
            ctclass.defrost();	
        }
        ctclass.setSuperclass(pool.get("com.fujitsu.vdmjunit.VDMJUnitTestPP"));
        /* This tries to create the boiler plate hooks and helper methods inside of the java class object class */
        try {
            ctclass.addMethod(startMethod.Create(temporaryObjectName, "start", null, AnnotationTypes.BeforeAll, null, ctclass, false));
            ctclass.addMethod(setupMethod.Create(temporaryObjectName, "setup", null, AnnotationTypes.Before, null, ctclass, false));
            String functionName = ctclass.getName().substring(ctclass.getName().lastIndexOf(".")+1);
            //System.out.println("FUNCTiONAME: " + functionName);
            ctclass.addMethod(helperMethod.Create(temporaryObjectName, functionName, null, null, null, ctclass, false));                
        } catch (DuplicateMemberException exce) {
        } 
        ctclass.writeFile(VDMUtility.FindTargetFolder());
    }
}
