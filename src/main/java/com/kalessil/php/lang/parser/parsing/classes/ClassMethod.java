package com.kalessil.php.lang.parser.parsing.classes;

import com.kalessil.php.lang.lexer.PhpTokenTypes;
import com.kalessil.php.lang.parser.parsing.StatementList;
import com.kalessil.php.lang.parser.parsing.functions.IsReference;
import com.kalessil.php.lang.parser.parsing.functions.ParameterList;
import com.kalessil.php.lang.parser.util.PhpPsiBuilder;
import com.kalessil.php.lang.psi.PhpStubElements;
import com.intellij.psi.tree.IElementType;

/**
 * Created by IntelliJ IDEA.
 * User: markov
 * Date: 27.10.2007
 */
public class ClassMethod implements PhpTokenTypes
{

	public static IElementType parseFunction(PhpPsiBuilder builder)
	{
		builder.match(kwFUNCTION);
		IsReference.parse(builder);
		builder.match(IDENTIFIER);

		ParameterList.parse(builder);

		if(builder.getTokenType() == opSEMICOLON)
		{
			builder.advanceLexer();
			return PhpStubElements.FUNCTION;
		}
		builder.match(chLBRACE);
		StatementList.parse(builder, chRBRACE);
		builder.match(chRBRACE);

		builder.compareAndEat(opSEMICOLON);

		return PhpStubElements.FUNCTION;
	}
}
