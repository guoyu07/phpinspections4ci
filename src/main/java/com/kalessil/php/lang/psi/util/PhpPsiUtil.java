package com.kalessil.php.lang.psi.util;

import com.kalessil.php.lang.psi.PhpPackage;
import com.kalessil.php.module.extension.PhpModuleExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import consulo.psi.PsiPackageManager;

/**
 * @author VISTALL
 * @since 19.09.13.
 */
public class PhpPsiUtil
{
	@Nullable
	public static PhpPackage findPackage(@NotNull Project project, @NotNull String name)
	{
		return (PhpPackage) PsiPackageManager.getInstance(project).findPackage(name, PhpModuleExtension.class);
	}

	@Nullable
	public static PhpPackage findPackage(@NotNull Project project, @NotNull PsiDirectory psiDirectory)
	{
		return (PhpPackage) PsiPackageManager.getInstance(project).findPackage(psiDirectory, PhpModuleExtension.class);
	}
}