package com.kalessil.php.codeInsight;

import com.intellij.codeInspection.InspectionToolProvider;
import org.jetbrains.annotations.NotNull;

/**
 * @author VISTALL
 * @author kalessil
 */
public class PhpInspectionToolProvider implements InspectionToolProvider {
    @Override
    @NotNull
    public Class[] getInspectionClasses() {
        return new Class[]{
        };
    }
}
