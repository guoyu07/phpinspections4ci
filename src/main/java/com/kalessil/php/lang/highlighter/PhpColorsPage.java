package com.kalessil.php.lang.highlighter;

import java.util.Map;

import javax.swing.Icon;

import com.intellij.lang.Language;
import com.intellij.openapi.options.colors.RainbowColorSettingsPage;
import com.intellij.psi.codeStyle.DisplayPriority;
import com.intellij.psi.codeStyle.DisplayPrioritySortable;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.PlatformUtils;
import com.kalessil.php.PhpBundle;
import com.kalessil.php.PhpLanguageLevel;
import com.kalessil.php.lang.PhpLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;

/**
 * @author jay
 * @author kalessil
 */
public class PhpColorsPage implements RainbowColorSettingsPage, DisplayPrioritySortable
{

	private static final String DEMO_TEXT = "<?php\n" +
			"$heredoc = <<< HEREDOC_ID\n" +
			"some $contents\n" +
			"HEREDOC_ID;\n" +
			"\n" +
			"// Sample comment\n\n" +
			"class SomeClass {\n" +
			"   /**\n" +
			"    * Description by <a href=\"mailto:\">alex</a>\n" +
			"    * @return SomeType\n" +
			"    */\n" +
			"   function doSmth($abc, $def) {\n" +
			"      $v = Helper::convert($abc . \"\\n {$def}\");\n" +
			"      $q = new Query( $this->invent(abs(0x80)) );\n" +
			"      return array($v => $q->result);\n" +
			"   }\n" +
			"}\n\n" +
			"include (dirname(__FILE__) . \"inc.php\");\n" +
			"`rm -r`;\n" +
			"\\";


	private static final AttributesDescriptor[] ATTRS = new AttributesDescriptor[]{
			new AttributesDescriptor(PhpBundle.message("color.settings.keyword"), PhpHighlightingData.KEYWORD),
			new AttributesDescriptor(PhpBundle.message("color.settings.comment"), PhpHighlightingData.COMMENT),
			new AttributesDescriptor(PhpBundle.message("color.settings.number"), PhpHighlightingData.NUMBER),
			new AttributesDescriptor(PhpBundle.message("color.settings.string"), PhpHighlightingData.STRING),
			new AttributesDescriptor(PhpBundle.message("color.settings.exec_command"), PhpHighlightingData.EXEC_COMMAND),
			new AttributesDescriptor(PhpBundle.message("color.settings.escape_sequence"), PhpHighlightingData.ESCAPE_SEQUENCE),
			new AttributesDescriptor(PhpBundle.message("color.settings.operation"), PhpHighlightingData.OPERATION_SIGN),
			new AttributesDescriptor(PhpBundle.message("color.settings.brackets"), PhpHighlightingData.BRACKETS),
			new AttributesDescriptor(PhpBundle.message("color.settings.predefined.symbols"), PhpHighlightingData.PREDEFINED_SYMBOL),
			//new AttributesDescriptor(PhpBundle.message("color.settings.expression_subtitution_marks"), PhpHighlightingData.EXPR_SUBST_MARKS),
			new AttributesDescriptor(PhpBundle.message("color.settings.bad_character"), PhpHighlightingData.BAD_CHARACTER),
			new AttributesDescriptor(PhpBundle.message("color.settings.comma"), PhpHighlightingData.COMMA),
			new AttributesDescriptor(PhpBundle.message("color.settings.semicolon"), PhpHighlightingData.SEMICOLON),
			new AttributesDescriptor(PhpBundle.message("color.settings.heredoc_id"), PhpHighlightingData.HEREDOC_ID),
			new AttributesDescriptor(PhpBundle.message("color.settings.heredoc_content"), PhpHighlightingData.HEREDOC_CONTENT),
			new AttributesDescriptor(PhpBundle.message("color.settings.var"), PhpHighlightingData.VAR),
			new AttributesDescriptor(PhpBundle.message("color.settings.identifier"), PhpHighlightingData.IDENTIFIER),
			new AttributesDescriptor(PhpBundle.message("color.settings.constant"), PhpHighlightingData.CONSTANT),
			new AttributesDescriptor(PhpBundle.message("color.settings.doccomment"), PhpHighlightingData.DOC_COMMENT),
			new AttributesDescriptor(PhpBundle.message("color.settings.doctag"), PhpHighlightingData.DOC_TAG),
			new AttributesDescriptor(PhpBundle.message("color.settings.docmarkup"), PhpHighlightingData.DOC_MARKUP)
	};

	@Override
	@NotNull
	public String getDisplayName()
	{
		return PhpBundle.message("color.settings.name");
	}

	@Override
	@NotNull
	public AttributesDescriptor[] getAttributeDescriptors()
	{
		return ATTRS;
	}

	@Override
	@NotNull
	public ColorDescriptor[] getColorDescriptors()
	{
		return new ColorDescriptor[0];
	}

	@Override
	@NotNull
	public SyntaxHighlighter getHighlighter()
	{
		return new PhpFileSyntaxHighlighter();
	}

	@Override
	@NonNls
	@NotNull
	public String getDemoText()
	{
		return DEMO_TEXT;
	}

	@Override
	@Nullable
	public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap()
	{
		return null;
	}

	@Nullable
    public Language getLanguage() {
        return PhpLanguage.INSTANCE;
    }

	public DisplayPriority getPriority() {
        return PlatformUtils.isPhpStorm() ? DisplayPriority.KEY_LANGUAGE_SETTINGS : DisplayPriority.LANGUAGE_SETTINGS;
    }

	public boolean isRainbowType(TextAttributesKey type) {
        return  PhpHighlightingData.PARAMETER.equals(type) ||
				PhpHighlightingData.VAR.equals(type) ||
				PhpHighlightingData.DOC_COMMENT.equals(type);
    }

}
