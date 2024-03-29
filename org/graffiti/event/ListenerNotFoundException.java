// ==============================================================================
//
// ListenerNotFoundException.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================
// $Id: ListenerNotFoundException.java,v 1.5 2010/12/22 13:05:34 klukas Exp $

package org.graffiti.event;

/**
 * Will be thrown, if a method tries to deal with a listener that cannot be
 * found in the listener list.
 * 
 * @version $Revision: 1.5 $
 */
public class ListenerNotFoundException
					extends Exception {
	// ~ Constructors ===========================================================
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a <code>ListenerNotFoundException</code> with the specified
	 * detail message.
	 * 
	 * @param msg
	 *           the detail message for the exception.
	 */
	public ListenerNotFoundException(String msg) {
		super(msg);
	}
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
