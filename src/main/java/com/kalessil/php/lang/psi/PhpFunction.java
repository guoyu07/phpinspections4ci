package com.kalessil.php.lang.psi;

import com.intellij.psi.PsiElement;
import com.kalessil.php.lang.psi.resolve.types.PhpTypeOwner;
import org.jetbrains.annotations.NotNull;
import com.intellij.util.ArrayFactory;

/**
 * @author jay
 * @author kalessi
 */
public interface PhpFunction extends PhpModifierListOwner, PhpTypeOwner, PhpBraceOwner
{
	PhpFunction[] EMPTY_ARRAY = new PhpFunction[0];

	ArrayFactory<PhpFunction> ARRAY_FACTORY = new ArrayFactory<PhpFunction>()
	{
		@NotNull
		@Override
		public PhpFunction[] create(int i)
		{
			return i == 0 ? EMPTY_ARRAY : new PhpFunction[i];
		}
	};

	PsiElement[] getParameters();

	ParameterList getParameterList();
}
