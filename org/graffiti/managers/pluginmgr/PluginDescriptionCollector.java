// ==============================================================================
//
// PluginDescriptionCollector.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================
// $Id: PluginDescriptionCollector.java,v 1.5 2010/12/14 07:02:25 morla Exp $

package org.graffiti.managers.pluginmgr;

import java.util.List;

/**
 * Collects plugin description URLs, which can be used by the PluginSelector.
 * 
 * @version $Revision: 1.5 $
 * @see PluginSelector
 * @see org.graffiti.managers.pluginmgr.PluginEntry
 */
public interface PluginDescriptionCollector {
	// ~ Methods ================================================================

	/**
	 * Returns an enumeration of {@link org.graffiti.managers.pluginmgr.PluginEntry}s.
	 * 
	 * @return DOCUMENT ME!
	 */
	public List<?> collectPluginDescriptions();
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
