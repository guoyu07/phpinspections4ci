package com.kalessil.php.lang.psi.resolve;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import com.kalessil.php.lang.psi.PhpElement;
import com.kalessil.php.lang.psi.PhpField;
import com.kalessil.php.lang.psi.PhpFunction;
import com.kalessil.php.lang.psi.PhpParameter;
import com.intellij.openapi.util.Comparing;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;

/**
 * @author jay
 * @date Apr 15, 2008 10:10:23 AM
 */
public class PhpResolveProcessor extends PhpScopeProcessor
{
	public static enum ResolveKind
	{
		METHOD,
		FIELD,
		FIELD_OR_PARAMETER
	}

	private Set<PsiElement> result = new LinkedHashSet<PsiElement>();
	private ResolveKind myKind;
	private String myName;

	public PhpResolveProcessor(PhpElement element, String name, ResolveKind kind)
	{
		super(element);
		myName = name;
		myKind = kind;
	}

	public Collection<PsiElement> getResult()
	{
		return result;
	}

	@Override
	public boolean execute(PsiElement psiElement)
	{
		switch(myKind)
		{
			case FIELD:
				if(!(psiElement instanceof PhpField))
				{
					return true;
				}
				break;
			case FIELD_OR_PARAMETER:
				if(!(psiElement instanceof PhpField) && !(psiElement instanceof PhpParameter))
				{
					return true;
				}
				break;
			case METHOD:
				if(!(psiElement instanceof PhpFunction))
				{
					return true;
				}
				break;
		}

		if(psiElement == element)
		{
			return true;
		}

		String name = ((PsiNamedElement) psiElement).getName();
		if(Comparing.equal(name, myName))
		{
			result.add(psiElement);
		}
		return true;
	}

}
