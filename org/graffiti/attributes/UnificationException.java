//==============================================================================
//
//   UnificationException.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: UnificationException.java,v 1.2 2010/07/19 12:59:19 morla Exp $

/*
 * $Id: UnificationException.java,v 1.2 2010/07/19 12:59:19 morla Exp $
 */
package org.graffiti.attributes;

/**
 * Thrown in the context of unification failures (e.g.: during the merge of two
 * collection attributes in an <code>addAttributeConsumer</code> call).
 *
 * @version $Revision: 1.2 $
 */
public class UnificationException
extends Exception
{
	//~ Constructors ===========================================================

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for UnificationException.
	 *
	 * @param arg0
	 */
	public UnificationException(String arg0)
	{
		super(arg0);
	}

	/**
	 * Constructor for UnificationException.
	 *
	 * @param arg0
	 */
	public UnificationException(Throwable arg0)
	{
		super(arg0);
	}
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
