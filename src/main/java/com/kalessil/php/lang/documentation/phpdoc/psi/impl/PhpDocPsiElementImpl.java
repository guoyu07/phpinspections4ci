package com.kalessil.php.lang.documentation.phpdoc.psi.impl;

import com.kalessil.php.lang.documentation.phpdoc.psi.PhpDocPsiElement;
import com.kalessil.php.lang.psi.impl.PhpElementImpl;
import com.intellij.lang.ASTNode;

/**
 * @author jay
 * @date Jun 29, 2008 2:09:01 AM
 */
abstract public class PhpDocPsiElementImpl extends PhpElementImpl implements PhpDocPsiElement
{

	public PhpDocPsiElementImpl(ASTNode node)
	{
		super(node);
	}

}
