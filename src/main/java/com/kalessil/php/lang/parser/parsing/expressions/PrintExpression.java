package com.kalessil.php.lang.parser.parsing.expressions;

import com.kalessil.php.lang.lexer.PhpTokenTypes;
import com.kalessil.php.lang.parser.PhpElementTypes;
import com.kalessil.php.lang.parser.parsing.expressions.bit.BitOrExpression;
import com.kalessil.php.lang.parser.util.PhpParserErrors;
import com.kalessil.php.lang.parser.util.PhpPsiBuilder;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;

/**
 * @author jay
 * @time 20.12.2007 23:25:11
 */
public class PrintExpression implements PhpTokenTypes
{

	public static IElementType parse(PhpPsiBuilder builder)
	{
		PsiBuilder.Marker marker = builder.mark();
		if(builder.compareAndEat(kwPRINT))
		{
			IElementType result = parse(builder);
			if(result == PhpElementTypes.EMPTY_INPUT)
			{
				builder.error(PhpParserErrors.expected("expression"));
			}
			marker.done(PhpElementTypes.PRINT_EXPRESSION);
			return PhpElementTypes.PRINT_EXPRESSION;
		}
		else
		{
			marker.drop();
			IElementType result = AssignmentExpression.parseWithoutPriority(builder);
			if(result == PhpElementTypes.EMPTY_INPUT)
			{
				result = BitOrExpression.parse(builder);
			}
			return result;
		}
	}

}
