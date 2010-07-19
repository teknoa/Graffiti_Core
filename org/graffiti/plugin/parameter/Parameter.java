//==============================================================================
//
//   Parameter.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: Parameter.java,v 1.2 2010/07/19 13:01:54 morla Exp $

package org.graffiti.plugin.parameter;

import java.awt.image.BufferedImage;

import org.graffiti.plugin.Displayable;

/**
 * Interface for a parameter used by an <code>Algorithm</code>.
 *
 * @version $Revision: 1.2 $
 *
 * @see org.graffiti.plugin.algorithm.Algorithm
 */
public interface Parameter
extends Displayable
{
	//~ Methods ================================================================

	/**
	 * Returns an image representing the <code>Parameter</code>. May return
	 * <code>null</code> if there is no representing image.
	 *
	 * @return an image representing the <code>Parameter</code>.
	 */
	public BufferedImage getImage();
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
