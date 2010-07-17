//==============================================================================
//
//   AlgorithmResult.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: AlgorithmResult.java,v 1.2 2010/07/17 22:00:18 klukas Exp $

/*
 * $Id
 */
package org.graffiti.plugin.algorithm;

import java.util.Map;

/**
 * An <code>AlgorithmResult</code> is a map of results that were computed by a
 * <code>CalculatingAlgorithm</code>. It maps the name of a result to the
 * corresponding value.
 *
 * @version $Revision: 1.2 $
 *
 * @see CalculatingAlgorithm
 */
public interface AlgorithmResult
{
    //~ Methods ================================================================

    /**
     * Returns the <code>Map</code>. This function is intended to be used by
     * other components that want to display the results.
     *
     * @return DOCUMENT ME!
     */
    public Map<?, ?> getResult();

    /**
     * Adds a key-value pair to the <code>Map</code>.
     *
     * @param key the key for the result.
     * @param value the value of the result.
     */
    public void addToResult(String key, Object value);
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
