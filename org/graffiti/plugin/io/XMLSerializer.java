//==============================================================================
//
//   XMLSerializer.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: XMLSerializer.java,v 1.3 2009/06/23 07:05:21 klukas Exp $

package org.graffiti.plugin.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

import org.graffiti.attributes.BooleanAttribute;
import org.graffiti.attributes.ByteAttribute;
import org.graffiti.attributes.CompositeAttribute;
import org.graffiti.attributes.DoubleAttribute;
import org.graffiti.attributes.FloatAttribute;
import org.graffiti.attributes.IntegerAttribute;
import org.graffiti.attributes.LongAttribute;
import org.graffiti.attributes.ShortAttribute;
import org.graffiti.attributes.StringAttribute;
import org.graffiti.graph.Edge;
import org.graffiti.graph.Graph;
import org.graffiti.graph.Node;

/**
 * Reads and Writes a graph in XML.  TODO: specify the XML format.
 *
 * @version $Revision: 1.3 $
 */
public class XMLSerializer
    extends AbstractIOSerializer
{
    //~ Methods ================================================================

    /**
     * Returns the extensions the serializer provides.
     *
     * @return DOCUMENT ME!
     */
    public String[] getExtensions()
    {
        return null;
    }
    
    /* (non-Javadoc)
     * @see org.graffiti.plugin.io.Serializer#getFileTypeDescriptions()
     */
    public String[] getFileTypeDescriptions() {
        return null;
    }

    /**
     * Reads in a graph from the given input stream.
     *
     * @param in The input stream to read the graph from.
     * @param g The graph to add the newly read graph to.
     */
    @Override
	public void read(InputStream in, Graph g)
    {
        // TODO
    }

    /**
     * Writes the contents of the given graph to a stream.
     *
     * @param stream The stream to save the graph to.
     * @param g The graph to save.
     */
    public void write(OutputStream stream, Graph g)
    {
        // TODO
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private BooleanAttribute loadBooleanAttribute()
    {
        return null;
    }

    /**
     * Loads an user-defined attribute. <b>Implementation Notes:</b> The user
     * has to provide a function that builds an user-defined attribute out of
     * the given base-hierarchie. The class that contains that function has to
     * be registered in the AttributeTypesManager.
     *
     * @return DOCUMENT ME!
     */
    private CompositeAttribute loadCompositeAttribute()
    {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private Edge loadEdge()
    {
        return null;
    }

    /**
     * private functions for loading standard attributes
     *
     * @return DOCUMENT ME!
     */
    private FloatAttribute loadFloatAttribute()
    {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private IntegerAttribute loadIntegerAttribute()
    {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private Node loadNode()
    {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private StringAttribute loadStringAttribute()
    {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param ba DOCUMENT ME!
     */
    private void saveBooleanAttribute(BooleanAttribute ba)
    {
    }

    /**
     * DOCUMENT ME!
     *
     * @param fa DOCUMENT ME!
     */
    private void saveByteAttribute(FloatAttribute fa)
    {
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private ByteAttribute saveByteAttribute()
    {
        return null;
    }

    /**
     * Saves an user-defined attribute <b>Impelementation Notes:</b> Has to
     * call <code>getAttributes</code> of the specific user-class in order to
     * get the base-hierarchie representation of the attribute that can be
     * saved.
     *
     * @param ca The user-defined attribute to be saved
     */
    private void saveCompositeAttribute(CompositeAttribute ca)
    {
    }

    /**
     * DOCUMENT ME!
     *
     * @param fa DOCUMENT ME!
     */
    private void saveDoubleAttribute(FloatAttribute fa)
    {
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private DoubleAttribute saveDoubleAttribute()
    {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param e DOCUMENT ME!
     */
    private void saveEdge(Edge e)
    {
    }

    /**
     * private functions for saving standard attributes
     *
     * @param fa DOCUMENT ME!
     */
    private void saveFloatAttribute(FloatAttribute fa)
    {
    }

    /**
     * DOCUMENT ME!
     *
     * @param ia DOCUMENT ME!
     */
    private void saveIntegerAttribute(IntegerAttribute ia)
    {
    }

    /**
     * DOCUMENT ME!
     *
     * @param fa DOCUMENT ME!
     */
    private void saveLongAttribute(FloatAttribute fa)
    {
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private LongAttribute saveLongAttribute()
    {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param n DOCUMENT ME!
     */
    private void saveNode(Node n)
    {
    }

    /**
     * DOCUMENT ME!
     *
     * @param fa DOCUMENT ME!
     */
    private void saveShortAttribute(FloatAttribute fa)
    {
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private ShortAttribute saveShortAttribute()
    {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param sa DOCUMENT ME!
     */
    private void saveStringAttribute(StringAttribute sa)
    {
    }

    /* (non-Javadoc)
     * @see org.graffiti.plugin.io.InputSerializer#read(java.io.InputStream)
     */
    @Override
	public Graph read(InputStream in) {
        // TODO Auto-generated method stub
        return null;
    }

	public void read(Reader reader, Graph newGraph) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public boolean validFor(InputStream reader) {
		// TODO Auto-generated method stub
		return false;
	}
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
