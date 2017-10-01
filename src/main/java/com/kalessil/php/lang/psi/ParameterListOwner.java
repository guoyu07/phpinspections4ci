package com.kalessil.php.lang.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author kalessil
 */
public interface ParameterListOwner {
    @Nullable
    ParameterList getParameterList();

    @NotNull
    PsiElement[] getParameters();
}
