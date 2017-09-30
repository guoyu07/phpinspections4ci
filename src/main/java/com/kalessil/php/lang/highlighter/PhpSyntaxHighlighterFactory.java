package com.kalessil.php.lang.highlighter;

import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;

/**
 * @author VISTALL
 * @author kalessil
 */
public class PhpSyntaxHighlighterFactory extends SyntaxHighlighterFactory
{
	public PhpSyntaxHighlighterFactory() {
    }

    @NotNull
    public SyntaxHighlighter getSyntaxHighlighter(Project project, VirtualFile virtualFile) {
        return new PhpFileSyntaxHighlighter();
    }
}
