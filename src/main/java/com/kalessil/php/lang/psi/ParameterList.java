package com.kalessil.php.lang.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

/**
 * @author jay
 * @author kalessil
 */
public interface ParameterList extends PhpElement
{
	@NotNull
	PsiElement[] getParameters();
}
