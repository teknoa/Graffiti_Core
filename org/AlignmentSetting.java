/*******************************************************************************
 * 
 *    Copyright (c) 2003-2007 Network Analysis Group, IPK Gatersleben
 * 
 *******************************************************************************/

package org;

import org.graffiti.graphics.GraphicAttributeConstants;

public enum AlignmentSetting {
	AUTO_OUTSIDE, BELOW, ABOVE, RIGHT, LEFT, INSIDEBOTTOM, INSIDETOP,
	CENTERED, BELOWRIGHT, BELOWLEFT, ABOVELEFT, ABOVERIGHT, NEARSOURCE, 
	NEARTARGET, BORDER_TOP_LEFT, BORDER_TOP_CENTER, 
	BORDER_TOP_RIGHT, BORDER_BOTTOM_LEFT, BORDER_BOTTOM_CENTER, 
	BORDER_BOTTOM_RIGHT, BORDER_LEFT_TOP, BORDER_LEFT_CENTER, 
	BORDER_LEFT_BOTTOM, BORDER_RIGHT_TOP, BORDER_RIGHT_CENTER, 
	BORDER_RIGHT_BOTTOM;

	
	public String toString() {
		switch(this) {
			case AUTO_OUTSIDE: return "auto_outside";
			case BELOW : return GraphicAttributeConstants.BELOW;
			case ABOVE : return GraphicAttributeConstants.ABOVE;
			case RIGHT : return GraphicAttributeConstants.RIGHT;
			case LEFT : return GraphicAttributeConstants.LEFT;
			case INSIDEBOTTOM : return GraphicAttributeConstants.INSIDEBOTTOM;
			case INSIDETOP : return GraphicAttributeConstants.INSIDETOP;
			case CENTERED : return GraphicAttributeConstants.CENTERED;
			case BELOWRIGHT : return GraphicAttributeConstants.BELOWRIGHT;
			case BELOWLEFT : return GraphicAttributeConstants.BELOWLEFT;
			case ABOVELEFT : return GraphicAttributeConstants.ABOVELEFT;
			case ABOVERIGHT : return GraphicAttributeConstants.ABOVERIGHT;
			case NEARSOURCE : return "nearsource";
			case NEARTARGET : return "neartarget";
			case BORDER_TOP_LEFT  : return GraphicAttributeConstants.BORDER_TOP_LEFT;
			case BORDER_TOP_CENTER: return GraphicAttributeConstants.BORDER_TOP_CENTER;
			case BORDER_TOP_RIGHT : return GraphicAttributeConstants.BORDER_TOP_RIGHT;
			case BORDER_RIGHT_TOP : return GraphicAttributeConstants.BORDER_RIGHT_TOP;
			case BORDER_RIGHT_CENTER: return GraphicAttributeConstants.BORDER_RIGHT_CENTER;
			case BORDER_RIGHT_BOTTOM: return GraphicAttributeConstants.BORDER_RIGHT_BOTTOM;
			case BORDER_BOTTOM_LEFT: return GraphicAttributeConstants.BORDER_BOTTOM_LEFT;
			case BORDER_BOTTOM_CENTER: return GraphicAttributeConstants.BORDER_BOTTOM_CENTER;
			case BORDER_BOTTOM_RIGHT: return GraphicAttributeConstants.BORDER_BOTTOM_RIGHT;
			case BORDER_LEFT_TOP  : return GraphicAttributeConstants.BORDER_LEFT_TOP;
			case BORDER_LEFT_CENTER: return GraphicAttributeConstants.BORDER_LEFT_CENTER;
			case BORDER_LEFT_BOTTOM: return GraphicAttributeConstants.BORDER_LEFT_BOTTOM;
		}
		return null;
	}
}
