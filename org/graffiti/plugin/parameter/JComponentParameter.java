package org.graffiti.plugin.parameter;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.List;

import javax.swing.JComponent;

import org.graffiti.plugin.parameter.AbstractSingleParameter;


/**
 * @author klukas
 *
 * @version $Revision: 1.1 $
 */
public class JComponentParameter
    extends AbstractSingleParameter
{
	private JComponent gui;
	
	public JComponentParameter(JComponent val, String name, String description) {
		super(null, name, description);
		this.gui = val;
	}

	@Override
	public Object getValue() {
		return gui;
	}
	
	
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
