package com.kalessil.php.lang.psi.impl;

import com.kalessil.php.lang.psi.PhpSelfAssignmentExpression;
import com.intellij.lang.ASTNode;

/**
 * @author jay
 * @date Apr 4, 2008 2:03:58 PM
 */
public class PhpSelfAssignmentExpressionImpl extends PhpAssignmentExpressionImpl implements PhpSelfAssignmentExpression
{
	public PhpSelfAssignmentExpressionImpl(ASTNode node)
	{
		super(node);
	}
}
