package com.kalessil.php.module.packageSupport;

import com.intellij.openapi.roots.ModuleExtension;
import com.kalessil.php.lang.psi.PsiPackageManager;
import com.kalessil.php.lang.psi.impl.PhpPackageImpl;
import com.kalessil.php.module.PhpModuleExtensionUtil;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.module.Module;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.PsiPackageSupportProvider;

/**
 * @author VISTALL
 * @since 11.10.2015
 */
public class PhpPackageSupportProvider implements PsiPackageSupportProvider
{
	@Override
	public boolean isSupported(@NotNull ModuleExtension moduleExtension)
	{
		return PhpModuleExtensionUtil.isSingleModuleExtension(moduleExtension.getModule());
	}

	@Override
	public boolean isValidPackageName(@NotNull Module module, @NotNull String packageName)
	{
		return true;
	}

	@NotNull
	@Override
	public PsiPackage createPackage(@NotNull PsiManager psiManager, @NotNull PsiPackageManager packageManager, @NotNull Class<? extends ModuleExtension> extensionClass, @NotNull String packageName)
	{
		return new PhpPackageImpl(psiManager, packageManager, extensionClass, packageName);
	}
}
