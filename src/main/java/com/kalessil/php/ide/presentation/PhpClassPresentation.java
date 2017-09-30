package com.kalessil.php.ide.presentation;

import javax.swing.Icon;

import com.kalessil.php.PhpLanguageLevel;
import com.kalessil.php.lang.psi.PhpClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.ItemPresentationProvider;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.ArrayUtil;

/**
 * @author VISTALL
 * @since 22.09.13.
 */
public class PhpClassPresentation implements ItemPresentationProvider<PhpClass>
{
	@Override
	public ItemPresentation getPresentation(final PhpClass phpClass)
	{
		return new ItemPresentation()
		{
 			@Nullable
			@Override
			public String getPresentableText()
			{
				return phpClass.getName();
			}

			@Nullable
			@Override
			public String getLocationString()
			{
				Module moduleForPsiElement = ModuleUtil.findModuleForPsiElement(phpClass);
				return getPresentablePathForClass(phpClass);
			}

			@Nullable
			@Override
			public Icon getIcon(boolean unused)
			{
				return phpClass.getIcon(0);
			}

			private String getPresentablePathForClass(@NotNull PhpClass klass)
			{
				VirtualFile classRoot = klass.getContainingFile().getVirtualFile();

				if(klass.getName() != null)
				{
					String[] fileNames = ArrayUtil.reverseArray(klass.getName().split("_"));
					for(String fileName : fileNames)
					{
						if(!classRoot.getNameWithoutExtension().equals(fileName))
						{
							break;
						}
						classRoot = classRoot.getParent();
					}
				}
				return PhpFilePresentation.getPresentablePathForFile(classRoot, klass.getProject());
			}
		};
	}
}
