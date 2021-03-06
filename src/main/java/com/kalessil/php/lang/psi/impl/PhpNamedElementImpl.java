package com.kalessil.php.lang.psi.impl;

import com.kalessil.php.lang.documentation.phpdoc.psi.PhpDocComment;
import com.kalessil.php.lang.lexer.PhpTokenTypes;
import com.kalessil.php.lang.psi.PhpConstantReference;
import com.kalessil.php.lang.psi.PhpElement;
import com.kalessil.php.lang.psi.PhpNamedElement;
import com.kalessil.php.lang.psi.PhpPsiElementFactory;
import com.kalessil.php.lang.psi.resolve.types.PhpType;
import com.kalessil.php.lang.psi.resolve.types.PhpTypeAnnotatorVisitor;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;

/**
 * @author jay
 * @date Jun 4, 2008 11:38:51 AM
 */
abstract public class PhpNamedElementImpl extends PhpElementImpl implements PhpNamedElement
{

	public PhpNamedElementImpl(ASTNode node)
	{
		super(node);
	}

	@Override
	public PsiElement setName(@NonNls @NotNull String name) throws IncorrectOperationException
	{
		PsiElement nameIdentifier = getNameIdentifier();
		//noinspection ConstantConditions
		if(nameIdentifier != null && !getName().equals(name))
		{
			final PhpConstantReference constantReference = PhpPsiElementFactory.createConstantReference(getProject(), name);
			nameIdentifier.replace(constantReference.getNameIdentifier());
		}
		return this;
	}

	@Nullable
	@Override
	public PsiElement getNameIdentifier()
	{
		return findChildByType(PhpTokenTypes.IDENTIFIER);
	}

	@Override
	public String getName()
	{
		PsiElement nameNode = getNameIdentifier();
		if(nameNode != null)
		{
			return nameNode.getText();
		}
		return null;
	}

	@Override
	public int getTextOffset()
	{
		PsiElement nameNode = getNameIdentifier();
		if(nameNode != null)
		{
			return nameNode.getNode().getStartOffset();
		}
		return super.getTextOffset();
	}

	@Override
	public PhpDocComment getDocComment()
	{
		final PhpElement element = getPrevPsiSibling();
		if(element instanceof PhpDocComment)
		{
			return (PhpDocComment) element;
		}
		return null;
	}

	@NotNull
	public PhpType getType()
	{
		PhpType type = getUserData(PhpTypeAnnotatorVisitor.TYPE_KEY);
		if(type == null)
		{
			PhpTypeAnnotatorVisitor.process(this);
		}
		type = getUserData(PhpTypeAnnotatorVisitor.TYPE_KEY);
		assert type != null;
		return type;
	}
}
