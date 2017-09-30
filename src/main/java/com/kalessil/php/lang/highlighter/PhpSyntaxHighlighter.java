package com.kalessil.php.lang.highlighter;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.ex.util.LayerDescriptor;
import com.intellij.openapi.editor.ex.util.LayeredLexerEditorHighlighter;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypes;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.tree.IElementType;
import com.kalessil.php.lang.lexer.PhpTokenTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author jay
 * @author kalessil
 */
public class PhpSyntaxHighlighter extends LayeredLexerEditorHighlighter
{
	public PhpSyntaxHighlighter(@Nullable Project project, @Nullable VirtualFile virtualFile, @NotNull EditorColorsScheme colors) {
        this(project, virtualFile, colors, null);
    }

    public PhpSyntaxHighlighter(
    		@Nullable Project project,
			@Nullable VirtualFile virtualFile,
			@NotNull EditorColorsScheme colors,
			@Nullable Lexer baseLexer
	) {
        super(new PhpFileSyntaxHighlighter(baseLexer), colors);

        final FileType fileType             = virtualFile == null ? FileTypes.PLAIN_TEXT : virtualFile.getFileType();
        final SyntaxHighlighter highlighter = SyntaxHighlighterFactory.getSyntaxHighlighter(fileType, project, virtualFile);
        if (highlighter != null) {
			this.registerLayer(PhpTokenTypes.HTML, new LayerDescriptor(new SyntaxHighlighter() {
				@NotNull
				public Lexer getHighlightingLexer() {
					return highlighter.getHighlightingLexer();
				}

				@NotNull
				public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
					return highlighter.getTokenHighlights(tokenType);
				}
			}, ""));
		}
    }
}
