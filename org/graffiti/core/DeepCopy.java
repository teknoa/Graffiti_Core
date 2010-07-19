//==============================================================================
//
//   DeepCopy.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: DeepCopy.java,v 1.2 2010/07/19 13:00:57 morla Exp $

package org.graffiti.core;

/**
 * Guarantees a deep copy.
 *
 * @version $Revision: 1.2 $
 */
public interface DeepCopy
{
	//~ Methods ================================================================

	/**
	 * Returns a deep copy of this object.
	 *
	 * @return A deep copy of this object.
	 */
	public Object copy();
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
