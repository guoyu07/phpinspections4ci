package com.kalessil.php.lang.parser.parsing.statements;

import com.kalessil.php.lang.lexer.PhpTokenTypes;
import com.kalessil.php.lang.parser.PhpElementTypes;
import com.kalessil.php.lang.parser.parsing.Statement;
import com.kalessil.php.lang.parser.parsing.expressions.Expression;
import com.kalessil.php.lang.parser.util.PhpPsiBuilder;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;

/**
 * Created by IntelliJ IDEA.
 * User: markov
 * Date: 01.11.2007
 */
public class DoWhileStatement implements PhpTokenTypes
{

	//		kwDO statement kwWHILE '(' expr ')' ';'
	public static IElementType parse(PhpPsiBuilder builder)
	{
		PsiBuilder.Marker statement = builder.mark();
		if(!builder.compareAndEat(kwDO))
		{
			statement.drop();
			return PhpElementTypes.EMPTY_INPUT;
		}
		Statement.parse(builder);
		builder.match(kwWHILE);
		builder.match(chLPAREN);
		Expression.parse(builder);
		builder.match(chRPAREN);
		if(!builder.compare(PHP_CLOSING_TAG))
		{
			builder.match(opSEMICOLON);
		}
		statement.done(PhpElementTypes.DO_WHILE);
		return PhpElementTypes.DO_WHILE;
	}
}
