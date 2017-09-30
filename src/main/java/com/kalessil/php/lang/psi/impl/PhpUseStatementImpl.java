package com.kalessil.php.lang.psi.impl;

import com.kalessil.php.lang.psi.PhpClassReference;
import com.kalessil.php.lang.psi.PhpUseStatement;
import com.kalessil.php.lang.psi.visitors.PhpElementVisitor;
import org.jetbrains.annotations.NotNull;
import com.intellij.lang.ASTNode;

/**
 * @author VISTALL
 * @since 19.09.13.
 */
public class PhpUseStatementImpl extends PhpElementImpl implements PhpUseStatement
{
	public PhpUseStatementImpl(ASTNode node)
	{
		super(node);
	}

	@Override
	public void accept(@NotNull PhpElementVisitor visitor)
	{
		visitor.visitUseStatement(this);
	}

	@NotNull
	@Override
	public PhpClassReference[] getClassReferences()
	{
		return findChildrenByClass(PhpClassReference.class);
	}
}
