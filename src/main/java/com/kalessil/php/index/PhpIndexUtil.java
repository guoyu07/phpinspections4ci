package com.kalessil.php.index;

import java.util.Collection;
import java.util.Collections;

import com.kalessil.php.lang.psi.PhpClass;
import com.kalessil.php.lang.psi.PhpElement;
import com.kalessil.php.lang.psi.PhpFunction;
import com.intellij.psi.search.GlobalSearchScope;

/**
 * @author VISTALL
 * @since 16.07.13.
 */
public class PhpIndexUtil
{

	public static Collection<PhpClass> getClassesFor(PhpElement element)
	{
		return getClassesForName(element, null);
		//return Collections.emptyList();
	}

	public static Collection<PhpClass> getClassesForName(PhpElement element, String name)
	{
		return PhpFullFqClassIndex.INSTANCE.get(name, element.getProject(), GlobalSearchScope.allScope(element.getProject()));
		//return Collections.emptyList();
	}

	public static Collection<PhpFunction> getMethodsFor(PhpElement element)
	{
		return Collections.emptyList();
	}
}
