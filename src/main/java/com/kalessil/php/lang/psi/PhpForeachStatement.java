package com.kalessil.php.lang.psi;

import com.intellij.psi.PsiElement;

/**
 * @author jay
 * @date Apr 15, 2008 3:36:12 PM
 */
public interface PhpForeachStatement extends PhpElement
{

	public PsiElement getArray();

	public PhpVariableReference getKey();

	public PhpVariableReference getValue();

}
