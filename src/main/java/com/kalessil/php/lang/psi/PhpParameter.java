package com.kalessil.php.lang.psi;

import com.kalessil.php.lang.psi.resolve.types.PhpTypeOwner;

/**
 * @author jay
 * @date Apr 3, 2008 10:32:20 PM
 */
public interface PhpParameter extends PhpElement, PhpNamedElement, PhpTypeOwner
{
	PhpParameter[] EMPTY_ARRAY = new PhpParameter[0];
}
