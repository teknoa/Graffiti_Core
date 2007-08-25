//==============================================================================
//
//   ChangeAttributesEdit.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: ChangeAttributesEdit.java,v 1.2 2007/08/25 12:18:34 klukas Exp $

package org.graffiti.undo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.ErrorMsg;
import org.graffiti.attributes.Attribute;
import org.graffiti.attributes.AttributeNotFoundException;

import org.graffiti.graph.Graph;
import org.graffiti.graph.GraphElement;

/**
 * ChangeAttributesEdit
 *
 * @author wirch
 * @version $Revision: 1.2 $
 */
public class ChangeAttributesEdit
    extends GraffitiAbstractUndoableEdit
{
	private static final long serialVersionUID = 1L;

    //~ Instance fields ========================================================

    /** map from an attribute to its old value */
    private Map attributeToOldValueMap;
    
    private Graph g = null;

    //~ Constructors ===========================================================

    /**
     * Creates a new <code>AttributeChangeEdit</code> object.
     *
     * @param attributeToOldValueMap map between an attribute and its old
     *        value.
     * @param geMap map between the old graph elements and the new ones.
     */
    public ChangeAttributesEdit(Graph graph, Map attributeToOldValueMap, Map geMap)
    {
        super(geMap);
        this.attributeToOldValueMap = attributeToOldValueMap;
        this.g = graph;
    }

    /**
     * Creates a new <code>AttributeChangeEdit</code> object. It is usefull if
     * only one attribute such as coordinate of a bend has been changed.
     *
     * @param attribute the changed attribute.
     * @param geMap map between the old graph elements and the new ones.
     */
    public ChangeAttributesEdit(Graph graph, Attribute attribute, Map geMap)
    {
        super(geMap);
        this.attributeToOldValueMap = new HashMap();
        this.attributeToOldValueMap.put(attribute,
            ((Attribute) attribute.copy()).getValue());
        this.g = graph;
    }

    //~ Methods ================================================================

    /**
     * @see javax.swing.undo.UndoableEdit#getPresentationName()
     */
    public String getPresentationName()
    {
        String name = "";

        if(attributeToOldValueMap.size() == 1)
        {
            name = sBundle.getString("undo.changeAttribute") + " " +
                ((Attribute) attributeToOldValueMap.keySet().iterator().next()).getName();
        }
        else if(attributeToOldValueMap.size() > 1)
        {
            name = sBundle.getString("undo.changeAttributes");
        }

        return name;
    }

    /*
     * @see org.graffiti.undo.GraffitiAbstractUndoableEdit#execute()
     */
    public void execute()
    {
        //do nothing
    }

    /**
     * @see javax.swing.undo.UndoableEdit#redo()
     */
    public void redo()
    {
        super.redo();
        changeValues();
    }

    /**
     * @see javax.swing.undo.UndoableEdit#undo()
     */
    public void undo()
    {
        super.undo();
        changeValues();
    }

    /**
     * Changes attribute value to the old ones during undo or redo operations.
     */
    private void changeValues()
    {
    	try {
    		g.getListenerManager().transactionStarted(this);
	        Object newValue = null;
	        Object oldValue = null;
	
	        /* maps from an old attribute: attribute belonged to
	         * a deleted graph element - to a new possibly created attribute
	         * at a new graph element
	         */
	        HashMap attributesMap = new LinkedHashMap();
	
	        for(Iterator iter = attributeToOldValueMap.keySet().iterator();
	            iter.hasNext();)
	        {
	            Attribute attribute = (Attribute) iter.next();
	            GraphElement newGraphElement = getNewGraphElement((GraphElement) attribute.getAttributable());
	
	            Attribute newAttribute;
	
	            try
	            {
	                // DEBUG:
	                newAttribute = newGraphElement.getAttribute(attribute.getPath());
	                if(attribute == newAttribute)
	                {
	                    newValue = attributeToOldValueMap.get(attribute);
	
	                    // TODO:fix finally the access to the attribute values
	                    // over the getValue(). 
	                    // It is currently only a temporary solution for nonfixed 
	                    // access. 
	                    oldValue = ((Attribute) attribute.copy()).getValue();
	
	                    //oldValue = attribute.getValue().;
	                    attribute.setValue(newValue);
	
	                    attributeToOldValueMap.put(attribute, oldValue);
	                }
	                else
	                {
	                    attributesMap.put(attribute, newAttribute);
	                }
	            }
	            catch(AttributeNotFoundException e)
	            {
	            	ErrorMsg.addErrorMessage(e);
	            }
	        }
	
	        if(!attributesMap.isEmpty())
	        {
	            for(Iterator iterator = attributesMap.keySet().iterator();
	                iterator.hasNext();)
	            {
	                Attribute attribute = (Attribute) iterator.next();
	                newValue = attributeToOldValueMap.get(attribute);
	
	                Attribute newAttribute = (Attribute) attributesMap.get(attribute);
	
	                // TODO:fix finally the access to the attribute values
	                // over the getValue(). 
	                // It is currently only a temporary solution for nonfixed 
	                // access.
	                oldValue = ((Attribute) newAttribute.copy()).getValue();
	
	                //oldValue = newAttribute.getValue();
	                newAttribute.setValue(newValue);
	
	                attributeToOldValueMap.remove(attribute);
	                attributeToOldValueMap.put(newAttribute, oldValue);
	            }
	        }
    	} catch(Exception e) {
    		ErrorMsg.addErrorMessage(e);
    	} finally {
    		g.getListenerManager().transactionFinished(this);
    	}
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
