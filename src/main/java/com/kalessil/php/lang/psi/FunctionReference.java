package com.kalessil.php.lang.psi;

import com.intellij.psi.PsiPolyVariantReference;
import org.jetbrains.annotations.Nullable;

/**
 * @author jay
 * @author kalessil
 */
public interface FunctionReference extends PhpElement, PsiPolyVariantReference, ParameterListOwner
{
	boolean canReadName();

	@Nullable
	String getName();
}
