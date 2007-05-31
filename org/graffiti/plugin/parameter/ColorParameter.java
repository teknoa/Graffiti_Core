/**
 * @author klukas
 */
package org.graffiti.plugin.parameter;

import java.awt.Color;

import org.ErrorMsg;

public class ColorParameter
    extends AbstractSingleParameter
{
    private Color value = null;

    public ColorParameter(Color value, String name, String description)
    {
        super(name, description);
        this.value = value;
    }

    public Color getColor()
    {
        return value;
    }

    public boolean isValid()
    {
        if(value == null)
        {
            return false;
        }

        return true;
    }

    public void setValue(Object value)
    {
        try
        {
            this.value = (Color) value;
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
        return getStandardXML(ErrorMsg.getHexFromColor(value));
    }

}