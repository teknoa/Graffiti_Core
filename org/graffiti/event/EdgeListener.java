// ==============================================================================
//
// EdgeListener.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================
// $Id: EdgeListener.java,v 1.4 2010/12/22 13:05:34 klukas Exp $

package org.graffiti.event;

/**
 * Interface that contains methods which are called when an edge is changed.
 * 
 * @version $Revision: 1.4 $
 */
public interface EdgeListener
					extends TransactionListener {
	// ~ Methods ================================================================
	
	/**
	 * Called after the edge was set directed or undirected.
	 * 
	 * @param e
	 *           the EdgeEvent detailing the changes.
	 */
	public void postDirectedChanged(EdgeEvent e);
	
	/**
	 * Called after the edge has been reversed.
	 * 
	 * @param e
	 *           the EdgeEvent detailing the changes.
	 */
	public void postEdgeReversed(EdgeEvent e);
	
	/**
	 * Called after the source node of an edge has changed.
	 * 
	 * @param e
	 *           the EdgeEvent detailing the changes.
	 */
	public void postSourceNodeChanged(EdgeEvent e);
	
	/**
	 * Called after the target node of an edge has changed.
	 * 
	 * @param e
	 *           the EdgeEvent detailing the changes.
	 */
	public void postTargetNodeChanged(EdgeEvent e);
	
	/**
	 * Called before the edge is set directed or undirected.
	 * 
	 * @param e
	 *           the EdgeEvent detailing the changes.
	 */
	public void preDirectedChanged(EdgeEvent e);
	
	/**
	 * Called before the edge is going to be reversed.
	 * 
	 * @param e
	 *           the EdgeEvent detailing the changes.
	 */
	public void preEdgeReversed(EdgeEvent e);
	
	/**
	 * Called before a change of the source node of an edge takes place.
	 * 
	 * @param e
	 *           the EdgeEvent detailing the changes.
	 */
	public void preSourceNodeChanged(EdgeEvent e);
	
	/**
	 * Called before a change of the target node of an edge takes place.
	 * 
	 * @param e
	 *           the EdgeEvent detailing the changes.
	 */
	public void preTargetNodeChanged(EdgeEvent e);
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
