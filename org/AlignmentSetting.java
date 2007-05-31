/*******************************************************************************
 * 
 *    Copyright (c) 2003-2007 Network Analysis Group, IPK Gatersleben
 * 
 *******************************************************************************/

package org;

public enum AlignmentSetting {
	AUTO_OUTSIDE, BELOW, ABOVE, RIGHT, LEFT, INSIDEBOTTOM, INSIDETOP,
	CENTERED, BELOWRIGHT, BELOWLEFT, ABOVELEFT, ABOVERIGHT, NEARSOURCE, 
	NEARTARGET;
	
	public String toString() {
		switch(this) {
			case AUTO_OUTSIDE: return "auto_outside";
			case BELOW : return "s";
			case ABOVE : return "n";
			case RIGHT : return "e";
			case LEFT : return "w";
			case INSIDEBOTTOM : return "b";
			case INSIDETOP : return "t";
			case CENTERED : return "c";
			case BELOWRIGHT : return "se";
			case BELOWLEFT : return "sw";
			case ABOVELEFT : return "nw";
			case ABOVERIGHT : return "ne";
			case NEARSOURCE : return "nearsource";
			case NEARTARGET : return "neartarget";
		}
		return null;
	}
}
