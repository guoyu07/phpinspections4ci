package com.kalessil.php.lang.parser.parsing.classes;

import com.kalessil.php.lang.lexer.PhpTokenTypes;
import com.kalessil.php.lang.parser.PhpElementTypes;
import com.kalessil.php.lang.parser.util.PhpParserErrors;
import com.kalessil.php.lang.parser.util.PhpPsiBuilder;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;

/**
 * Created by IntelliJ IDEA.
 * User: markov
 * Date: 26.10.2007
 */
public class ClassStatementList implements PhpTokenTypes
{

	//	class_statement_list:
	//		class_statement_list class_statement
	//		| /* empty */
	//	;
	public static void parse(PhpPsiBuilder builder)
	{
		while(!builder.eof() && !builder.compare(chRBRACE))
		{
			IElementType result = parseStatement(builder);
			if(result == PhpElementTypes.EMPTY_INPUT)
			{
				builder.error(PhpParserErrors.unexpected(builder.getTokenType()));
				builder.advanceLexer();
			}
		}
	}

	//	class_statement:
	//		variable_modifiers class_variable_declaration ';'
	//		| class_constant_declaration ';'
	//		| method_modifiers kwFUNCTION is_reference IDENTIFIER
	//			'(' parameter_list ')' method_body
	//	;
	private static IElementType parseStatement(PhpPsiBuilder builder)
	{
		PsiBuilder.Marker mark = builder.mark();

		ClassMemberModifiers.parseModifiers(builder);

		IElementType elementType = null;

		IElementType tokenType = builder.getTokenType();
		if(tokenType == kwFUNCTION)
		{
			elementType = ClassMethod.parseFunction(builder);
		}
		else
		{
			if(tokenType == kwVAR || tokenType == kwCONST)
			{
				builder.advanceLexer();
			}

			elementType = ClassField.parse(builder, tokenType == kwCONST);
		}

		if(elementType != PhpElementTypes.EMPTY_INPUT)
		{
			mark.done(elementType);
		}
		else
		{
			mark.drop();
			builder.error("'function' expected");
		}
		return elementType;
	}

}
