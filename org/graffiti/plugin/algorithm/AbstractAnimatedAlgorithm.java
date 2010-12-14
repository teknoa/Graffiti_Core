// ==============================================================================
//
// AbstractAnimatedAlgorithm.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================
// $Id: AbstractAnimatedAlgorithm.java,v 1.3 2010/12/14 07:02:25 morla Exp $

package org.graffiti.plugin.algorithm;

/**
 * Gives a standard implementation for the <code>execute()</code> method. This
 * implementation repeatedly calls <code>nextStep()</code> without pauses.
 */
public abstract class AbstractAnimatedAlgorithm
					extends AbstractAlgorithm
					implements AnimatedAlgorithm {
	// ~ Methods ================================================================

	/**
	 * Executes the whole algorithm as one big step. This is the standard
	 * implementation that calls <code>nextStep()</code> repeatedly without
	 * pauses inbetween.
	 */
	public void execute() {
	}
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
