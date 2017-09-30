package com.kalessil.php.lang.documentation.phpdoc.parser;

import org.jetbrains.annotations.NotNull;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilderFactory;
import com.intellij.lang.PsiParser;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.ILazyParseableElementType;
import com.kalessil.php.lang.PhpFileType;
import com.kalessil.php.lang.documentation.phpdoc.lexer.PhpDocLexer;
import com.kalessil.php.lang.documentation.phpdoc.lexer.PhpDocTokenTypes;
import com.kalessil.php.lang.documentation.phpdoc.psi.PhpDocElementType;

/**
 * @author jay
 * @date Jun 26, 2008 10:12:07 PM
 */
public interface PhpDocElementTypes extends PhpDocTokenTypes
{

	final public ILazyParseableElementType DOC_COMMENT = new ILazyParseableElementType("PhpDocComment")
	{
		@Override
		@NotNull
		public Language getLanguage()
		{
			return PhpFileType.INSTANCE.getLanguage();
		}

		public ASTNode parseContents(ASTNode chameleon) {
            PsiBuilderFactory factory = PsiBuilderFactory.getInstance();
            PsiElement parentElement = chameleon.getTreeParent().getPsi();
            PsiBuilder builder = factory.createBuilder(parentElement.getProject(), chameleon, new PhpDocLexer(), this.getLanguage(), chameleon.getText());
            PsiParser parser = new PhpDocParser();
            return parser.parse(this, builder).getFirstChildNode();
        }
	};

	final public PhpDocElementType phpDocText = new PhpDocElementType("PhpDocText");
	final public PhpDocElementType phpDocTag = new PhpDocElementType("PhpDocTag");
	final public PhpDocElementType phpDocInlineTag = new PhpDocElementType("PhpDocInlineTag");
	final public PhpDocElementType phpDocTagValue = new PhpDocElementType("PhpDocTagValue");
	final public PhpDocElementType phpDocType = new PhpDocElementType("PhpDocType");
	final public PhpDocElementType phpDocVariable = new PhpDocElementType("PhpDocVariable");

}
