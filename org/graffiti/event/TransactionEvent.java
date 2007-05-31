//==============================================================================
//
//   TransactionEvent.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: TransactionEvent.java,v 1.1 2007/05/31 12:56:05 klukas Exp $

package org.graffiti.event;

import java.util.Set;

/**
 * Contains a transaction event. A <code>TransactionEvent</code> object is
 * passed to every <code>TransactionListener</code> object which is registered
 * to receive a transaction event.
 *
 * @version $Revision: 1.1 $
 *
 * @see TransactionListener
 */
public class TransactionEvent
    extends AbstractEvent
{
    //~ Instance fields ========================================================

    private static final long serialVersionUID = 1L;
    /**
     * Contains the objects that have been changed during the lifetime of a
     * transaction.
     */
    private Set changedObjects;

    //~ Constructors ===========================================================

    /**
     * Constructs a transaction event object with the specified source
     * component.
     *
     * @param source the source component of the transaction.
     * @param changedObjects DOCUMENT ME!
     */
    public TransactionEvent(Object source, Set changedObjects)
    {
        this(source);
        this.changedObjects = changedObjects;
    }

    /**
     * Constructs a transaction event object with the specified source
     * component.
     *
     * @param source the graph that originated the event.
     */
    public TransactionEvent(Object source)
    {
        super(source);
        changedObjects = null;
    }

    //~ Methods ================================================================

    /**
     * Returns the <code>Set</code> of objects that have been changed during
     * the transaction.
     *
     * @return the graph that originated this event.
     */
    public Set getChangedObjects()
    {
        return changedObjects;
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
