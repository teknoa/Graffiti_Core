package org.graffiti.plugin.parameter;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ListCellRenderer;

/**
 * @author klukas
 *
 * @version $Revision: 1.4 $
 */
public class ObjectListParameter
    extends AbstractSingleParameter
{
   @SuppressWarnings("unchecked")
   private Collection possibleValues;
   private ListCellRenderer renderer;
   private boolean left_aligned;

	@SuppressWarnings("unchecked")
	public ObjectListParameter(Object val, String name, String description, Collection possibleValues) {
		super(val, name, description);
		this.possibleValues = possibleValues;
	}

	public ObjectListParameter(Object val, String name, String description, Object[] values) {
		super(val, name, description);
		ArrayList<Object> va = new ArrayList<Object>();
		for (Object o : values)
			va.add(o);
		this.possibleValues = va;
	}

	@SuppressWarnings("unchecked")
	public Collection getPossibleValues() {
		return possibleValues;
	}

	public void setRenderer(ListCellRenderer renderer) {
		this.renderer = renderer;
	}
	
	public ListCellRenderer getRenderer() {
		return renderer;
	}

	public boolean isLeftAligned() {
		return left_aligned;
	}
	
	public void setLeftAligned(boolean left_aligned) {
		this.left_aligned = left_aligned;
	}
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
