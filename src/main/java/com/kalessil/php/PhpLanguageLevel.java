package com.kalessil.php;

import org.jetbrains.annotations.NotNull;

/**
 * @author VISTALL
 * @author kalessil
 */
public enum PhpLanguageLevel
{
	PHP_5_3("5.3.0", "PHP 5.3", ""),
	PHP_5_4("5.4.0", "PHP 5.4", ""),
	PHP_5_5("5.5.0", "PHP 5.5", ""),
	PHP_5_6("5.6.0", "PHP 5.6", ""),
	PHP_7_0("7.0.0", "PHP 1.0", ""),
	PHP_7_1("7.1.0", "PHP 7.1", "");

	private final String myVersionString;
	private final String myPresentableName;
	private final String myShortDescription;

 	PhpLanguageLevel(@NotNull String versionString, @NotNull String presentableName, @NotNull String shortDescription) {
        this.myVersionString    = versionString;
        this.myPresentableName  = presentableName;
        this.myShortDescription = shortDescription;
    }

	public boolean isAtLeast(@NotNull PhpLanguageLevel languageLevel)
	{
		return this.compareTo(languageLevel) >= 0;
	}
}
