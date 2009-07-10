//==============================================================================
//
//   TransactionListener.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: TransactionListener.java,v 1.2 2009/07/10 08:17:44 klukas Exp $

package org.graffiti.event;

import java.util.EventListener;

import org.BackgroundTaskStatusProviderSupportingExternalCall;

/**
 * Interface, that contains methods which are called when transactions are
 * started or finished.
 *
 * @version $Revision: 1.2 $
 */
public interface TransactionListener
    extends EventListener
{
    //~ Methods ================================================================

    /**
     * Called when a transaction has stopped.
     *
     * @param e the EdgeEvent detailing the changes.
     */
    public void transactionFinished(TransactionEvent e, BackgroundTaskStatusProviderSupportingExternalCall status);

    /**
     * Called when a transaction has started.
     *
     * @param e the EdgeEvent detailing the changes.
     */
    public void transactionStarted(TransactionEvent e);
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
