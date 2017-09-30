package com.kalessil.php.lang.parser.parsing.classes;

import com.kalessil.php.lang.lexer.PhpTokenTypes;
import com.kalessil.php.lang.parser.PhpElementTypes;
import com.kalessil.php.lang.parser.parsing.expressions.StaticScalar;
import com.kalessil.php.lang.parser.util.ListParsingHelper;
import com.kalessil.php.lang.parser.util.ParserPart;
import com.kalessil.php.lang.parser.util.PhpPsiBuilder;
import com.intellij.psi.tree.IElementType;

/**
 * Created by IntelliJ IDEA.
 * User: markov
 * Date: 27.10.2007
 */
public class ClassField implements PhpTokenTypes
{

	//	variable_modifiers class_variable_declaration

	//	class_variable_declaration:
	//		class_variable_declaration ',' VARIABLE_REFERENCE
	//		| class_variable_declaration ',' VARIABLE_REFERENCE '=' static_scalar
	//		| VARIABLE_REFERENCE
	//		| VARIABLE_REFERENCE '=' static_scalar
	//	;
	public static IElementType parse(PhpPsiBuilder builder, final boolean constValue)
	{
		ParserPart fieldParser = new ParserPart()
		{
			@Override
			public IElementType parse(PhpPsiBuilder builder)
			{
				IElementType type = constValue ? IDENTIFIER : VARIABLE;
				if(!builder.compare(type))
				{
					return PhpElementTypes.EMPTY_INPUT;
				}

				builder.match(type);
				if(builder.compareAndEat(opASGN))
				{
					StaticScalar.parse(builder);
				}

				return PhpElementTypes.CLASS_FIELD;
			}
		};
		IElementType result = fieldParser.parse(builder);
		if(result == PhpElementTypes.EMPTY_INPUT)
		{
			return PhpElementTypes.EMPTY_INPUT;
		}
		ListParsingHelper.parseCommaDelimitedExpressionWithLeadExpr(builder, result, fieldParser, false);
		builder.match(opSEMICOLON);
		return result;
	}

}
