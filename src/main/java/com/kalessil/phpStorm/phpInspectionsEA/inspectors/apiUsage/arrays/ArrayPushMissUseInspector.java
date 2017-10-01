package com.kalessil.phpStorm.phpInspectionsEA.inspectors.apiUsage.arrays;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiWhiteSpace;
import com.kalessil.php.lang.lexer.PhpTokenTypes;
import com.kalessil.php.lang.psi.FunctionReference;
import com.kalessil.phpStorm.phpInspectionsEA.openApi.BasePhpElementVisitor;
import com.kalessil.phpStorm.phpInspectionsEA.openApi.BasePhpInspection;
import com.kalessil.phpStorm.phpInspectionsEA.utils.OpenapiTypesUtil;
import org.jetbrains.annotations.NotNull;

public class ArrayPushMissUseInspector extends BasePhpInspection {
    private static final String messagePattern = "'%t%[] = ...' should be used instead (2x faster).";

    @NotNull
    public String getShortName() {
        return "ArrayPushMissUseInspection";
    }

    @Override
    @NotNull
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new BasePhpElementVisitor() {
            @Override
            public void visitPhpFunctionCall(@NotNull FunctionReference reference) {
                /* check requirements */
                final PsiElement[] params = reference.getParameters();
                final String function     = reference.getName();
                if (params.length != 2 || function == null || !function.equals("array_push")) {
                    return;
                }

                /* inspect given call: single instruction, 2nd parameter is not variadic */
                if (OpenapiTypesUtil.isStatementImpl(reference.getParent())) {
                    PsiElement variadicCandidate = params[1].getPrevSibling();
                    if (variadicCandidate instanceof PsiWhiteSpace) {
                        variadicCandidate = variadicCandidate.getPrevSibling();
                    }
                    /* do not report cases with variadic 2nd parameter */
                    if (OpenapiTypesUtil.is(variadicCandidate, PhpTokenTypes.opVARIADIC)) {
                        return;
                    }

                    final String message = messagePattern.replace("%t%", params[0].getText());
                    holder.registerProblem(reference, message);
                }
            }
        };
    }
}
