// ==============================================================================
//
// GenericPlugin.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================
// $Id: GenericPlugin.java,v 1.7 2010/12/22 13:05:34 klukas Exp $

package org.graffiti.plugin;

import javax.swing.ImageIcon;

import org.graffiti.attributes.AttributeDescription;
import org.graffiti.options.GravistoPreferences;
import org.graffiti.plugin.actions.URLattributeAction;
import org.graffiti.plugin.algorithm.Algorithm;
import org.graffiti.plugin.extension.Extension;
import org.graffiti.plugin.io.InputSerializer;
import org.graffiti.plugin.io.OutputSerializer;

/**
 * An interface for a generic plugin for the graffiti graph editor. Any plugin
 * which shall be able to be plugged into the editor must implement this
 * interface. <br>
 * Implementation note<br>
 * : The getter-Methods of this interface may not return <code>null</code>. If
 * you want to prevent yourself from implementing every such method you can
 * also extend the abstract class <code>GenericPluginAdapter</code>.
 */
public interface GenericPlugin {
	// ~ Methods ================================================================
	
	/**
	 * Returns the array of <code>org.graffiti.algorithm.Algorithm</code>s the
	 * plugin contains.
	 * 
	 * @return the array of <code>org.graffiti.algorithm.Algorithm</code>s the
	 *         plugin contains.
	 */
	public Algorithm[] getAlgorithms();
	
	/**
	 * Returns an array of Extensions the plugin contains.
	 * 
	 * @return A array of Extensions the plugin contains.
	 */
	public Extension[] getExtensions();
	
	/**
	 * Returns the attribute types provided by this plugin.
	 * 
	 * @return the attribute types provided by this plugin.
	 */
	@SuppressWarnings("unchecked")
	public Class[] getAttributes();
	
	public AttributeDescription[] getAttributeDescriptions();
	
	/**
	 * Returns the array containing the names of the plugin classes the current
	 * plugin depends on.
	 * 
	 * @return the array containing the names of the plugin classes the current
	 *         plugin depends on.
	 */
	public String[] getDependencies();
	
	/**
	 * Returns the icon of the plugin.
	 * 
	 * @return the icon of the plugin.
	 */
	public ImageIcon getIcon();
	
	/**
	 * Returns the input serializers the plugin provides.
	 * 
	 * @return the input serializers the plugin provides.
	 */
	public InputSerializer[] getInputSerializers();
	
	/**
	 * Returns the output serializers the plugin provides.
	 * 
	 * @return the output serializers the plugin provides.
	 */
	public OutputSerializer[] getOutputSerializers();
	
	/**
	 * States whether this class wants to be registered as a <code>SelectionListener</code>.
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean isSelectionListener();
	
	/**
	 * States whether this class wants to be registered as a <code>SessionListener</code>.
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean isSessionListener();
	
	/**
	 * States whether this class wants to be registered as a <code>ViewListener</code>, i.e. if it wants to get informed when
	 * another view in the same session becomes active. This method is not
	 * called when another session is activated. Implement <code>SessionListener</code> if you are interested in session changed
	 * events.
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean isViewListener();
	
	/**
	 * Returns the array of class names of the views, this plugin contains.
	 * 
	 * @return the array of class names this plugin contains.
	 */
	public String[] getViews();
	
	/**
	 * Runs configuration routines for the plugin, e.g. load preferences etc.
	 * 
	 * @param prefs
	 *           the 'plugins' preferences node.
	 */
	public void configure(GravistoPreferences prefs);
	
	/**
	 * The routines to perform before the editor will exit.
	 */
	public void doBeforeExit();
	
	/**
	 * Interrupts the running plugin.
	 */
	public void interrupt();
	
	/**
	 * States whether this class needs up-to-date information about the current
	 * editcomponents. If this method returns <code>true</code>, it must
	 * implement interface <code>NeedEditComponents</code>.
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean needsEditComponents();
	
	/**
	 * Stops a running plugin. Performs exit routines.
	 */
	public void stop();
	
	public String getDefaultView();
	
	public URLattributeAction[] getURLattributeActions();
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
