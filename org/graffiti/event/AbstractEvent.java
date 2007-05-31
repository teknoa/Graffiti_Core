//==============================================================================
//
//   AbstractEvent.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: AbstractEvent.java,v 1.1 2007/05/31 12:56:04 klukas Exp $

package org.graffiti.event;

import java.util.EventObject;

/**
 * This class is merely meant to group all the Node-/Edge-/ etc. events. One
 * could imagine that common things could be added right there (for example
 * the timestamp of the event). Otherwise the class is empty.
 *
 * @version $Revision: 1.1 $
 */
public abstract class AbstractEvent
    extends EventObject
{
    //~ Constructors ===========================================================

    /**
     * Constructs an AbstractEvent with object o as source.
     *
     * @param o the object that is considered as source of the event.
     */
    public AbstractEvent(Object o)
    {
        super(o);
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
