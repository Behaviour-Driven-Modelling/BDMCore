package annotations.ast;

import com.fujitsu.vdmj.ast.annotations.ASTAnnotation;
import com.fujitsu.vdmj.ast.lex.LexIdentifierToken;

public class ASTStepDefinitionAnnotation  extends ASTAnnotation {

    private static final long serialVersionUID = 1L;

    public ASTStepDefinitionAnnotation(LexIdentifierToken name) {
        super(name);
    }
    public static void doInit()
	{
	}


}
