package com.kalessil.phpStorm.phpInspectionsEA.utils;

import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.kalessil.php.lang.parser.PhpElementTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final public class OpenapiTypesUtil {

    static public boolean isAssignment(@Nullable PsiElement expression) {
        return expression != null && expression.getNode().getElementType() == PhpElementTypes.ASSIGNMENT_EXPRESSION;
    }

    static public boolean isFunctionReference(@Nullable PsiElement expression) {
        return expression != null && expression.getNode().getElementType() == PhpElementTypes.FUNCTION_CALL;
    }

    static public boolean isStatementImpl(@Nullable PsiElement expression) {
        return expression != null && expression.getNode().getElementType() == PhpElementTypes.STATEMENT;
    }

    static public boolean isPhpExpressionImpl(@Nullable PsiElement expression) {
        return expression != null && expression.getNode().getElementType() == PhpElementTypes.EXPRESSION;
    }

    static public boolean isNumber(@Nullable PsiElement expression) {
        return expression != null && expression.getNode().getElementType() == PhpElementTypes.NUMBER;
    }

    static public boolean is(@Nullable PsiElement expression, @NotNull IElementType type) {
        return expression != null && expression.getNode().getElementType() == type;
    }
}
