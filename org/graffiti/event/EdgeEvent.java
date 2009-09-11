//==============================================================================
//
//   EdgeEvent.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: EdgeEvent.java,v 1.2 2009/09/11 12:28:37 morla Exp $

package org.graffiti.event;

import org.graffiti.attributes.Attributable;
import org.graffiti.graph.Edge;

/**
 * Contains an edge event. An <code>EdgeEvent</code> object is passed to every
 * <code>EdgeListener</code> or <code>AbstractEdgeListener</code> object which
 * is registered to receive the "interesting" edge events using the
 * component's <code>addEdgeListener</code> method.
 * (<code>AbstractEdgeListener</code> objects implement the
 * <code>EdgeListener</code> interface.) Each such listener object gets an
 * <code>EdgeEvent</code> containing the edge event.
 *
 * @version $Revision: 1.2 $
 *
 * @see EdgeListener
 * @see AbstractEdgeListener
 */
public class EdgeEvent
    extends AbstractEvent
{
    //~ Constructors ===========================================================

    /**
     * Constructs an edge event object with the specified source component.
     *
     * @param edge the edge that originated the event.
     */
    public EdgeEvent(Edge edge)
    {
        super(edge);
    }

    //~ Methods ================================================================

    /**
     * Returns the originator of the event.
     *
     * @return the edge that has been changed by this event.
     */
    public Edge getEdge()
    {
        return (Edge) getSource();
    }

    public Attributable getAttributeable() {
		if (getEdge()!=null)
			return getEdge();
		return null;
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
