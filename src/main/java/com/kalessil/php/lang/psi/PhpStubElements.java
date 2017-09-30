package com.kalessil.php.lang.psi;

import com.kalessil.php.lang.psi.impl.stub.elements.PhpClassStubElement;
import com.kalessil.php.lang.psi.impl.stub.elements.PhpFieldStubElement;
import com.kalessil.php.lang.psi.impl.stub.elements.PhpFileStubElement;
import com.kalessil.php.lang.psi.impl.stub.elements.PhpFunctionStubElement;

/**
 * @author VISTALL
 * @since 16.07.13.
 */
public interface PhpStubElements
{
	PhpFileStubElement FILE = new PhpFileStubElement();
	PhpClassStubElement CLASS = new PhpClassStubElement();
	PhpFieldStubElement FIELD = new PhpFieldStubElement();
	PhpFunctionStubElement FUNCTION = new PhpFunctionStubElement();
}
