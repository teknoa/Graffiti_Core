//==============================================================================
//
//   BooleanParameter.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: BooleanParameter.java,v 1.1 2007/05/31 12:56:08 klukas Exp $

package org.graffiti.plugin.parameter;

/**
 * Parameter that contains a <code>Boolean</code> value.
 *
 * @version $Revision: 1.1 $
 */
public class BooleanParameter
    extends AbstractSingleParameter
{
    //~ Instance fields ========================================================

    /** The value of this parameter. */
    private Boolean value = null;

    //~ Constructors ===========================================================

    /**
     * Constructs a new boolean parameter.
     *
     * @param value the new Boolean value. May be null.
     * @param name the name of the parameter.
     * @param description the description of the parameter.
     */
    public BooleanParameter(Boolean value, String name, String description)
    {
        super(name, description);
        this.value = value;
    }

    /**
     * Constructs a new boolean parameter.
     *
     * @param value the new boolean value..
     * @param name the name of the parameter.
     * @param description the description of the parameter.
     */
    public BooleanParameter(boolean value, String name, String description)
    {
        super(name, description);
        this.value = new Boolean(value);
    }

    //~ Methods ================================================================

    /**
     * Returns the value of this parameter as a <code>Boolean</code>.
     *
     * @return the value of this parameter as a <code>Boolean</code>.
     */
    public Boolean getBoolean()
    {
        return value;
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

        return true;
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
            this.value = (Boolean) value;
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

    /**
     * @see org.graffiti.plugin.parameter.Parameter#toXMLString()
     */
    public String toXMLString() {
        return getStandardXML(value.booleanValue() ? "true" : "false");
    }

}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
