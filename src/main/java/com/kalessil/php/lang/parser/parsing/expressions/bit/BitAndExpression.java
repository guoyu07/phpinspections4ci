package com.kalessil.php.lang.parser.parsing.expressions.bit;

import com.kalessil.php.lang.lexer.PhpTokenTypes;
import com.kalessil.php.lang.parser.PhpElementTypes;
import com.kalessil.php.lang.parser.parsing.expressions.AssignmentExpression;
import com.kalessil.php.lang.parser.parsing.expressions.comparition.EqualityExpression;
import com.kalessil.php.lang.parser.util.PhpParserErrors;
import com.kalessil.php.lang.parser.util.PhpPsiBuilder;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;

/**
 * Created by IntelliJ IDEA.
 * User: markov
 * Date: 15.12.2007
 */
public class BitAndExpression implements PhpTokenTypes
{

	public static IElementType parse(PhpPsiBuilder builder)
	{
		PsiBuilder.Marker marker = builder.mark();
		IElementType result = EqualityExpression.parse(builder);
		if(result != PhpElementTypes.EMPTY_INPUT)
		{
			if(builder.compareAndEat(opBIT_AND))
			{
				result = AssignmentExpression.parseWithoutPriority(builder);
				if(result == PhpElementTypes.EMPTY_INPUT)
				{
					result = EqualityExpression.parse(builder);
				}
				if(result == PhpElementTypes.EMPTY_INPUT)
				{
					builder.error(PhpParserErrors.expected("expression"));
				}
				PsiBuilder.Marker newMarker = marker.precede();
				marker.done(PhpElementTypes.BIT_EXPRESSION);
				result = PhpElementTypes.BIT_EXPRESSION;
				if(builder.compareAndEat(opBIT_AND))
				{
					subParse(builder, newMarker);
				}
				else
				{
					newMarker.drop();
				}
			}
			else
			{
				marker.drop();
			}
		}
		else
		{
			marker.drop();
		}
		return result;
	}

	private static IElementType subParse(PhpPsiBuilder builder, PsiBuilder.Marker marker)
	{
		IElementType result = AssignmentExpression.parseWithoutPriority(builder);
		if(result == PhpElementTypes.EMPTY_INPUT)
		{
			result = EqualityExpression.parse(builder);
		}
		if(result == PhpElementTypes.EMPTY_INPUT)
		{
			builder.error(PhpParserErrors.expected("expression"));
		}
		PsiBuilder.Marker newMarker = marker.precede();
		marker.done(PhpElementTypes.BIT_EXPRESSION);
		if(builder.compareAndEat(opBIT_AND))
		{
			subParse(builder, newMarker);
		}
		else
		{
			newMarker.drop();
		}
		return PhpElementTypes.BIT_EXPRESSION;
	}
}
