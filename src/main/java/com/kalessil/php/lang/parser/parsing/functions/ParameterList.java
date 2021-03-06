package com.kalessil.php.lang.parser.parsing.functions;

import com.kalessil.php.lang.lexer.PhpTokenTypes;
import com.kalessil.php.lang.parser.PhpElementTypes;
import com.kalessil.php.lang.parser.parsing.classes.ClassReference;
import com.kalessil.php.lang.parser.parsing.expressions.StaticScalar;
import com.kalessil.php.lang.parser.util.ListParsingHelper;
import com.kalessil.php.lang.parser.util.ParserPart;
import com.kalessil.php.lang.parser.util.PhpParserErrors;
import com.kalessil.php.lang.parser.util.PhpPsiBuilder;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;

/**
 * @author markov
 * @date 14.10.2007
 */
public class ParameterList implements PhpTokenTypes
{

	//	parameter_list:
	//		non_empty_parameter_list
	//		| /* empty */
	//	;
	//

	public static IElementType parse(PhpPsiBuilder builder)
	{
		PsiBuilder.Marker parameterList = builder.mark();
		builder.match(chLPAREN);
		ParserPart parameterParser = new Parameter();
		int result = ListParsingHelper.parseCommaDelimitedExpressionWithLeadExpr(builder, parameterParser.parse(builder), parameterParser, false);
		builder.match(chRPAREN);
		parameterList.done(PhpElementTypes.PARAMETER_LIST);
		return (result > 0) ? PhpElementTypes.PARAMETER_LIST : PhpElementTypes.EMPTY_INPUT;
	}

	//	non_empty_parameter_list:
	//		optional_class_type VARIABLE_REFERENCE
	//		| optional_class_type '&' VARIABLE_REFERENCE
	//		| optional_class_type '&' VARIABLE_REFERENCE '=' static_scalar
	//		| optional_class_type VARIABLE_REFERENCE '=' static_scalar
	//		| non_empty_parameter_list ',' optional_class_type VARIABLE_REFERENCE
	//		| non_empty_parameter_list ',' optional_class_type '&' VARIABLE_REFERENCE
	//		| non_empty_parameter_list ',' optional_class_type '&' VARIABLE_REFERENCE '=' static_scalar
	//		| non_empty_parameter_list ',' optional_class_type VARIABLE_REFERENCE '=' static_scalar
	//	;
	//
	//	optional_class_type:
	//		/* empty */
	//		| IDENTIFIER
	//		| kwARRAY
	//	;

	/*
		non_empty_parameter_list:
			parameter | non_empty_parameter_list ',' parameter
		;

		parameter:
			optional_class_type [opBIT_AND] VARIABLE_REFERENCE [opASGN static_scalar]
		;
	 */
	private static class Parameter implements ParserPart
	{

		@Override
		public IElementType parse(PhpPsiBuilder builder)
		{
			PsiBuilder.Marker parameter = builder.mark();

			if(!builder.compareAndEat(kwARRAY))
			{
				ClassReference.parse(builder);
			}

			builder.compareAndEat(opBIT_AND);
			if(!builder.compareAndEat(VARIABLE))
			{
				parameter.rollbackTo();
				return PhpElementTypes.EMPTY_INPUT;
			}
			if(builder.compare(opASGN))
			{
				PsiBuilder.Marker defaultValue = builder.mark();
				builder.advanceLexer();
				if(StaticScalar.parse(builder) == PhpElementTypes.EMPTY_INPUT)
				{
					builder.error(PhpParserErrors.expected("default value"));
				}
				defaultValue.done(PhpElementTypes.PARAMETER_DEFAULT_VALUE);
			}
			parameter.done(PhpElementTypes.PARAMETER);
			return PhpElementTypes.PARAMETER;
		}
	}

}
