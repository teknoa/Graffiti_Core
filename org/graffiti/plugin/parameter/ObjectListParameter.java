package org.graffiti.plugin.parameter;

import java.util.ArrayList;
import java.util.Collection;


/**
 * @author klukas
 *
 * @version $Revision: 1.2 $
 */
public class ObjectListParameter
    extends AbstractSingleParameter
{
   @SuppressWarnings("unchecked")
   private Collection possibleValues;

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
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
