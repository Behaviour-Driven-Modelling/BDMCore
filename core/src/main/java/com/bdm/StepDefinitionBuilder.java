package com.bdm;

import java.io.Console;

import com.bdm.factory.FactoryProvider;
import com.bdm.factory.locators.FactoryLocator;
import com.bdm.factory.locators.MethodLocator;
import com.bdm.factory.locators.ValidatorLocator;
import com.bdm.factory.method.IMethod;
import com.bdm.factory.method.MethodFactory;
import com.bdm.factory.validator.IValidator;
import com.bdm.factory.validator.ValidatorFactory;
import com.fujitsu.vdmj.tc.definitions.TCDefinition;
import com.fujitsu.vdmj.tc.expressions.TCExpression;
import com.fujitsu.vdmj.tc.expressions.TCStringLiteralExpression;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.DuplicateMemberException;


public class StepDefinitionBuilder {
    private static final String packageName = "specification.";
    public void BuildStepDefinitionClass(String className, TCExpression nameArgument)
    {
        ValidatorFactory validatorFactory = (ValidatorFactory)FactoryProvider.GetFactory(FactoryLocator.ValidatorFactory);
        IValidator<TCExpression[]> stepDefinitionValidator = (IValidator<TCExpression[]>)validatorFactory.create(ValidatorLocator.StepDefinitionValidator);

        TCExpression[] args = new TCExpression[1];
        args[0] = nameArgument;

        if(stepDefinitionValidator.Validate(args)) {
            TCStringLiteralExpression temporaryObjectName = (TCStringLiteralExpression)args[0];
            try {
                ClassPool pool = ClassPool.getDefault();
                CtClass ctclass = pool.getCtClass(packageName+className);
                StepDefinitionClassWriter(ctclass,pool,temporaryObjectName.value.value);
            } catch (NotFoundException e) {
                try {
                    ClassPool pool = ClassPool.getDefault();
                    
                    CtClass ctclass = pool.makeClass(packageName+className);
                    StepDefinitionClassWriter(ctclass,pool,temporaryObjectName.value.value);
                } catch (Exception exception) {
                    System.out.printf("exception thrown: %s %s\n", exception.getMessage(),this);
                }
            } catch (Exception exc) {
                System.out.printf("exception thrown: %s %s\n", exc.getMessage(),this);
            }
        } else {
            args[0].report(stepDefinitionValidator.Code(), stepDefinitionValidator.Message());
        }

        
    }
    public void BuildStepDefinitionMethod(TCDefinition def, AnnotationTypes annotationType, TCExpression valueArgument, TCExpression nameArgument ){
        ValidatorFactory validatorFactory = (ValidatorFactory)FactoryProvider.GetFactory(FactoryLocator.ValidatorFactory);
        IValidator<TCExpression[]> annotationValidator = (IValidator<TCExpression[]>)validatorFactory.create(ValidatorLocator.AnnotationValidator);
        IValidator<String[]>  inputParameterValidator = (IValidator<String[]>)validatorFactory.create(ValidatorLocator.InputValidator);
        TCExpression[] args = new TCExpression[2];
        args[0] = valueArgument;
        args[1] = nameArgument;
        if(annotationValidator.Validate(args)) {
            String[] inputArgs = new String[2];
            TCStringLiteralExpression valueArgumentSL = (TCStringLiteralExpression)valueArgument;
            TCStringLiteralExpression nameArgumentSL = (TCStringLiteralExpression)nameArgument;
            
            inputArgs[0] = valueArgumentSL.value.value;
            inputArgs[1] = def.name.toString();

            try {
                if(inputParameterValidator.Validate(inputArgs)) {
                    ClassPool pool = ClassPool.getDefault();
                    CtClass ctclass = pool.getCtClass(packageName+def.name.getModule());
                    StepDefinintionMethodWriter(def,ctclass,nameArgumentSL.value.value, inputParameterValidator.ReturnValues(), annotationType, valueArgumentSL.value.value);
                } else {
                    def.report(inputParameterValidator.Code(), inputParameterValidator.Message());
                }
                
            } catch (NotFoundException exce) {
                try {
                    if(inputParameterValidator.Validate(inputArgs)) {
                        ClassPool pool = ClassPool.getDefault();
                        CtClass ctclass = pool.makeClass(packageName+def.name.getModule());
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

    private void StepDefinintionMethodWriter(TCDefinition def, CtClass ctclass, String temporaryObjectName, String[] inputParameters, AnnotationTypes annotationType, String annotationValue) throws Exception{
        MethodFactory methodFactory = (MethodFactory)FactoryProvider.GetFactory(FactoryLocator.MethodFactory);
        IMethod stepDefinitionMethod = methodFactory.create(MethodLocator.StepDefinitionMethod);

        if (ctclass.isFrozen()) {
            ctclass.defrost();
        }
        try {
            
            int idxStartBody = def.toString().lastIndexOf("=");
            if(def.toString().substring(idxStartBody+1).trim().contains("skip")) {
                ctclass.addMethod(stepDefinitionMethod.Create(temporaryObjectName, def.name.getName(), inputParameters, annotationType, annotationValue, ctclass, true));
            } else {
                ctclass.addMethod(stepDefinitionMethod.Create(temporaryObjectName, def.name.getName(), inputParameters, annotationType, annotationValue, ctclass, false));
            }
        } catch (DuplicateMemberException e) {
            //System.out.printf("exception thrown: %s %s\n", e.getMessage(),this);
        }
        ctclass.writeFile(VDMUtility.FindTargetFolder());
    }

    private void StepDefinitionClassWriter(CtClass ctclass, ClassPool pool, String temporaryObjectName)throws Exception {
        MethodFactory methodFactory = (MethodFactory)FactoryProvider.GetFactory(FactoryLocator.MethodFactory);
        IMethod startMethod = methodFactory.create(MethodLocator.CreateMethod);
        IMethod setupMethod = methodFactory.create(MethodLocator.SetupMethod);
        IMethod helperMethod = methodFactory.create(MethodLocator.CheckLocalVariableMethod);

        if (ctclass.isFrozen()) {
            ctclass.defrost();	
        }
        ctclass.setSuperclass(pool.get("com.fujitsu.vdmjunit.VDMJUnitTestPP"));
        
        try {
            ctclass.addMethod(startMethod.Create(temporaryObjectName, "start", null, AnnotationTypes.BeforeAll, null, ctclass, false));
            ctclass.addMethod(setupMethod.Create(temporaryObjectName, "setup", null, AnnotationTypes.Before, null, ctclass, false));
            String functionName = ctclass.getName().substring(ctclass.getName().lastIndexOf(".")+1);
            //System.out.println("FUNCTiONAME: " + functionName);
            ctclass.addMethod(helperMethod.Create(temporaryObjectName, functionName, null, null, null, ctclass, false));                
        } catch (DuplicateMemberException exce) {
            //System.out.printf("exception thrown: %s %s\n", exce.getMessage(),this);
        } 
        ctclass.writeFile(VDMUtility.FindTargetFolder());
    }
}
