/*******************************************************************************
 * 
 *    Copyright (c) 2003-2009 Plant Bioinformatics Group, IPK Gatersleben
 * 
 *******************************************************************************/
/*
 * Created on Feb 24, 2010 by Christian Klukas
 */

package org.color;

import java.awt.Color;

/**
 * @author klukas
 *
 */
public class ColorXYZ {
	
	double x,y,z;

	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public ColorXYZ(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * @param c1
	 */
	public ColorXYZ(Color c) {
		int r,g,b;
		r = c.getRed();
		g = c.getGreen();
		b = c.getBlue();
		ColorXYZ xyz = ColorUtil.colorRGB2XYZ(r, g, b);
		x = xyz.x;
		y = xyz.y;
		z = xyz.z;
	}

	/**
	 * @return
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return
	 */
	public double getY() {
		return y;
	}

	/**
	 * @return
	 */
	public double getZ() {
		return z;
	}

}
