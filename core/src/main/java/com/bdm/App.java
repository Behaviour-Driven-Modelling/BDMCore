package com.bdm;

import com.bdm.factory.FactoryProvider;
import com.bdm.factory.IAbstractFactory;
import com.bdm.factory.locators.FactoryLocator;
import com.bdm.factory.locators.MethodLocator;
import com.bdm.factory.locators.ValidatorLocator;
import com.bdm.factory.method.IMethod;
import com.bdm.factory.method.MethodFactory;
import com.bdm.factory.validator.IValidator;
import com.bdm.factory.validator.ValidatorFactory;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.DuplicateMemberException;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

       /*  IAbstractFactory<IValidator,ValidatorLocator> validatorFactory = (ValidatorFactory)FactoryProvider.GetFactory(FactoryLocator.ValidatorFactory);
        IAbstractFactory<IMethod,MethodLocator> methodFactory = (MethodFactory)FactoryProvider.GetFactory(FactoryLocator.MethodFactory);

        IValidator stepDefinitionValidator = validatorFactory.create(ValidatorLocator.StepDefinitionValidator);

        IMethod startMethod = methodFactory.create(MethodLocator.CreateMethod);
        IMethod setupMethod = methodFactory.create(MethodLocator.SetupMethod);
        IMethod helperMethod = methodFactory.create(MethodLocator.CheckLocalVariableMethod);

        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass ctclass = pool.getCtClass("testasdf");
            if (ctclass.isFrozen()) {
                ctclass.defrost();
            }
            ctclass.setSuperclass(pool.get("com.fujitsu.vdmjunit.VDMJUnitTestPP"));
            try {
                ctclass.addMethod(startMethod.Create("f1", "start", null, AnnotationTypes.BeforeAll, null, ctclass, false));
                ctclass.addMethod(setupMethod.Create("f1", "setup", null, AnnotationTypes.Before, null, ctclass, false));
                ctclass.addMethod(helperMethod.Create("f1", "test", null, null, null, ctclass, false));                
            } catch (DuplicateMemberException e) {
                //System.out.printf("exception thrown: %s %s\n", e.getMessage(),"main");
            }
            ctclass.writeFile(VDMUtility.FindTargetFolder());
        } catch (NotFoundException e) {
            try {
                ClassPool pool = ClassPool.getDefault();
                CtClass ctclass = pool.makeClass("testasdf");
                if (ctclass.isFrozen()) {
                    ctclass.defrost();	
                }
                ctclass.setSuperclass(pool.get("com.fujitsu.vdmjunit.VDMJUnitTestPP"));
                try {
                    ctclass.addMethod(startMethod.Create("f1", "start", null, AnnotationTypes.BeforeAll, null, ctclass, false));
                    ctclass.addMethod(setupMethod.Create("f1", "setup", null, AnnotationTypes.Before, null, ctclass, false));
                    ctclass.addMethod(helperMethod.Create("f1", "test", null, null, null, ctclass, false));                
                } catch (DuplicateMemberException exce) {
                    //System.out.printf("exception thrown: %s %s\n", exce.getMessage(),"main");
                } 
                ctclass.writeFile(VDMUtility.FindTargetFolder());
            } catch (Exception exception) {
                System.out.printf("exception thrown: %s %s\n", exception.getMessage(),"main");
            }
        } catch (Exception exc) {
            System.out.printf("exception thrown: %s %s\n", exc.getMessage(),"main");
        }
        
 */
    }
}
