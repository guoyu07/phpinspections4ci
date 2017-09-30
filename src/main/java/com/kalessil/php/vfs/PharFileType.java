package com.kalessil.php.vfs;

import com.intellij.ide.highlighter.ArchiveFileType;
import org.jetbrains.annotations.NotNull;

/**
 * @author VISTALL
 * @since 13.07.13.
 */
public class PharFileType extends ArchiveFileType
{
	public static final PharFileType INSTANCE = new PharFileType();

	public String getProtocol()
	{
		return PharFileSystem.PROTOCOL;
	}

	@NotNull
	@Override
	public String getDefaultExtension()
	{
		return "phar";
	}

	@NotNull
	@Override
	public String getName()
	{
		return "PHAR_ARCHIVE";
	}
}
