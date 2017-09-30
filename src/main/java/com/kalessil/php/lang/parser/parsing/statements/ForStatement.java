package com.kalessil.php.lang.parser.parsing.statements;

import com.kalessil.php.lang.lexer.PhpTokenTypes;
import com.kalessil.php.lang.parser.PhpElementTypes;
import com.kalessil.php.lang.parser.parsing.Statement;
import com.kalessil.php.lang.parser.parsing.StatementList;
import com.kalessil.php.lang.parser.parsing.expressions.Expression;
import com.kalessil.php.lang.parser.util.ListParsingHelper;
import com.kalessil.php.lang.parser.util.ParserPart;
import com.kalessil.php.lang.parser.util.PhpPsiBuilder;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;

/**
 * Created by IntelliJ IDEA.
 * User: markov
 * Date: 01.11.2007
 */
public class ForStatement implements PhpTokenTypes
{

	//	kwFOR '('
	//		for_expr ';'
	//		for_expr ';'
	//		for_expr ')' for_statement
	public static IElementType parse(PhpPsiBuilder builder)
	{
		PsiBuilder.Marker statement = builder.mark();
		if(!builder.compareAndEat(kwFOR))
		{
			statement.drop();
			return PhpElementTypes.FOR;
		}
		builder.match(chLPAREN);

		// initial expression
		parseForExpression(builder);
		builder.match(opSEMICOLON);

		// conditional expression
		parseForExpression(builder);
		builder.match(opSEMICOLON);

		// repeated expression
		parseForExpression(builder);

		builder.match(chRPAREN);
		parseForStatement(builder);
		statement.done(PhpElementTypes.FOR);
		return PhpElementTypes.FOR;
	}

	//	for_statement:
	//		statement
	//		| ':' statement_list kwENDFOR ';'
	//	;
	private static void parseForStatement(PhpPsiBuilder builder)
	{
		if(builder.compareAndEat(opCOLON))
		{
			StatementList.parse(builder, kwENDFOR);
			builder.match(kwENDFOR);
			if(!builder.compare(PHP_CLOSING_TAG))
			{
				builder.match(opSEMICOLON);
			}
		}
		else
		{
			Statement.parse(builder);
		}
	}

	//	for_expr:
	//		/* empty */
	//		| non_empty_for_expr
	//	;
	//
	//	non_empty_for_expr:
	//		non_empty_for_expr ',' expr
	//		| expr
	//	;
	private static void parseForExpression(PhpPsiBuilder builder)
	{
		ParserPart parserPart = new ParserPart()
		{
			@Override
			public IElementType parse(PhpPsiBuilder builder)
			{
				return Expression.parse(builder);
			}
		};
		ListParsingHelper.parseCommaDelimitedExpressionWithLeadExpr(builder, parserPart.parse(builder), parserPart, false);
	}
}
