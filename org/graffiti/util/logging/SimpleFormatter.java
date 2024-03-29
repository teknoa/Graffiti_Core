// ==============================================================================
//
// SimpleFormatter.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================
// $Id: SimpleFormatter.java,v 1.5 2010/12/22 13:05:34 klukas Exp $

package org.graffiti.util.logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Provides a brief summary of the LogRecord in a human readable format.
 * 
 * @version $Revision: 1.5 $
 */
public class SimpleFormatter
					extends Formatter {
	// ~ Methods ================================================================
	
	/**
	 * Returns the message of the given <code>LogRecord</code>
	 * 
	 * @param record
	 *           DOCUMENT ME!
	 * @return a human readable string of the log record's message.
	 */
	@Override
	public String format(LogRecord record) {
		return record.getMessage() + "\n";
	}
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
