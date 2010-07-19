//==============================================================================
//
//   Serializer.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: Serializer.java,v 1.2 2010/07/19 13:01:49 morla Exp $

package org.graffiti.plugin.io;

/**
 * Defines a generic serializer which provides a set of extensions.
 */
public interface Serializer
{
	//~ Methods ================================================================

	/**
	 * The file extensions the serializer can read or write.
	 *
	 * @return DOCUMENT ME!
	 */
	public String[] getExtensions();

	/**
	 * The file type descriptions of the files the serializer can read or write.
	 *
	 * @return DOCUMENT ME!
	 */
	public String[] getFileTypeDescriptions();
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
