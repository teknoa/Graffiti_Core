/*******************************************************************************
 * 
 *    Copyright (c) 2003-2007 Network Analysis Group, IPK Gatersleben
 * 
 *******************************************************************************/

package org;

public enum LabelFrameSetting {
	NO_FRAME, 
	RECTANGLE, ELLIPSE, 
	CAPSULE, 
	RECTANGLE_ROUNDED, RECTANGLE_BOTTOM_ROUND, RECTANGLE_CORNER_CUT, 
	CIRCLE, CIRCLE_HALF_FILLED, CIRCLE_FILLED, 
	PIN, 
	SIDE_LINES;

	@Override
	public String toString() {
		switch(this) {
			case NO_FRAME  					: return "no frame";
			case RECTANGLE 					: return "rectangle";
			case ELLIPSE 					: return "ellipse";
			case CAPSULE					: return "capsule";
			case RECTANGLE_ROUNDED			: return "rectangle rounded";
			case RECTANGLE_BOTTOM_ROUND		: return "rectangle bottom rounded";
			case RECTANGLE_CORNER_CUT		: return "rectangle corner cut";
			case CIRCLE						: return "circle";
			case CIRCLE_HALF_FILLED			: return "circle half filled";
			case CIRCLE_FILLED				: return "circle filled";
			case PIN						: return "pin";
			case SIDE_LINES					: return "side lines";
			default 						: return null;
		}
	}
	
	public String toGMLstring() {
		switch(this) {
			case NO_FRAME  					: return "";
			case RECTANGLE 					: return "box";
			case ELLIPSE 					: return "oval";
			case CAPSULE					: return "capsule";
			case RECTANGLE_ROUNDED			: return "roundrect";
			case RECTANGLE_BOTTOM_ROUND		: return "roundrect2";
			case RECTANGLE_CORNER_CUT		: return "cutrect";
			case CIRCLE						: return "circle";
			case CIRCLE_HALF_FILLED			: return "hcircle";
			case CIRCLE_FILLED				: return "fcircle";
			case PIN						: return "pin";
			case SIDE_LINES					: return "lines";
			default 						: return null;
		}
	}
}
