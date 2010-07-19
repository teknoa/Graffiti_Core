//==============================================================================
//
//   SelectionListener.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: SelectionListener.java,v 1.2 2010/07/19 13:01:49 morla Exp $

package org.graffiti.selection;

/**
 * Interfaces a listener, which wants to be informed about a change in the
 * selection model.
 *
 * @version $Revision: 1.2 $
 */
public interface SelectionListener
{
	//~ Methods ================================================================

	/**
	 * Is called, if something in the selection model changed.
	 */
	public void selectionChanged(SelectionEvent e);

	/**
	 * Is called, if a named selection is added or removed.
	 */
	public void selectionListChanged(SelectionEvent e);
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
