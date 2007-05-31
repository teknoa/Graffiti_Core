//==============================================================================
//
//   PluginManagerListener.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: PluginManagerListener.java,v 1.1 2007/05/31 12:55:59 klukas Exp $

package org.graffiti.managers.pluginmgr;

import org.graffiti.plugin.GenericPlugin;

/**
 * Represents a listener, which is called, iff a plugin has been added to the
 * plugin manager.
 *
 * @version $Revision: 1.1 $
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
