package com.kalessil.php.lang.documentation.phpdoc.parser;

import com.kalessil.php.lang.documentation.phpdoc.parser.tags.PhpDocDefaultTagParser;
import com.kalessil.php.lang.documentation.phpdoc.parser.tags.PhpDocReturnTagParser;
import com.kalessil.php.lang.documentation.phpdoc.parser.tags.PhpDocVarTagParser;
import com.kalessil.php.lang.parser.util.PhpPsiBuilder;
import org.jetbrains.annotations.NotNull;
import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;

/**
 * @author jay
 * @date Jun 28, 2008 4:38:44 PM
 */
public class PhpDocParser implements PsiParser
{

	public PhpDocParser()
	{
		PhpDocDefaultTagParser.register();
		PhpDocVarTagParser.register();
		PhpDocReturnTagParser.register();
	}

	@NotNull
	public ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder builder)
	{
		PhpPsiBuilder phpBuilder = new PhpPsiBuilder(builder);
		PsiBuilder.Marker rootMarker = phpBuilder.mark();
		new PhpDocParsing().parse(phpBuilder);
		rootMarker.done(root);
		return phpBuilder.getTreeBuilt();
	}
}
