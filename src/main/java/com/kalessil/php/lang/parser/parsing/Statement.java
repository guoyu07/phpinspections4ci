package com.kalessil.php.lang.parser.parsing;

import com.kalessil.php.lang.lexer.PhpTokenTypes;
import com.kalessil.php.lang.parser.PhpElementTypes;
import com.kalessil.php.lang.parser.parsing.expressions.Expression;
import com.kalessil.php.lang.parser.parsing.statements.*;
import com.kalessil.php.lang.parser.util.PhpParserErrors;
import com.kalessil.php.lang.parser.util.PhpPsiBuilder;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.kalessil.php.lang.parser.parsing.statements.ForStatement;

/**
 * @author markov
 * @author kalessil
 */
public class Statement implements PhpTokenTypes
{

	//	statement:
	//		'{' statement_list '}'
	//		| kwIF '(' expr ')' statement elseif_list else_single
	//		| kwIF '(' expr ')' ':' statement_list new_elseif_list new_else_single kwENDIF ';'
	//		| kwWHILE '(' expr ')' while_statement
	//		| kwDO statement kwWHILE '(' expr ')' ';'
	//		| kwFOR '('
	//			for_expr ';'
	//			for_expr ';'
	//			for_expr ')' for_statement
	//		| kwSWITCH '(' expr ')' switch_case_list
	//		| kwBREAK ';'
	//		| kwBREAK expr ';'
	//		| kwCONTINUE ';'
	//		| kwCONTINUE expr ';'
	//		| kwRETURN ';'
	//		| kwRETURN expr ';'
	//		| kwGLOBAL global_var_list ';'
	//		| STATIC_KEYWORD static_var_list ';'
	//		| kwECHO echo_expr_list ';'
	//		| HTML
	//		| expr ';'
	//		| kwUNSET '(' unset_variables ')' ';'
	//		| kwFOREACH '(' variable kwAS
	//			foreach_variable foreach_optional_arg ')'
	//			foreach_statement
	//		| kwFOREACH '(' expr_without_variable kwAS
	//			variable foreach_optional_arg ')'
	//			foreach_statement
	//		| kwDECLARE '(' declare_list ')' declare_statement
	//		| ';' /* empty statement */
	//		| kwTRY '{' statement_list '}'
	//			non_empty_catch_clauses
	//		| kwTHROW expr ';'
	//	;
	public static IElementType parse(PhpPsiBuilder builder)
	{
		//		'{' statement_list '}'
        if (builder.compare(PhpTokenTypes.chLBRACE)) {
            StatementList.parse(builder, TokenSet.EMPTY);
            return PhpElementTypes.GROUP_STATEMENT;
        }
        //		HTML
        else if(builder.compare(TokenSet.create(HTML, PHP_CLOSING_TAG))) {
			builder.compareAndEat(PHP_CLOSING_TAG);
			final PsiBuilder.Marker html = builder.mark();
			if(builder.compareAndEat(HTML))
			{
				html.done(PhpElementTypes.HTML);
			}
			else
			{
				html.drop();
			}
			builder.compareAndEat(TokenSet.create(PHP_OPENING_TAG, PHP_ECHO_OPENING_TAG));
			return PhpElementTypes.HTML;
		}
		//		';' /* empty statement */
		else if (builder.compare(PhpTokenTypes.opSEMICOLON)) {
            final PsiBuilder.Marker statement = builder.mark();
            builder.advanceLexer();
            statement.done(PhpElementTypes.STATEMENT);
            return PhpElementTypes.STATEMENT;
        }
        //		expr ';'
        else {
            IElementType result = parseStatementByKeyword(builder);
            if (result == PhpElementTypes.EMPTY_INPUT) {
                PsiBuilder.Marker statement = builder.mark();
                result = Expression.parse(builder);
                if (result != PhpElementTypes.EMPTY_INPUT) {
                    if (builder.getTokenType() != PhpTokenTypes.PHP_CLOSING_TAG) {
                        builder.match(PhpTokenTypes.opSEMICOLON);
                    }

                    statement.done(PhpElementTypes.STATEMENT);
                } else {
                    statement.drop();
                    builder.error(PhpParserErrors.expected("statement"));
                }
            }

            return result;
        }
	}

	//	statement:
	//		kwIF '(' expr ')' statement elseif_list else_single
	//		| kwIF '(' expr ')' ':' statement_list new_elseif_list new_else_single kwENDIF ';'
	//		| kwWHILE '(' expr ')' while_statement
	//		| kwDO statement kwWHILE '(' expr ')' ';'
	//		| kwFOR '('
	//			for_expr ';'
	//			for_expr ';'
	//			for_expr ')' for_statement
	//		| kwSWITCH '(' expr ')' switch_case_list
	//		| kwBREAK ';'
	//		| kwBREAK expr ';'
	//		| kwCONTINUE ';'
	//		| kwCONTINUE expr ';'
	//		| kwRETURN ';'
	//		| kwRETURN expr ';'
	//		| kwGLOBAL global_var_list ';'
	//		| STATIC_KEYWORD static_var_list ';'
	//		| kwECHO echo_expr_list ';'
	//		| kwUNSET '(' unset_variables ')' ';'
	//		| kwFOREACH '(' variable kwAS
	//			foreach_variable foreach_optional_arg ')'
	//			foreach_statement
	//		| kwFOREACH '(' expr_without_variable kwAS
	//			variable foreach_optional_arg ')'
	//			foreach_statement
	//		| kwDECLARE '(' declare_list ')' declare_statement
	//		| kwTRY '{' statement_list '}'
	//			non_empty_catch_clauses
	//		| kwTHROW expr ';'
	//	;
	private static IElementType parseStatementByKeyword(PhpPsiBuilder builder)
	{
        if (!builder.compare(tsSTATEMENT_FIRST_TOKENS)) {
            return PhpElementTypes.EMPTY_INPUT;
        } else if (builder.compare(kwIF)) {
            return IfStatement.parse(builder);
        } else if (builder.compare(kwWHILE)) {
            return WhileStatement.parse(builder);
        } else if (builder.compare(kwDO)) {
            return DoWhileStatement.parse(builder);
        } else if (builder.compare(kwFOR)) {
            return ForStatement.parse(builder);
        } else if (builder.compare(kwSWITCH)) {
            return SwitchStatement.parse(builder);
        } else if (builder.compare(kwBREAK)) {
            return BreakStatement.parse(builder);
        } else if (builder.compare(kwCONTINUE)) {
            return ContinueStatement.parse(builder);
        } else if (builder.compare(kwRETURN)) {
            return ReturnStatement.parse(builder);
        } else if (builder.compare(kwGLOBAL)) {
            return GlobalStatement.parse(builder);
        } else if (builder.compare(STATIC_KEYWORD)) {
            return StaticStatement.parse(builder);
        } else if (builder.compare(kwECHO)) {
            return EchoStatement.parse(builder);
        } else if (builder.compare(kwUNSET)) {
            return UnsetStatement.parse(builder);
        } else if (builder.compare(kwFOREACH)) {
            return ForeachStatement.parse(builder);
        } else if (builder.compare(kwDECLARE)) {
            return DeclareStatement.parse(builder);
        } else if (builder.compare(kwTRY)) {
            return TryStatement.parse(builder);
        } else if (builder.compare(kwTHROW)) {
            return ThrowStatement.parse(builder);
        } else {
            return PhpElementTypes.EMPTY_INPUT;
        }
    }
}
