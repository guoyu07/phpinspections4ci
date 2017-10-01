package com.kalessil.php.lang.parser.parsing.functions;

import com.intellij.psi.tree.TokenSet;
import com.kalessil.php.lang.lexer.PhpTokenTypes;
import com.kalessil.php.lang.parser.PhpElementTypes;
import com.kalessil.php.lang.parser.parsing.classes.ClassReference;
import com.kalessil.php.lang.parser.parsing.expressions.Expression;
import com.kalessil.php.lang.parser.parsing.expressions.StaticScalar;
import com.kalessil.php.lang.parser.util.ListParsingHelper;
import com.kalessil.php.lang.parser.util.ParserPart;
import com.kalessil.php.lang.parser.util.PhpParserErrors;
import com.kalessil.php.lang.parser.util.PhpPsiBuilder;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;

/**
 * @author markov
 * @author kalesil
 */
public class ParameterList implements PhpTokenTypes
{
    public ParameterList() {
    }

    public static IElementType parse(PhpPsiBuilder builder) {
        PsiBuilder.Marker parameterList = builder.mark();
        ParameterList.Parameter part = new ParameterList.Parameter();
        int result = ListParsingHelper.parseCommaDelimitedExpressionWithLeadExpr(builder, part.parse(builder), part, false);
        parameterList.done(PhpElementTypes.PARAMETER_LIST);
        return result > 0 ? PhpElementTypes.PARAMETER_LIST : PhpElementTypes.EMPTY_INPUT;
    }

    private static class Parameter implements ParserPart {

        public IElementType parse(PhpPsiBuilder builder) {
            if (builder.compare(PhpTokenTypes.chRPAREN)) {
                return PhpElementTypes.EMPTY_INPUT;
            } else {
                PsiBuilder.Marker parameter = builder.mark();

                PsiBuilder.Marker defaultValue;

                builder.compareAndEat(PhpTokenTypes.opBIT_AND);
                builder.compareAndEat(PhpTokenTypes.opVARIADIC);
                if (builder.compareAndEat(PhpTokenTypes.DOLLAR)) {
                    builder.error(PhpParserErrors.expected(PhpTokenTypes.VARIABLE));
                } else if (!builder.compareAndEat(PhpTokenTypes.VARIABLE)) {
                    builder.error(PhpParserErrors.expected(PhpTokenTypes.VARIABLE));
                    builder.compareAndEat(PhpTokenTypes.IDENTIFIER);
                }

                if (builder.compare(PhpTokenTypes.opASGN)) {
                    builder.advanceLexer();
                    defaultValue = builder.mark();
                    if (Expression.parse(builder) == PhpElementTypes.EMPTY_INPUT) {
                        builder.error(PhpParserErrors.expected("default value"));
                    }

                    defaultValue.done(PhpElementTypes.PARAMETER_DEFAULT_VALUE);
                }

                parameter.done(PhpElementTypes.PARAMETER);
                return PhpElementTypes.PARAMETER;
            }
        }
    }
}
