package com.kalessil.php.lang.parser.parsing;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import com.kalessil.php.lang.lexer.PhpTokenTypes;
import com.kalessil.php.lang.parser.PhpElementTypes;
import com.kalessil.php.lang.parser.util.PhpParserErrors;
import com.kalessil.php.lang.parser.util.PhpPsiBuilder;

/**
 * @author markov
 * @author kalessil
 */
public class Program
{
	//	start:
	//		statement_list
	//	;
	public static void parse(PhpPsiBuilder builder)
	{
        PsiBuilder.Marker statementList = builder.mark();
        if (builder.getTokenType() != PhpTokenTypes.PHP_CLOSING_TAG) {
            while(true) {
                int previous = builder.getCurrentOffset();
                if (!StatementList.parseTopStatement(builder)) {
                    IElementType tokenType = builder.getTokenType();
                    if (tokenType != null) {
                        builder.error("Unexpected token: " + tokenType);
                        builder.advanceLexer();
                    }
                }

                if (builder.eof() || builder.getTokenType() == PhpTokenTypes.PHP_CLOSING_TAG) {
                    break;
                }

                if (previous == builder.getCurrentOffset()) {
                    builder.error(PhpParserErrors.unexpected(builder.getTokenType()));
                    builder.advanceLexer();
                }
            }
        }

        statementList.done(PhpElementTypes.TOP_GROUP_STATEMENT);

        while(!builder.eof()) {
            builder.advanceLexer();
        }
	}
}
