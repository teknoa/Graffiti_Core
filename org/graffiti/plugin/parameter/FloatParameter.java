//==============================================================================
//
//   FloatParameter.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: FloatParameter.java,v 1.1 2007/05/31 12:56:07 klukas Exp $

package org.graffiti.plugin.parameter;

/**
 * Parameter that contains a float value.
 *
 * @version $Revision: 1.1 $
 */
public class FloatParameter
    extends AbstractLimitableParameter
{
    //~ Instance fields ========================================================

    /** The value of this parameter. */
    private Float value = null;

    //~ Constructors ===========================================================

    /**
     * Constructs a new float parameter.
     *
     * @param name the name of the parameter.
     * @param description the description of the parameter.
     */
    public FloatParameter(String name, String description)
    {
        super(name, description);
    }

    //~ Methods ================================================================

    /**
     * Returns the value of this parameter as a <code>Float</code>.
     *
     * @return the value of this parameter as a <code>Float</code>.
     */
    public Float getFloat()
    {
        return value;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Comparable getMax()
    {
        return null; // TODO
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Comparable getMin()
    {
        return null; // TODO
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isValid()
    {
        return false; // TODO
    }

    /**
     * Sets the value of the <code>AttributeParameter</code>.
     *
     * @param value the new value of the <code>AttributeParameter</code>.
     */
    public void setValue(Object value)
    {
        // TODO
    }

    /**
     * Returns the value of this parameter.
     *
     * @return the value of this parameter.
     */
    public Object getValue()
    {
        return value;
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
