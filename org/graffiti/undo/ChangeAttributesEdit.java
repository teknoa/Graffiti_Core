//==============================================================================
//
//   ChangeAttributesEdit.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: ChangeAttributesEdit.java,v 1.1 2007/05/31 12:56:09 klukas Exp $

package org.graffiti.undo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.graffiti.attributes.Attribute;
import org.graffiti.attributes.AttributeNotFoundException;

import org.graffiti.graph.GraphElement;

/**
 * ChangeAttributesEdit
 *
 * @author wirch
 * @version $Revision: 1.1 $
 */
public class ChangeAttributesEdit
    extends GraffitiAbstractUndoableEdit
{
    //~ Static fields/initializers =============================================

    /** The logger for the current class. */
    private static final Logger logger = Logger.getLogger(ChangeAttributesEdit.class.getName());

    //~ Instance fields ========================================================

    /** map from an attribute to its old value */
    private Map attributeToOldValueMap;

    //~ Constructors ===========================================================

    /**
     * Creates a new <code>AttributeChangeEdit</code> object.
     *
     * @param attributeToOldValueMap map between an attribute and its old
     *        value.
     * @param geMap map between the old graph elements and the new ones.
     */
    public ChangeAttributesEdit(Map attributeToOldValueMap, Map geMap)
    {
        super(geMap);
        this.attributeToOldValueMap = attributeToOldValueMap;

        logger.finer("Attribute to old Value Map contains " +
            attributeToOldValueMap.size() + " key-value-entries.");
    }

    /**
     * Creates a new <code>AttributeChangeEdit</code> object. It is usefull if
     * only one attribute such as coordinate of a bend has been changed.
     *
     * @param attribute the changed attribute.
     * @param geMap map between the old graph elements and the new ones.
     */
    public ChangeAttributesEdit(Attribute attribute, Map geMap)
    {
        super(geMap);
        this.attributeToOldValueMap = new HashMap();
        this.attributeToOldValueMap.put(attribute,
            ((Attribute) attribute.copy()).getValue());
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

            logger.finer("The new graph element of the old attribute is " +
                newGraphElement.toString());
            logger.finer("Attributable of this old attribute is " +
                attribute.getAttributable().toString());
            logger.finer("The path of the old attribute is " +
                attribute.getPath());

            Attribute newAttribute;

            try
            {
                // DEBUG:
                newAttribute = newGraphElement.getAttribute(attribute.getPath());
                logger.finer("The path of the new attribute is " +
                    newAttribute.getPath());

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
                    logger.info("New attribute is recognized.");
                }
            }
            catch(AttributeNotFoundException e)
            {
                logger.finer("Attribute with path " + attribute.getPath() +
                    " not found at " + newGraphElement.toString());
            }
        }

        if(!attributesMap.isEmpty())
        {
            logger.finer("attributesMap isn't empty.");

            for(Iterator iterator = attributesMap.keySet().iterator();
                iterator.hasNext();)
            {
                Attribute attribute = (Attribute) iterator.next();
                newValue = attributeToOldValueMap.get(attribute);
                logger.finer("The old attribute is of type " +
                    attribute.getClass().toString());
                logger.finer("The new Value is " + newValue);

                Attribute newAttribute = (Attribute) attributesMap.get(attribute);
                logger.finer("The old attribute is of type " +
                    newAttribute.getClass().toString());

                // TODO:fix finally the access to the attribute values
                // over the getValue(). 
                // It is currently only a temporary solution for nonfixed 
                // access.
                oldValue = ((Attribute) newAttribute.copy()).getValue();

                //oldValue = newAttribute.getValue();
                logger.finer("The old value is " + oldValue);
                newAttribute.setValue(newValue);
                logger.finer("The saved value of new attribute is " +
                    newAttribute.getValue().toString());

                attributeToOldValueMap.remove(attribute);
                attributeToOldValueMap.put(newAttribute, oldValue);
            }
        }
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
