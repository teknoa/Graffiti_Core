//==============================================================================
//
//   ParserException.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: ParserException.java,v 1.1 2007/05/31 12:56:00 klukas Exp $

package org.graffiti.plugin.io;

import java.io.IOException;

/**
 * ParserException will be thrown whenever an error occurs while reading in a
 * graph.
 *
 * @see java.io.IoException
 */
public class ParserException
    extends IOException
{
    //~ Constructors ===========================================================

    /**
     * Constructs a new <code>ParserException</code>.
     *
     * @param message the message for this exception.
     */
    public ParserException(String message)
    {
        super(message);
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
