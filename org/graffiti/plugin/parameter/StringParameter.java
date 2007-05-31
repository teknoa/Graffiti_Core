//==============================================================================
//
//   StringParameter.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: StringParameter.java,v 1.1 2007/05/31 12:56:07 klukas Exp $

package org.graffiti.plugin.parameter;

/**
 * Parameter that contains an <code>Integer</code> value.
 *
 * @version $Revision: 1.1 $
 */
public class StringParameter
    extends AbstractSingleParameter
{
    //~ Instance fields ========================================================

    /** The value of this parameter. */
    private String value = null;

    //~ Constructors ===========================================================

    /**
     * Constructs a new integer parameter.
     *
     * @param value the new integer value. May be null.
     * @param name the name of the parameter.
     * @param description the description of the parameter.
     */
    public StringParameter(String value, String name, String description)
    {
        super(name, description);
        this.value = value;
    }

    //~ Methods ================================================================

    /**
     * Returns the value of this parameter as an <code>String</code>.
     *
     * @return the value of this parameter as an <code>String</code>.
     */
    public String getString()
    {
        return value;
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
            this.value = (String) value;
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
        return getStandardXML(value.toString());
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
