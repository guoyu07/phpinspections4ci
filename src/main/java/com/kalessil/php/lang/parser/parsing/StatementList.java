package com.kalessil.php.lang.parser.parsing;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.containers.HashMap;
import com.kalessil.php.lang.lexer.PhpTokenTypes;
import com.kalessil.php.lang.parser.PhpElementTypes;
import com.kalessil.php.lang.parser.parsing.classes.ClassConstant;
import com.kalessil.php.lang.parser.parsing.classes.ClassDeclaration;
import com.kalessil.php.lang.parser.parsing.classes.ClassReference;
import com.kalessil.php.lang.parser.parsing.functions.Function;
import com.kalessil.php.lang.parser.parsing.statements.EchoStatement;
import com.kalessil.php.lang.parser.util.PhpParserErrors;
import com.kalessil.php.lang.parser.util.PhpPsiBuilder;

/**
 * User: markov
 * User: kalessil
 */
public class StatementList implements PhpTokenTypes
{
	static final HashMap<IElementType, TokenSet> TS_CACHE = new HashMap<>();

	//	statement_list:
	//		statement_list top_statement
	//		| /* empty */
	//	;
	//
	//	top_statement:
	//		statement
	//		| function_declaration_statement
	//		| class_declaration_statement
	//	;
	public static void parse(PhpPsiBuilder builder, IElementType endDelimiter)
	{
		TokenSet whereToStop = TS_CACHE.get(endDelimiter);
        if (whereToStop == null) {
            whereToStop = TokenSet.create(endDelimiter);
            TS_CACHE.put(endDelimiter, whereToStop);
        }
        parse(builder, whereToStop);
	}

	public static void parse(PhpPsiBuilder builder, TokenSet whereToStop)
	{
		PsiBuilder.Marker statementList = builder.mark();
		if (whereToStop == TokenSet.EMPTY) {
			boolean braced = builder.compareAndEat(PhpTokenTypes.chLBRACE);
			int balance = braced ? 1 : 0;
			IElementType tokenType = builder.getTokenType();

			while(balance != 0 || !whereToStop.contains(tokenType)) {
				if (tokenType != PhpTokenTypes.chLBRACE && tokenType != PhpTokenTypes.DOLLAR_LBRACE) {
					if (tokenType == PhpTokenTypes.chRBRACE) {
						--balance;
						if (braced && balance == 0 || balance < 0) {
							break;
						}
					}
				} else {
					++balance;
				}

				builder.advanceLexer();
				tokenType = builder.getTokenType();
				if (tokenType == null) {
					break;
				}
			}

			if (braced) {
				builder.match(PhpTokenTypes.chRBRACE);
			}
		} else {
			if (!whereToStop.contains(builder.getTokenType())) {
				while (true) {
					final int previous = builder.getCurrentOffset();
					if (!parseTopStatement(builder)) {
						final IElementType tokenType = builder.getTokenType();
						if (tokenType != null) {
							builder.error("Unexpected token: " + tokenType);
							builder.advanceLexer();
						}
					}

					if (builder.eof() || whereToStop.contains(builder.getTokenType())) {
						break;
					}

					if (previous == builder.getCurrentOffset()) {
						builder.error(PhpParserErrors.unexpected(builder.getTokenType()));
						builder.advanceLexer();
					}
				}
			}
		}

		statementList.collapse(PhpElementTypes.GROUP_STATEMENT);
	}

	public static boolean parseTopStatement(PhpPsiBuilder builder)
	{
        IElementType parsed = PhpElementTypes.EMPTY_INPUT;
        if (builder.compare(PhpTokenTypes.PHP_OPENING_TAG)) {
            builder.advanceLexer();
        } else if (builder.compareAndEat(PhpTokenTypes.PHP_ECHO_OPENING_TAG)) {
            PsiBuilder.Marker echo = builder.mark();
            parsed = EchoStatement.parse(builder);
            echo.done(PhpElementTypes.ECHO);
        }

        if (parsed == PhpElementTypes.EMPTY_INPUT) {
            parsed = Function.parse(builder);
        }

        if(builder.getTokenType() == NAMESPACE_KEYWORD) {
			parseNamespaceStatement(builder);
		}

		while(builder.getTokenType() == USE_KEYWORD) {
			parseUseStatement(builder);
		}

		if(parsed == PhpElementTypes.EMPTY_INPUT) {
			parsed = ClassConstant.parse(builder);
		}

        if (parsed == PhpElementTypes.EMPTY_INPUT) {
            parsed = ClassDeclaration.parse(builder);
        }

        if (parsed == PhpElementTypes.EMPTY_INPUT && !builder.eof()) {
            parsed = Statement.parse(builder);
        }

        if (builder.compare(PhpTokenTypes.PHP_CLOSING_TAG)) {
            builder.advanceLexer();
            if (builder.compare(PhpTokenTypes.PHP_OPENING_TAG)) {
                builder.advanceLexer();
            }
        }

        return parsed != PhpElementTypes.EMPTY_INPUT;
	}

	private static void parseNamespaceStatement(PhpPsiBuilder builder)
	{
		PsiBuilder.Marker marker = builder.mark();

		builder.match(NAMESPACE_KEYWORD);

		if(ClassReference.parseClassNameReference(builder, null, false, false, false) == null)
		{
			builder.error("Namespace expected");
		}

		builder.match(opSEMICOLON);
		marker.done(PhpElementTypes.NAMESPACE_STATEMENT);
	}

	private static void parseUseStatement(PhpPsiBuilder builder)
	{
		PsiBuilder.Marker marker = builder.mark();

		builder.match(USE_KEYWORD);

		if(ClassReference.parseClassNameReference(builder, null, false, false, true) == null)
		{
			builder.error("Reference expected");
		}
		else
		{
			while(builder.getTokenType() == opCOMMA)
			{
				builder.advanceLexer();

				if(ClassReference.parseClassNameReference(builder, null, false, false, true) == null)
				{
					builder.error("Reference expected");
					break;
				}
			}
		}

		builder.match(opSEMICOLON);
		marker.done(PhpElementTypes.USE_STATEMENT);
	}
}
