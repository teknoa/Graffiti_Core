// ==============================================================================
//
// IOManager.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================
// $Id: IOManager.java,v 1.10 2014/11/10 00:04:55 klapperipk Exp $

package org.graffiti.managers;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Set;

import javax.swing.JFileChooser;

import org.graffiti.managers.pluginmgr.PluginManagerListener;
import org.graffiti.plugin.io.InputSerializer;
import org.graffiti.plugin.io.OutputSerializer;

/**
 * Handles the editor's IO serializers.
 * 
 * @version $Revision: 1.10 $
 */
public interface IOManager
		extends PluginManagerListener {
	// ~ Methods ================================================================
	
	/**
	 * Registers the given input serializer.
	 * 
	 * @param i
	 *           the new serializer to add.
	 */
	void addInputSerializer(InputSerializer i);
	
	/**
	 * Adds the given <code>IOManagerListener</code> to the list of io manager
	 * listeners.
	 * 
	 * @param ioManagerListener
	 *           the listener to add.
	 */
	void addListener(IOManagerListener ioManagerListener);
	
	/**
	 * Registers the given output serializer.
	 * 
	 * @param o
	 *           the new serializer to add.
	 */
	void addOutputSerializer(OutputSerializer o);
	
	/**
	 * Creates an instance of an input serializer from the given extension.
	 * 
	 * @return DOCUMENT ME!
	 * @throws FileNotFoundException
	 */
	InputSerializer createInputSerializer(InputStream is, String ext)
			throws IllegalAccessException, InstantiationException, FileNotFoundException;
	
	/**
	 * Modifies the given file chooser by registering file extensions from the
	 * input serializers.
	 * 
	 * @return DOCUMENT ME!
	 */
	JFileChooser createOpenFileChooser();
	
	/**
	 * Creates an instance of an output serializer from the given extension.
	 * 
	 * @return DOCUMENT ME!
	 */
	
	OutputSerializer createOutputSerializer(String ext)
			throws IllegalAccessException, InstantiationException;
	
	/**
	 * Creates an instance of an output serializer from the given extension and
	 * file type description.
	 * @return the output serializer
	 */
	OutputSerializer createOutputSerializer(String ext, String fileTypeDescription)
			throws IllegalAccessException, InstantiationException;
	
	/**
	 * Creates and returns a file open chooser dialog with the registered file
	 * extensions from the output serializers.
	 * 
	 * @return DOCUMENT ME!
	 */
	JFileChooser createSaveFileChooser();
	
	/**
	 * Returns <code>true</code>, if the io manager has a registered input
	 * manager.
	 * 
	 * @return <code>true</code>, if the io manager has a registered input
	 *         manager.
	 */
	boolean hasInputSerializer();
	
	/**
	 * Returns <code>true</code>, if the io manager has a registered output
	 * manager.
	 * 
	 * @return <code>true</code>, if the io manager has a registered output
	 *         manager.
	 */
	boolean hasOutputSerializer();
	
	/**
	 * Returns <code>true</code>, if the given io manager listener was in the
	 * list of listeners and could be removed.
	 * 
	 * @param l
	 *           the io manager listener to remove.
	 * @return DOCUMENT ME!
	 */
	boolean removeListener(IOManagerListener l);
	
	Set<String> getGraphFileExtensions();
	
	// ~ Inner Interfaces =======================================================
	
	/**
	 * Interfaces an io manager listener.
	 * 
	 * @version $Revision: 1.10 $
	 */
	public interface IOManagerListener {
		/**
		 * Called, if an input serializer is added to the io manager.
		 * 
		 * @param is
		 *           the input serializer, which was added to the manager.
		 */
		void inputSerializerAdded(InputSerializer is);
		
		/**
		 * Called, if an output serializer ist added to the io manager.
		 * 
		 * @param os
		 *           the output serializer, which was added to the manager.
		 */
		void outputSerializerAdded(OutputSerializer os);
	}
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
