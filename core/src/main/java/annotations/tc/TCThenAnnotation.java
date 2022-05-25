package annotations.tc;

import com.bdm.AnnotationTypes;
import com.bdm.StepDefinitionBuilder;
import com.fujitsu.vdmj.tc.annotations.TCAnnotation;
import com.fujitsu.vdmj.tc.definitions.TCClassDefinition;
import com.fujitsu.vdmj.tc.definitions.TCDefinition;
import com.fujitsu.vdmj.tc.expressions.TCExpression;
import com.fujitsu.vdmj.tc.expressions.TCExpressionList;
import com.fujitsu.vdmj.tc.lex.TCIdentifierToken;
import com.fujitsu.vdmj.tc.modules.TCModule;
import com.fujitsu.vdmj.tc.statements.TCStatement;
import com.fujitsu.vdmj.tc.types.TCType;
import com.fujitsu.vdmj.typechecker.Environment;
import com.fujitsu.vdmj.typechecker.NameScope;




public class TCThenAnnotation extends TCAnnotation
{
	private static final long serialVersionUID = 1L;

	public TCThenAnnotation(TCIdentifierToken name, TCExpressionList args)
	{
		super(name, args);
	}

	public static void doInit()
	{
	}
	@Override
	public void tcBefore(TCDefinition def, Environment env, NameScope scope)
	{
		StepDefinitionBuilder stepDefinitionBuilder = new StepDefinitionBuilder();
		stepDefinitionBuilder.BuildStepDefinitionMethod(def, AnnotationTypes.Then, args.elementAt(1),args.elementAt(0));
	
		
	}
	

	@Override
	public void tcBefore(TCModule module)
	{
		name.report(6009, "@Then only applies to definitions");
	}

	@Override
	public void tcBefore(TCClassDefinition clazz)
	{
		name.report(6009, "@Then only applies to definitions");

	}

	@Override
	public void tcBefore(TCExpression exp, Environment env, NameScope scope)
	{
		name.report(6009, "@Then only applies to definitions");
	}

	@Override
	public void tcBefore(TCStatement stmt, Environment env, NameScope scope)
	{
		name.report(6009, "@Then only applies to definitions");
	}

	@Override
	public void tcAfter(TCDefinition def, TCType type, Environment env, NameScope scope)
	{
		
	}

	

	

}
