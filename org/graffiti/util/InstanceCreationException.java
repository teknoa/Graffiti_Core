//==============================================================================
//
//   InstanceCreationException.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: InstanceCreationException.java,v 1.1 2007/05/31 12:56:03 klukas Exp $

package org.graffiti.util;

/**
 * An exception, which is thrown, iff the instanciation of a given class fails.
 *
 * @version $Revision: 1.1 $
 */
public class InstanceCreationException
    extends Exception
{
    //~ Constructors ===========================================================

    /**
     * Creates a new InstanceCreationException object.
     *
     * @param msg DOCUMENT ME!
     */
    public InstanceCreationException(String msg)
    {
        super(msg);
    }

    /**
     * Creates a new InstanceCreationException object.
     *
     * @param ex DOCUMENT ME!
     */
    public InstanceCreationException(Exception ex)
    {
        super(ex);
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
