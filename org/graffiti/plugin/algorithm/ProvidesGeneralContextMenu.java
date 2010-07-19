/*******************************************************************************
 * 
 *    Copyright (c) 2003-2008 Network Analysis Group, IPK Gatersleben
 * 
 *******************************************************************************/
// $Id: ProvidesGeneralContextMenu.java,v 1.3 2010/07/19 13:00:48 morla Exp $

package org.graffiti.plugin.algorithm;

import javax.swing.JMenuItem;

/**
 * DOCUMENT ME!
 *
 * @author $Author: morla $
 * @version $Revision: 1.3 $
 */
public interface ProvidesGeneralContextMenu
{
	//~ Methods ================================================================

	/**
	 * This method should be implemented, as that it returns the desired
	 * Context-MenuItem for the Plugin. It will be added on the fly to a newly
	 * created context menu, when the user right-clicks an EditorFrame. The
	 * plugin should implement the Interface <code>SelectionListener</code> if
	 * the menu item should be variable to the current selection. You could
	 * also return a MenuItem that contains a subMenu.
	 *
	 * @return <code>MenuItem</code> the menu item for the context menu
	 */
	public JMenuItem[] getCurrentContextMenuItem();
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
