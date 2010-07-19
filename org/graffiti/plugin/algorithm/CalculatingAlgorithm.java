//==============================================================================
//
//   CalculatingAlgorithm.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: CalculatingAlgorithm.java,v 1.2 2010/07/19 13:00:48 morla Exp $

package org.graffiti.plugin.algorithm;

/**
 * After its execution a <code>CalculatingAlgorithm</code> has computed an
 * <code>AlgorithmResult</code>, that can be obtained through the
 * <code>getResult()</code> method.
 *
 * @version $Revision: 1.2 $
 *
 * @see AlgorithmResult
 * @see Algorithm
 */
public interface CalculatingAlgorithm
extends Algorithm
{
	//~ Methods ================================================================

	/**
	 * Returns the <code>AlgorithmResult</code> that was computed by the
	 * <code>Algorithm</code> during the last execution. If there was no
	 * previous execution it returns <code>null</code>.
	 *
	 * @return the <code>AlgorithmResult</code> computed in the previous
	 *         execution of the <code>Algorithm</code>.
	 */
	public AlgorithmResult getResult();

	/**
	 * @link aggregation
	 * @clientCardinality 1
	 * @clientCardinality 1
	 */

	/*#AlgorithmResult lnkAlgorithmResult;*/
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
