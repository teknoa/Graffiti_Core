//==============================================================================
//
//   DoubleParameter.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: DoubleParameter.java,v 1.1 2007/05/31 12:56:08 klukas Exp $

package org.graffiti.plugin.parameter;

/**
 * Represents a double parameter.
 *
 * @version $Revision: 1.1 $
 */
public class DoubleParameter
    extends AbstractLimitableParameter
{
    //~ Instance fields ========================================================

    /** The value of this parameter. */
    private Double value = null;

    //~ Constructors ===========================================================

    /**
     * Constructs a new double parameter.
     *
     * @param name the name of the parameter.
     * @param description the description of the parameter.
     */
    public DoubleParameter(String name, String description)
    {
        super(name, description);
    }

    /**
     * Constructs a new double parameter.
     *
     * @param val the value of the parameter
     * @param name the name of the parameter.
     * @param description the description of the parameter.
     */
    public DoubleParameter(double val, String name, String description)
    {
        super(name, description);
        value = new Double(val);
    }

    //~ Methods ================================================================

    /**
     * DOCUMENT ME!
     *
     * @param val DOCUMENT ME!
     */
    public void setDouble(Double val)
    {
        this.value = val;
    }

    /**
     * DOCUMENT ME!
     *
     * @param val DOCUMENT ME!
     */
    public void setDouble(double val)
    {
        this.value = new Double(val);
    }

    /**
     * Returns the value of this parameter as a <code>Double</code>.
     *
     * @return the value of this parameter as a <code>Double</code>.
     */
    public Double getDouble()
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
        this.value = (Double) value;
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
