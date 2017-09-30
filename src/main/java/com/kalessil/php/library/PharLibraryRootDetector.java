package com.kalessil.php.library;

import com.intellij.openapi.roots.OrderRootType;
import com.kalessil.php.vfs.PharFileType;
import com.intellij.openapi.roots.libraries.ui.FileTypeBasedRootFilter;


/**
 * @author VISTALL
 * @since 09.03.14
 */
public class PharLibraryRootDetector extends FileTypeBasedRootFilter
{
	public PharLibraryRootDetector()
	{
		super(OrderRootType.CLASSES, false, PharFileType.INSTANCE, "Phar library");
	}
}
