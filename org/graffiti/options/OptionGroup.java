// ==============================================================================
//
// OptionGroup.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================
// $Id: OptionGroup.java,v 1.5 2010/12/22 13:05:35 klukas Exp $

package org.graffiti.options;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Represents an option pane group.
 * 
 * @version $Revision: 1.5 $
 */
public class OptionGroup {
	// ~ Instance fields ========================================================
	
	/** The name of this option group. */
	private String name;
	
	/** The list of members of this option group. */
	private Vector<Object> members;
	
	// ~ Constructors ===========================================================
	
	/**
	 * Constructs a new option group.
	 * 
	 * @param name
	 *           the name of the option group.
	 */
	public OptionGroup(String name) {
		this.name = name;
		members = new Vector<Object>();
	}
	
	// ~ Methods ================================================================
	
	/**
	 * Returns the member at the specified index.
	 * 
	 * @param index
	 *           the index of the member of interest.
	 * @return the member at the specified index. <code>null</code> else.
	 */
	public Object getMember(int index) {
		return ((index >= 0) && (index < members.size()))
							? members.elementAt(index) : null;
	}
	
	/**
	 * Returns the number of members.
	 * 
	 * @return the number of members.
	 */
	public int getMemberCount() {
		return members.size();
	}
	
	/**
	 * Returns the index of the specified member.
	 * 
	 * @param member
	 *           the object of interest.
	 * @return the index of the specified member.
	 */
	public int getMemberIndex(Object member) {
		return members.indexOf(member);
	}
	
	/**
	 * Returns an enumeration of all members in this option group.
	 * 
	 * @return an enumeration of all members on this option group.
	 */
	public Enumeration<Object> getMembers() {
		return members.elements();
	}
	
	/**
	 * Returns the name of the option group.
	 * 
	 * @return the name of the option group.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Adds the given option group to this option group.
	 * 
	 * @param group
	 *           the option group to add.
	 */
	public void addOptionGroup(OptionGroup group) {
		if (members.indexOf(group) != -1) {
			return;
		}
		
		members.addElement(group);
	}
	
	/**
	 * Adds the given option pane to this option group.
	 * 
	 * @param pane
	 *           the option pane to add.
	 */
	public void addOptionPane(OptionPane pane) {
		if (members.indexOf(pane) != -1) {
			return;
		}
		
		members.addElement(pane);
	}
	
	/**
	 * Calls the <code>save</code> methods of all members.
	 */
	public void save() {
		Enumeration<Object> enum2 = members.elements();
		
		while (enum2.hasMoreElements()) {
			Object elem = enum2.nextElement();
			
			try {
				if (elem instanceof OptionPane) {
					((OptionPane) elem).save(null);
				} else
					if (elem instanceof OptionGroup) {
						((OptionGroup) elem).save();
					}
			} catch (Throwable t) {
				// Log.log(Log.ERROR, elem, "Error saving option pane");
				// Log.log(Log.ERROR, elem, t);
			}
		}
	}
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
