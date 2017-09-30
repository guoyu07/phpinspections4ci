package com.kalessil.php.lang.parser.parsing.expressions.logical;

import com.kalessil.php.lang.lexer.PhpTokenTypes;
import com.kalessil.php.lang.parser.PhpElementTypes;
import com.kalessil.php.lang.parser.parsing.expressions.AssignmentExpression;
import com.kalessil.php.lang.parser.parsing.expressions.InstanceofExpression;
import com.kalessil.php.lang.parser.util.PhpPsiBuilder;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;

/**
 * @author jay
 * @time 16.12.2007 21:00:36
 */
public class LogicalNotExpression implements PhpTokenTypes
{

	public static IElementType parse(PhpPsiBuilder builder)
	{
		PsiBuilder.Marker marker = builder.mark();
		if(builder.compareAndEat(opNOT))
		{
			parse(builder);
			marker.done(PhpElementTypes.UNARY_EXPRESSION);
			return PhpElementTypes.UNARY_EXPRESSION;
		}
		else
		{
			marker.drop();
			IElementType result = AssignmentExpression.parseWithoutPriority(builder);
			if(result == PhpElementTypes.EMPTY_INPUT)
			{
				result = InstanceofExpression.parse(builder);
			}
			return result;
		}
	}
}
