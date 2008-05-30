/*******************************************************************************
 * 
 *    Copyright (c) 2003-2008 Network Analysis Group, IPK Gatersleben
 * 
 *******************************************************************************/
// $Id: ProvidesEdgeContextMenu.java,v 1.2 2008/05/30 10:52:49 klukas Exp $

package org.graffiti.plugin.algorithm;

import java.util.Collection;

import javax.swing.JMenuItem;

import org.graffiti.graph.Edge;

/**
 * DOCUMENT ME!
 *
 * @author $Author: klukas $
 * @version $Revision: 1.2 $
 */
public interface ProvidesEdgeContextMenu
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
    public JMenuItem[] getCurrentEdgeContextMenuItem(Collection<Edge> selectedEdges);
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
