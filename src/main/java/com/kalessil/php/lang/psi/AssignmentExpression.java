package com.kalessil.php.lang.psi;

import com.kalessil.php.lang.psi.resolve.types.PhpTypeOwner;
import com.intellij.psi.PsiElement;

/**
 * @author jay
 * @date Apr 4, 2008 11:28:13 AM
 */
public interface AssignmentExpression extends PhpElement, PhpTypeOwner
{

	public PsiElement getVariable();

	public PsiElement getValue();

}
