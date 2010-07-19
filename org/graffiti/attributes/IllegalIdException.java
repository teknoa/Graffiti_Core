//==============================================================================
//
//   IllegalIdException.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: IllegalIdException.java,v 1.2 2010/07/19 12:59:22 morla Exp $

package org.graffiti.attributes;

/**
 * The <code>IllegalIdException</code> will be thrown if a method tries to add
 * an attribute at a location where another attribute already exists.
 *
 * @version $Revision: 1.2 $
 */
public class IllegalIdException
extends RuntimeException
{
	//~ Constructors ===========================================================

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an <code>IllegalIdException</code> with the specified detail
	 * message.
	 *
	 * @param msg The detail message which is saved for later retrieval by the
	 *        <code>getMessage()</code> method.
	 */
	public IllegalIdException(String msg)
	{
		super(msg);
	}
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
