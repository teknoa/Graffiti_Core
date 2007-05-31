//==============================================================================
//
//   GraphElementNotFoundException.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: GraphElementNotFoundException.java,v 1.1 2007/05/31 12:55:55 klukas Exp $

package org.graffiti.graph;

/**
 * The <code>GraphElementNotFoundException</code> will be thrown if a method
 * tries to deal with a <code>GraphElement</code> which cannot be found in the
 * <code>Graph</code>.
 *
 * @version $Revision: 1.1 $
 */
public class GraphElementNotFoundException
    extends RuntimeException
{
    //~ Constructors ===========================================================

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new <code>GraphElementNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the error message.
     */
    public GraphElementNotFoundException(String msg)
    {
        super(msg);
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
