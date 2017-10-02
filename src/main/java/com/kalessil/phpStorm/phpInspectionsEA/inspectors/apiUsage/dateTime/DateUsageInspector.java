package com.kalessil.phpStorm.phpInspectionsEA.inspectors.apiUsage.dateTime;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.kalessil.php.lang.psi.FunctionReference;
import com.kalessil.phpStorm.phpInspectionsEA.openApi.BasePhpElementVisitor;
import com.kalessil.phpStorm.phpInspectionsEA.openApi.BasePhpInspection;
import com.kalessil.phpStorm.phpInspectionsEA.utils.OpenapiTypesUtil;
import org.jetbrains.annotations.NotNull;

/*
 * This file is part of the Php Inspections (EA Extended) package.
 *
 * (c) Vladimir Reznichenko <kalessil@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

public class DateUsageInspector extends BasePhpInspection {
    private static final String messageDropTime = "'time()' is default valued already, it can safely be removed.";

    @NotNull
    public String getShortName() {
        return "DateUsageInspection";
    }

    @Override
    @NotNull
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new BasePhpElementVisitor() {
            @Override
            public void visitPhpFunctionCall(@NotNull FunctionReference reference) {
                final PsiElement[] arguments = reference.getParameters();
                final String functionName    = reference.getName();
                if (arguments.length == 2 && functionName != null && functionName.equals("date")) {
                    final PsiElement candidate = arguments[1];
                    if (OpenapiTypesUtil.isFunctionReference(candidate)) {
                        final FunctionReference inner = (FunctionReference) candidate;
                        final String innerName        = inner.getName();
                        if (innerName != null && innerName.equals("time") && inner.getParameters().length == 0) {
                            holder.registerProblem(inner, messageDropTime, ProblemHighlightType.LIKE_UNUSED_SYMBOL);
                        }
                    }
                }
            }
        };
    }
}
