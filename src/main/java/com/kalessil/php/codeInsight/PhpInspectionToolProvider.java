package com.kalessil.php.codeInsight;

import com.kalessil.php.lang.inspections.PhpDynamicAsStaticMethodCall;
import com.kalessil.php.lang.inspections.PhpUndefinedMethodCall;
import com.kalessil.php.lang.inspections.PhpUndefinedVariable;
import com.kalessil.php.lang.inspections.classes.PhpUnimplementedMethodsInClass;
import com.intellij.codeInspection.InspectionToolProvider;

/**
 * @author VISTALL
 * @since 18.09.13.
 */
public class PhpInspectionToolProvider implements InspectionToolProvider
{
	@Override
	public Class[] getInspectionClasses()
	{
		return new Class[]{
				PhpUndefinedVariable.class,
				PhpUndefinedMethodCall.class,
				PhpDynamicAsStaticMethodCall.class,
				PhpUnimplementedMethodsInClass.class,
		};
	}
}
