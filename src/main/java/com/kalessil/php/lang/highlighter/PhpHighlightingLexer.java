package com.kalessil.php.lang.highlighter;

import com.intellij.lexer.Lexer;
import com.intellij.lexer.MergingLexerAdapter;
import com.kalessil.php.PhpLanguageLevel;
import com.kalessil.php.lang.documentation.phpdoc.lexer.PhpDocLexer;
import com.kalessil.php.lang.documentation.phpdoc.lexer.PhpDocTokenTypes;
import com.kalessil.php.lang.documentation.phpdoc.parser.PhpDocElementTypes;
import com.kalessil.php.lang.lexer.PhpFlexLexer;
import com.kalessil.php.lang.lexer.PhpStringLiteralLexer;
import com.kalessil.php.lang.lexer.PhpTokenTypes;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.LayeredLexer;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * @author jay
 * @author kalessil
 */
public class PhpHighlightingLexer extends LayeredLexer
{
	public PhpHighlightingLexer(@NotNull Lexer baseLexer) {
		super(baseLexer);

		registerSelfStoppingLayer(
			new LayeredLexer(new PhpDocLexer()),
			new IElementType[]{PhpTokenTypes.DOC_COMMENT},
			new IElementType[]{PhpDocTokenTypes.DOC_COMMENT_END}
		);

		registerLayer(
			new PhpStringLiteralLexer(PhpStringLiteralLexer.NO_QUOTE_CHAR, PhpTokenTypes.STRING_LITERAL, PhpStringLiteralLexer.TYPE_DOUBLE_QUOTE),
			PhpTokenTypes.STRING_LITERAL
		);
		registerLayer(
			new PhpStringLiteralLexer(PhpStringLiteralLexer.NO_QUOTE_CHAR, PhpTokenTypes.STRING_LITERAL_SINGLE_QUOTE, PhpStringLiteralLexer.TYPE_SINGLE_QUOTE),
			PhpTokenTypes.STRING_LITERAL_SINGLE_QUOTE
		);
    }
}