package com.kalessil.php.lang.parser;

import com.intellij.openapi.project.Project;
import com.kalessil.php.PhpLanguageLevel;
import com.kalessil.php.lang.documentation.phpdoc.lexer.PhpDocTokenTypes;
import com.kalessil.php.lang.lexer.PhpFlexAdapter;
import com.kalessil.php.lang.lexer.PhpTokenTypes;
import com.kalessil.php.lang.psi.PhpStubElements;
import com.kalessil.php.lang.psi.impl.PhpFileImpl;
import org.jetbrains.annotations.NotNull;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;

/**
 * @author jay
 * @author kalessil
 */
public class PhpParserDefinition implements ParserDefinition
{
	private static final TokenSet WHITESPACE_TOKENS;
	static {
        WHITESPACE_TOKENS = TokenSet.create(PhpTokenTypes.WHITE_SPACE, PhpDocTokenTypes.DOC_WHITESPACE);
    }

	@NotNull
    public TokenSet getWhitespaceTokens() {
		return WHITESPACE_TOKENS;
	}

	@NotNull
    public Lexer createLexer(@NotNull Project project) {
		return new PhpFlexAdapter(PhpLanguageLevel.PHP_5_6);
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return PhpElementTypes.tsCOMMENTS;
	}

	@Override
	@NotNull
	public PsiParser createParser(@NotNull Project project)
	{
		return new PhpPsiParser();
	}

	@Override
	@NotNull
	public IFileElementType getFileNodeType()
	{
		return PhpStubElements.FILE;
	}

	@Override
	@NotNull
	public TokenSet getStringLiteralElements()
	{
		return PhpTokenTypes.tsSTRINGS;
	}

	@Override
	@NotNull
	public PsiElement createElement(ASTNode node)
	{
		return PhpPsiCreator.create(node);
	}

	@Override
	public PsiFile createFile(FileViewProvider viewProvider)
	{
		return new PhpFileImpl(viewProvider);
	}

	@NotNull
	@Override
	public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right)
	{
		return SpaceRequirements.MAY;
	}
}
