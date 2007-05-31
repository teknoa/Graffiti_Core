//==============================================================================
//
//   DefaultAlgorithmResult.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: DefaultAlgorithmResult.java,v 1.1 2007/05/31 12:55:54 klukas Exp $

package org.graffiti.plugin.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * DOCUMENT ME!
 *
 * @author Paul
 */
public class DefaultAlgorithmResult
    implements AlgorithmResult
{
    //~ Instance fields ========================================================

    /** DOCUMENT ME! */
    protected Map resultMap = new HashMap();

    //~ Constructors ===========================================================

    /**
     * Constructor for DefaultAlgorithmResult.
     */
    public DefaultAlgorithmResult()
    {
        super();
    }

    /**
     * Constructor for DefaultAlgorithmResult.
     *
     * @param resultMap DOCUMENT ME!
     */
    public DefaultAlgorithmResult(Map resultMap)
    {
        super();
        this.resultMap = resultMap;
    }

    //~ Methods ================================================================

    /**
     * @see org.graffiti.plugin.algorithm.AlgorithmResult#getResult()
     */
    public Map getResult()
    {
        return this.resultMap;
    }

    /*
     * @see org.graffiti.plugin.algorithm.AlgorithmResult#addToResult(java.lang.String, java.lang.Object)
     */
    public void addToResult(String key, Object value)
    {
        this.resultMap.put(key, value);
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
