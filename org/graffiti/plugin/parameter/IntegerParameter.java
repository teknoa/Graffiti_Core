//==============================================================================
//
//   IntegerParameter.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: IntegerParameter.java,v 1.1 2007/05/31 12:56:08 klukas Exp $

package org.graffiti.plugin.parameter;

/**
 * Parameter that contains an <code>Integer</code> value.
 *
 * @version $Revision: 1.1 $
 */
public class IntegerParameter
    extends AbstractLimitableParameter
{
    //~ Instance fields ========================================================

    /** The maximum valid value of this parameter. */
    private Integer max = null;

    /** The minimum valid value of this parameter. */
    private Integer min = null;

    /** The value of this parameter. */
    private Integer value = null;

    //~ Constructors ===========================================================

    /**
     * Constructs a new integer parameter.
     *
     * @param value the new integer value. May be null.
     * @param min the minimum value.
     * @param max the maximum value.
     * @param name the name of the parameter.
     * @param description the description of the parameter.
     */
    public IntegerParameter(Integer value, Integer min, Integer max,
        String name, String description)
    {
        super(name, description);

        this.value = value;
        this.min = min;
        this.max = max;
    }

    /**
     * Constructs a new integer parameter.
     *
     * @param value the new integer value. May be null.
     * @param name the name of the parameter.
     * @param description the description of the parameter.
     */
    public IntegerParameter(Integer value, String name, String description)
    {
        super(name, description);
        this.value = value;
    }

    /**
     * Constructs a new integer parameter.
     *
     * @param value the new integer value.
     * @param name the name of the parameter.
     * @param description the description of the parameter.
     */
    public IntegerParameter(int value, String name, String description)
    {
        super(name, description);
        this.value = new Integer(value);
    }

    //~ Methods ================================================================

    /**
     * Returns the value of this parameter as an <code>Integer</code>.
     *
     * @return the value of this parameter as an <code>Integer</code>.
     */
    public Integer getInteger()
    {
        return value;
    }

    /**
     * Returns the maximum of the intervall.
     *
     * @return the maximum of the intervall.
     */
    public Comparable getMax()
    {
        return max;
    }

    /**
     * Returns the minimum of the intervall.
     *
     * @return the minimum of the intervall.
     */
    public Comparable getMin()
    {
        return min;
    }

    /**
     * Returns <code>true</code>, if the current value is valid.
     *
     * @return DOCUMENT ME!
     */
    public boolean isValid()
    {
        if(value == null)
        {
            return false;
        }

        if((min == null) && (max == null))
        {
            return true;
        }

        return ((min.compareTo(value) < 0) && (max.compareTo(value) > 0));
    }

    /**
     * Sets the value of the <code>AttributeParameter</code>.
     *
     * @param value the new value of the <code>AttributeParameter</code>.
     *
     * @exception IllegalArgumentException thrown if <code>value</code> is not
     *            of the correct type.
     */
    public void setValue(Object value)
    {
        try
        {
            this.value = (Integer) value;
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
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
