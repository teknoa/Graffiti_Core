package org.graffiti.attributes;

public class AttributeDescription {

	private String id;
	@SuppressWarnings("unchecked")
	private Class attributeClass;
	private String user_description;
	
	private boolean isNodeDesc, isEdgeDesc;

	@SuppressWarnings("unchecked")
	public AttributeDescription(String id, Class attributeClass, String user_description, boolean isNodeDesc, boolean isEdgeDesc) {
		this.setId(id);
		this.setAttributeClass(attributeClass);
		this.setUser_description(user_description);
		
		this.isNodeDesc = isNodeDesc;
		this.isEdgeDesc = isEdgeDesc;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@SuppressWarnings("unchecked")
	public void setAttributeClass(Class attributeClass) {
		this.attributeClass = attributeClass;
	}

	@SuppressWarnings("unchecked")
	public Class getAttributeClass() {
		return attributeClass;
	}

	public void setUser_description(String user_description) {
		this.user_description = user_description;
	}

	public String getUser_description() {
		return user_description;
	}

	public boolean isNodeAttributeDescription() {
		return isNodeDesc;
	}

	public boolean isEdgeAttributeDescription() {
		return isEdgeDesc;
	}

}
