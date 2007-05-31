//==============================================================================
//
//   ListenerNotFoundException.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: ListenerNotFoundException.java,v 1.1 2007/05/31 12:56:04 klukas Exp $

package org.graffiti.event;

/**
 * Will be thrown, if a method tries to deal with a listener that cannot be
 * found in the listener list.
 *
 * @version $Revision: 1.1 $
 */
public class ListenerNotFoundException
    extends Exception
{
    //~ Constructors ===========================================================

    /**
     * Constructs a <code>ListenerNotFoundException</code>  with the specified
     * detail message.
     *
     * @param msg the detail message for the exception.
     */
    public ListenerNotFoundException(String msg)
    {
        super(msg);
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
