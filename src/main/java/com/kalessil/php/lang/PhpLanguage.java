package com.kalessil.php.lang;

import com.intellij.lang.Language;
import com.intellij.psi.templateLanguages.TemplateLanguage;

/**
 * @author jay
 * @author kalessil
 */
public class PhpLanguage extends Language implements TemplateLanguage {
    public static final PhpLanguage INSTANCE = new PhpLanguage();

    private PhpLanguage() {
        super("PHP");
    }
}
