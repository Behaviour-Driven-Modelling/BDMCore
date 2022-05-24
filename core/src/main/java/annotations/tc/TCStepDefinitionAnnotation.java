package annotations.tc;


import com.bdm.StepDefinitionBuilder;
import com.fujitsu.vdmj.tc.annotations.TCAnnotation;
import com.fujitsu.vdmj.tc.definitions.TCClassDefinition;
import com.fujitsu.vdmj.tc.definitions.TCDefinition;
import com.fujitsu.vdmj.tc.expressions.TCExpression;
import com.fujitsu.vdmj.tc.expressions.TCExpressionList;
import com.fujitsu.vdmj.tc.expressions.TCStringLiteralExpression;
import com.fujitsu.vdmj.tc.lex.TCIdentifierToken;
import com.fujitsu.vdmj.tc.modules.TCModule;
import com.fujitsu.vdmj.tc.statements.TCStatement;
import com.fujitsu.vdmj.typechecker.Environment;
import com.fujitsu.vdmj.typechecker.NameScope;




public class TCStepDefinitionAnnotation extends TCAnnotation
{
	private static final long serialVersionUID = 1L;
    public TCStepDefinitionAnnotation(TCIdentifierToken name, TCExpressionList args)
	{
		super(name, args);
	}

	public static void doInit()
	{
	}

    @Override
	public void tcBefore(TCDefinition def, Environment env, NameScope scope)
	{
		name.report(6009, "@StepDefinitions only applies to classes");
		
	}
	

	@Override
	public void tcBefore(TCModule module)
	{
		name.report(6009, "@StepDefinitions only applies to classes");
	}

	@Override
	public void tcBefore(TCClassDefinition clazz)
	{
        TCExpression nameArg = args.elementAt(0);
		StepDefinitionBuilder test = new StepDefinitionBuilder();
		test.BuildStepDefinitionClass(clazz.name.getName(), nameArg);
		
	}

	@Override
	public void tcBefore(TCExpression exp, Environment env, NameScope scope)
	{
		
		name.report(6009, "@StepDefinitions only applies to classes");
	}

	@Override
	public void tcBefore(TCStatement stmt, Environment env, NameScope scope)
	{
		
		name.report(6009, "@StepDefinitions only applies to classes");
	}

    @Override
	public void tcAfter(TCClassDefinition clazz){
        //StepDefinition test = new StepDefinition();
        //test.AfterCleanup(clazz);
	}


   

}
