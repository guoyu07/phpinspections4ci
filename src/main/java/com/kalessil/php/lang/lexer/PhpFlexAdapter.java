package com.kalessil.php.lang.lexer;

import com.intellij.lexer.FlexAdapter;
import com.kalessil.php.PhpLanguageLevel;

/**
 * @author jay
 */
public class PhpFlexAdapter extends FlexAdapter {

    public PhpFlexAdapter(PhpLanguageLevel languageLevel) {
        super(new PhpFlexLexer(false, languageLevel));
    }

}
