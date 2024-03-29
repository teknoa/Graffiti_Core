// ==============================================================================
//
// CompositeAttribute.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================
// $Id: CompositeAttribute.java,v 1.4 2010/12/22 13:05:32 klukas Exp $

package org.graffiti.attributes;

/**
 * Interfaces a composite attribute. All user-defined attributes should extend
 * CompositeAttribute.
 * 
 * @version $Revision: 1.4 $
 * @see Attribute
 */
public abstract class CompositeAttribute
					extends AbstractAttribute {
	// ~ Constructors ===========================================================
	
	/**
	 * Creates a new <code>CompositeAttribute</code> with the given id.
	 * 
	 * @param id
	 *           DOCUMENT ME!
	 */
	public CompositeAttribute(String id) {
		super(id);
	}
	
	// ~ Methods ================================================================
	
	/**
	 * Sets the given attribute to the given value. The attribute that is
	 * already location <code>path</code> is overwritten!
	 * 
	 * @param att
	 *           the <code>Attribute</code> containing the new value for the
	 *           value asociated with the given id.
	 * @param id
	 *           the id of the attribute to be set.
	 * @exception AttributeNotFoundException
	 *               if there is no value associated
	 *               with the given id.
	 * @exception IllegalArgumentException
	 *               if <code>att</code> has not the
	 *               appropriate type.
	 */
	public abstract void setAttribute(String id, Attribute att)
						throws AttributeNotFoundException, IllegalArgumentException;
	
	/**
	 * Transforms the <code>CompositeAttribute</code> into the basehierarchy.
	 * Maps relative paths to the apropriate 'standard attributes' (e.g.
	 * IntegerAttribute, StringAttribute etc.) of the composite attribute.
	 * This representation is intended to be read-only - changes to the <code>CollectionAttribute</code> have no effect on the values of this
	 * <code>CompositeAttribute</code>.
	 * 
	 * @return the values of the composite attribute in a CollectionAttribute.
	 */
	public abstract CollectionAttribute getAttributes();
	
	/**
	 * Returns the attribute with the given id. Proper ids can be obtained by
	 * calling <code>getAttributes()</code> and then traversing the contained <code>Attributes</code> and calling <code>getId()</code> on them. The
	 * returned <code>Attribute</code> is intended to be read-only. Changes to
	 * the attribute have no effect on the coresponding value in the <code>CompositeAttribute</code>.
	 * 
	 * @param id
	 *           the id of the attribute searched for.
	 * @return the attribute with the given id.
	 * @exception AttributeNotFoundException
	 *               if the searched attribute is not
	 *               found.
	 */
	public Attribute getAttribute(String id)
						throws AttributeNotFoundException {
		return getAttributes().getAttribute(id);
	}
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
