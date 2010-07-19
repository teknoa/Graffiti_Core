//==============================================================================
//
//   PluginManagerListener.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: PluginManagerListener.java,v 1.3 2010/07/19 13:01:31 morla Exp $

package org.graffiti.managers.pluginmgr;

import org.graffiti.plugin.GenericPlugin;

/**
 * Represents a listener, which is called, if a plugin has been added to the
 * plugin manager.
 *
 * @version $Revision: 1.3 $
 */
public interface PluginManagerListener
{
	//~ Methods ================================================================

	/**
	 * Called by the plugin manager, iff a plugin has been added.
	 *
	 * @param plugin the added plugin.
	 * @param desc the description of the new plugin.
	 */
	public void pluginAdded(GenericPlugin plugin, PluginDescription desc);
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
