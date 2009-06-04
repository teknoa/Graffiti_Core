/*******************************************************************************
 * 
 *    Copyright (c) 2003-2007 Network Analysis Group, IPK Gatersleben
 * 
 *******************************************************************************/
/*
 * Created on 04.11.2005 by Christian Klukas
 */
package org;

public class ObjectRef {

	private Object data;
	private String toStringVal = null; 
	
	public ObjectRef() {
		// empty
	}
	
	public ObjectRef(String toStringDef, Object initData) {
		this.toStringVal = toStringDef;
		this.data = initData;
	}
	
	public String toString() {
		if (toStringVal==null)
			return super.toString();
		else
			return toStringVal;
	}

	public synchronized void setObject(Object data) {
		this.data = data;
	}

	public synchronized Object getObject() {
		return data;
	}
}
