package com.kalessil.php.lang.psi;

import org.jetbrains.annotations.NotNull;

/**
 * @author jay
 * @author kalessil
 */
public interface ParameterList extends PhpElement
{
	@NotNull
	PhpParameter[] getParameters();
}
