// ==============================================================================
//
// PluginXMLParser.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================
// $Id: PluginXMLParser.java,v 1.11 2010/12/22 13:05:33 klukas Exp $

package org.graffiti.managers.pluginmgr;

import java.io.IOException;
import java.io.InputStream;

import org.ErrorMsg;
import org.apache.commons.digester.Digester;
import org.apache.commons.logging.impl.NoOpLog;
import org.graffiti.core.StringBundle;
import org.xml.sax.SAXException;

/**
 * The XML parser for the plugin descriptions. The plugin description
 * (<tt>plugin.xml</tt>) file is validated by the <tt>plugin.dtd</tt>.
 * 
 * @version $Revision: 1.11 $
 */
public class PluginXMLParser {
	// ~ Static fields/initializers =============================================
	
	/** The <code>StringBundle</code> for the xml parser. */
	protected static StringBundle sBundle = StringBundle.getInstance();
	
	/** The public identifier of the plugin dtd. */
	public static String PUBLIC_DTD_IDENTIFIER = sBundle.getString(
						"plugin.dtd.identifier");
	
	/** The local plugin dtd. */
	public static String PLUGIN_DTD_LOCAL = sBundle.getRes("plugin.dtd.local")
						.toString();
	
	// ~ Instance fields ========================================================
	
	/** The parser instance. */
	private Digester parser;
	
	/** A temporary plugin description, which is used by the xml parser. */
	private PluginDescription description;
	
	// ~ Constructors ===========================================================
	
	/**
	 * Constructs a new plugin xml parser instance.
	 */
	public PluginXMLParser() {
		parser = new Digester();
		
		parser.setValidating(true);
		parser.setErrorHandler(new PluginXMLParserErrorHandler());
		
		parser.setLogger(new NoOpLog());
		
		// register an alternative plugin.dtd URL
		parser.register(PUBLIC_DTD_IDENTIFIER, PLUGIN_DTD_LOCAL);
		
		parser.addObjectCreate("plugin",
							"org.graffiti.managers.pluginmgr.PluginDescription");
		
		parser.addCallMethod("plugin/author", "setAuthor", 0);
		parser.addCallMethod("plugin/description", "setDescription", 0);
		
		parser.addCallMethod("plugin/plugindesc/name", "setName", 0);
		parser.addCallMethod("plugin/plugindesc/main", "setMain", 0);
		parser.addCallMethod("plugin/plugindesc/compatibility", "setCompatibleVersion", 0);
		parser.addCallMethod("plugin/plugindesc/version", "setVersion", 0);
		parser.addCallMethod("plugin/plugindesc/available", "setAvailable", 0);
		parser.addCallMethod("plugin/plugindesc/optional", "setIsOptional", 0);
		parser.addCallMethod("plugin/plugindesc/optionaldefault", "setIsOptionalDefaultTrue", 0);
		parser.addCallMethod("plugin/plugindesc/priority", "setIsPriorityPlugin", 0);
		parser.addCallMethod("plugin/plugindesc/feedname", "setRSSfeedName", 0);
		parser.addCallMethod("plugin/plugindesc/feedurl", "setRSSfeedURL", 0);
		
		// the rules for the plugin's dependencies
		parser.addObjectCreate("plugin/deps/plugindesc",
							"org.graffiti.managers.pluginmgr.PluginDependency");
		
		parser.addCallMethod("plugin/deps/plugindesc/name", "setName", 0);
		parser.addCallMethod("plugin/deps/plugindesc/main", "setMain", 0);
		parser.addCallMethod("plugin/deps/plugindesc/version", "setVersion", 0);
		
		parser.addCallMethod("plugin/deps/plugindesc/avaliable", "setAvailable", 0);
		
		// add the parsed plugin dependency to the list of dependencies
		parser.addSetNext("plugin/deps/plugindesc", "addPluginDependency",
							"org.graffiti.managers.pluginmgr.PluginDependency");
		
		// create a rule for saving the parsed plugin description object
		parser.addSetNext("plugin", "setPluginDescription",
							"org.graffiti.managers.pluginmgr.PluginDescription");
	}
	
	// ~ Methods ================================================================
	
	/**
	 * Sets the internal temporary plugin description to the given value. This
	 * method is used by the xml parser only.
	 * 
	 * @param description
	 *           the new value.
	 */
	public void setPluginDescription(PluginDescription description) {
		this.description = description;
	}
	
	/**
	 * Parses the given <code>plugin.xml</code> file.
	 * 
	 * @param is
	 *           input stream of the<code>plugin.xml</code> file.
	 * @return an instance of plugin description, which contains the parsed
	 *         information.
	 * @throws IOException
	 *            DOCUMENT ME!
	 */
	public PluginDescription parse(InputStream is)
						throws IOException {
		parser.clear();
		parser.push(this);
		try {
			parser.parse(is);
		} catch (SAXException saxe) {
			ErrorMsg.addErrorMessage(saxe);
			return null;
		}
		
		try {
			validateDescription(description);
		} catch (SAXException saxe) {
			ErrorMsg.addErrorMessage(saxe);
			return null;
		}
		
		return description;
	}
	
	/**
	 * Validates the given current plugin description.
	 * 
	 * @param description
	 *           the description to validate.
	 * @throws SAXException
	 *            DOCUMENT ME!
	 */
	public void validateDescription(PluginDescription description)
						throws SAXException {
		if (description == null) {
			throw new SAXException(new PluginManagerException(
								"exception.PluginDescriptionNull"));
		}
		
		if ((description.getName() == null) || "".equals(description.getName())) {
			throw new SAXException(new PluginManagerException(
								"exception.IllegalPluginName", description.getName()).getMessage());
		}
		
		if ((description.getMain() == null) || "".equals(description.getMain())) {
			throw new SAXException(new PluginManagerException(
								"exception.IllegalPluginMain", description.getMain()).getMessage());
		}
	}
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
