package com.kalessil.php.lang.psi;

import com.kalessil.php.lang.psi.resolve.types.PhpTypeOwner;
import org.jetbrains.annotations.Nullable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPolyVariantReference;

/**
 * @author jay
 * @date May 15, 2008 12:35:16 PM
 */
public interface PhpMethodReference extends PhpElement, PsiPolyVariantReference, PhpTypeOwner
{
	boolean canReadName();

	String getMethodName();

	@Nullable
	PhpClassReference getClassReference();

	@Nullable
	PsiElement getObjectReference();

	boolean isStatic();

	PsiElement getNameIdentifier();
}
