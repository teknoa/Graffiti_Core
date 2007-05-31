package org.graffiti.plugin.parameter;

import java.util.Collection;
import java.util.List;


/**
 * @author klukas
 *
 * @version $Revision: 1.1 $
 */
public class ObjectListParameter
    extends AbstractSingleParameter
{
   private Collection possibleValues;

	public ObjectListParameter(Object val, String name, String description, Collection possibleValues) {
		super(val, name, description);
		this.possibleValues = possibleValues;
	}

	public Collection getPossibleValues() {
		return possibleValues;
	}
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
