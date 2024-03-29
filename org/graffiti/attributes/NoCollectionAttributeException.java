// ==============================================================================
//
// NoCollectionAttributeException.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================
// $Id: NoCollectionAttributeException.java,v 1.5 2010/12/22 13:05:32 klukas Exp $

package org.graffiti.attributes;

/**
 * The <code>NoCollectionAttributeException</code> will be thrown if a method
 * tries to add an attribute to an attribute which is no <code>CollectionAttribute</code>.
 * 
 * @version $Revision: 1.5 $
 */
public class NoCollectionAttributeException
					extends RuntimeException {
	// ~ Constructors ===========================================================
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a <code>NoCollectionAttributeException</code> with the
	 * specified detail message.
	 * 
	 * @param msg
	 *           The detail message which is saved for later retrieval by the <code>getMessage()</code> method.
	 */
	NoCollectionAttributeException(String msg) {
		super(msg);
	}
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
