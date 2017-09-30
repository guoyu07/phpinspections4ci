package com.kalessil.php.lang.highlighter;

import com.kalessil.php.lang.PhpLanguage;
import com.kalessil.php.lang.lexer.PhpTokenTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.ide.highlighter.HtmlFileType;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.ex.util.LayerDescriptor;
import com.intellij.openapi.editor.ex.util.LayeredLexerEditorHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import consulo.lang.LanguageVersionResolvers;

/**
 * Created by IntelliJ IDEA.
 * User: jay
 * Date: 22.02.2007
 *
 * @author jay
 */
public class PhpSyntaxHighlighter extends LayeredLexerEditorHighlighter
{
	public PhpSyntaxHighlighter(@Nullable final Project project, @Nullable final VirtualFile virtualFile, @NotNull final EditorColorsScheme colors)
	{
		super(new PhpFileSyntaxHighlighter(LanguageVersionResolvers.INSTANCE.forLanguage(PhpLanguage.INSTANCE).getLanguageVersion(PhpLanguage
				.INSTANCE, project, virtualFile)), colors);
		registerLayer(PhpTokenTypes.HTML, new LayerDescriptor(SyntaxHighlighterFactory.getSyntaxHighlighter(HtmlFileType.INSTANCE, project,
				virtualFile), ""));
	}
}