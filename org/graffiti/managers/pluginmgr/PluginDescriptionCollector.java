//==============================================================================
//
//   PluginDescriptionCollector.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: PluginDescriptionCollector.java,v 1.1 2007/05/31 12:55:58 klukas Exp $

package org.graffiti.managers.pluginmgr;

import java.util.List;

/**
 * Collects plugin description URLs, which can be used by the PluginSelector.
 *
 * @version $Revision: 1.1 $
 *
 * @see PluginSelector
 * @see org.graffiti.managers.pluginmgr.Entry
 */
public interface PluginDescriptionCollector
{
    //~ Methods ================================================================

    /**
     * Returns an enumeration of {@link
     * org.graffiti.managers.pluginmgr.Entry}s.
     *
     * @return DOCUMENT ME!
     */
    public List collectPluginDescriptions();
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
