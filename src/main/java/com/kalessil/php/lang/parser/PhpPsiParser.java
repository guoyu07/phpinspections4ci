package com.kalessil.php.lang.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import com.kalessil.php.lang.parser.parsing.Program;
import com.kalessil.php.lang.parser.util.PhpPsiBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * @author jay
 * @author kalessil
 */
public class PhpPsiParser implements PsiParser
{
	PhpPsiParser() {
    }

    @NotNull
    public ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder builder) {
		// builder.setDebugMode(true);
        PhpPsiBuilder psiBuilder = new PhpPsiBuilder(builder);
        PsiBuilder.Marker marker = psiBuilder.mark();
        // long startTime = System.currentTimeMillis();
        Program.parse(psiBuilder);
        // System.out.println("parsing time: " + (System.currentTimeMillis() - startTime));
        marker.done(root);
		return psiBuilder.getTreeBuilt();
    }
}
