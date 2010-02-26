/*******************************************************************************
 * 
 *    Copyright (c) 2003-2009 Plant Bioinformatics Group, IPK Gatersleben
 * 
 *******************************************************************************/
/*
 * Created on Feb 24, 2010 by Christian Klukas
 */

package org.color;

/**
 * @author klukas
 *
 */
public class Color_CIE_Lab {
	private double l;
	private double a;
	private double b;

	public Color_CIE_Lab(double l, double a, double b) {
		this.setL(l);
		this.setA(a);
		this.setB(b);
	}

	/**
	 * @param l the l to set
	 */
	private void setL(double l) {
		this.l = l;
	}

	/**
	 * @return the l
	 */
	public double getL() {
		return l;
	}

	/**
	 * @param a the a to set
	 */
	public void setA(double a) {
		this.a = a;
	}

	/**
	 * @return the a
	 */
	public double getA() {
		return a;
	}

	/**
	 * @param b the b to set
	 */
	public void setB(double b) {
		this.b = b;
	}

	/**
	 * @return the b
	 */
	public double getB() {
		return b;
	}

}
