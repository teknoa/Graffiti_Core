//==============================================================================
//
//   DefaultAlgorithmManager.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: DefaultAlgorithmManager.java,v 1.2 2009/06/23 07:05:20 klukas Exp $

package org.graffiti.managers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.graffiti.managers.pluginmgr.PluginDescription;
import org.graffiti.plugin.GenericPlugin;
import org.graffiti.plugin.algorithm.Algorithm;

/**
 * Manages the map of available algorithms: key = algorithm class names, 
 * value = algorithm
 *
 * @version $Revision: 1.2 $
 */
public class DefaultAlgorithmManager
    implements AlgorithmManager
{
    //~ Instance fields ========================================================

    /** The algorithms: key = algorithm class names, value = algorithm */
    private Map algorithms;

    //~ Constructors ===========================================================

    /**
     * Constructs a new algorithm manager.
     */
    public DefaultAlgorithmManager()
    {
        algorithms = new HashMap();
    }

    //~ Methods ================================================================

    /*
     * @see org.graffiti.managers.AlgorithmManager#getAlgorithms()
     */
    public List getAlgorithms()
    {
        return new LinkedList(algorithms.values());
    }

    /*
     * @see org.graffiti.managers.AlgorithmManager#getClassName(org.graffiti.plugin.algorithm.Algorithm)
     */
    public String getClassName(Algorithm algorithm)
    {
        for (Iterator it = algorithms.keySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry)it.next();
			if (entry.getValue() == algorithm) {
				return (String)entry.getKey();
			}
		}
		// not found:
        return null;
    }

    /*
     * @see org.graffiti.managers.AlgorithmManager#addAlgorithm(org.graffiti.plugin.algorithm.Algorithm)
     */
    public void addAlgorithm(Algorithm algorithm)
    {
        algorithms.put(algorithm.getClass().getName(), algorithm);
    }

    /*
     * @see org.graffiti.managers.pluginmgr.PluginManagerListener#pluginAdded(org.graffiti.plugin.GenericPlugin, org.graffiti.managers.pluginmgr.PluginDescription)
     */
    public void pluginAdded(GenericPlugin plugin, PluginDescription desc)
    {
        if(plugin.getAlgorithms() != null)
        {
            Algorithm[] algorithms = plugin.getAlgorithms();

            for(int i = 0; i < algorithms.length; i++)
            {
                if (algorithms[i]!=null)
                	addAlgorithm(algorithms[i]);
            }
        }
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
