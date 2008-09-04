//==============================================================================
//
//   DefaultPluginManager.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: DefaultPluginManager.java,v 1.6 2008/09/04 09:56:08 klukas Exp $

package org.graffiti.managers.pluginmgr;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.ErrorMsg;
import org.graffiti.core.StringBundle;
import org.graffiti.options.GravistoPreferences;
import org.graffiti.plugin.GenericPlugin;
import org.graffiti.util.InstanceCreationException;
import org.graffiti.util.InstanceLoader;
import org.graffiti.util.PluginHelper;
import org.graffiti.util.ProgressViewer;
import org.graffiti.util.StringSplitter;

/**
 * Manages the list of plugins.
 *
 * @version $Revision: 1.6 $
 */
public class DefaultPluginManager
    implements PluginManager
{
    //~ Static fields/initializers =============================================
    
    public static DefaultPluginManager lastInstance = null;

    /** The logger for the current class. */
    private static final Logger logger = Logger.getLogger(DefaultPluginManager.class.getName());

    //~ Instance fields ========================================================

    /** The <code>StringBundle</code> of the plugin manager. */
    protected StringBundle sBundle = StringBundle.getInstance();

    /**
     * Maps from a plugin name (<code>String</code>) to a plugin entry
     * (<code>Entry</code>).
     */
    private Hashtable<String, PluginEntry> pluginEntries;

    /**
     * Holds the plugin entries of the last search. This avoids researching
     * everytime a dependent plugin is automatically searched.
     */
    private List<PluginEntry> entries;

    /** The list of plugin manager listeners. */
    private List<PluginManagerListener> pluginManagerListeners;

    /** The preferences of the plugin manager. */
    private GravistoPreferences prefs;

    //~ Constructors ===========================================================

    /**
     * Constructs a new <code>PluginManger</code> instance.
     *
     * @param prefs the preferences, which contain information about what to
     *        load during the instanciation of the plugin manager.
     */
    public DefaultPluginManager(GravistoPreferences prefs)
    {
        this.prefs = prefs;
        this.pluginEntries = new Hashtable<String, PluginEntry>();
        this.pluginManagerListeners = new LinkedList<PluginManagerListener>();
        lastInstance=this;
    }

    //~ Methods ================================================================

    /**
     * Checks if the plugin is already installed, i.e. if the plugin's name is
     * in the list of plugin entries.
     *
     * @param name name of the plugin.
     *
     * @return <code>true</code> if the plugin's name is in the list of plugin
     *         entries, <code>false</code> otherwise.
     */
    public boolean isInstalled(String name)
    {
        return pluginEntries.containsKey(name);
    }

    /**
     * Sets the <code>loadOnStartup</code> flag of the given object, to the
     * given value.
     *
     * @param name the name of the plugin.
     * @param loadOnStartup <code>true</code>, if the plugin should be loaded
     *        at startup.
     */
    public void setLoadOnStartup(String name, Boolean loadOnStartup)
    {
        ((PluginEntry) pluginEntries.get(name)).setLoadOnStartup(loadOnStartup);
    }

    /**
     * Returns the corrent list of plugin entries.
     *
     * @return a <code>Collection</code> containing all the plugin entries.
     */
    public Collection<PluginEntry> getPluginEntries()
    {
        return pluginEntries.values();
    }

    /**
     * Returns the plugin instance of the given plugin name.
     *
     * @param name the name of the plugin.
     *
     * @return the instance of the plugin of the given name.
     */
    public GenericPlugin getPluginInstance(String name)
    {
        return ((PluginEntry) pluginEntries.get(name)).getPlugin();
    }

    /**
     * Adds the given plugin manager listener to the list of listeners.
     *
     * @param listener the new listener to add to the list.
     */
    public void addPluginManagerListener(PluginManagerListener listener)
    {
        pluginManagerListeners.add(listener);
    }

    /**
     * Returns a new instance of the plugin &quot;main&quot; class with the
     * given plugin name.
     *
     * @param pluginLocation the URL to the plugin.
     *
     * @return the instantiated plugin.
     *
     * @exception PluginManagerException an error occured while loading or
     *            instantiating the  plugin.
     */
    public GenericPlugin createInstance(URL pluginLocation)
        throws PluginManagerException
    {
        return createInstance(pluginLocation, null);
    }

    /**
     * Returns a new instance of the plugin &quot;main&quot; class with the
     * given plugin name. The progress made is displayed with progressViewer.
     *
     * @param pluginLocation the URL to the plugin.
     * @param progressViewer the progress viewer that display the progress made
     *
     * @return the instantiated plugin.
     *
     * @exception PluginManagerException an error occured while loading or
     *            instantiating the  plugin.
     */
    public GenericPlugin createInstance(URL pluginLocation,
        ProgressViewer progressViewer)
        throws PluginManagerException
    {
        PluginDescription description = PluginHelper.readPluginDescription(pluginLocation);

        GenericPlugin pluginInstance = createInstance(description,
                progressViewer);

        //        // add the plugin to the list of instanciated plugins.
        //        addPlugin(description, pluginInstance, pluginLocation, Boolean.TRUE);
        //
        //        // inform all listeners about the new plugin.
        //        firePluginAdded(pluginInstance, description);
        return pluginInstance;
    }

    /**
     * Loads the plugin from the given location.
     *
     * @param description DOCUMENT ME!
     * @param pluginLocation the location of the plugin.
     * @param loadOnStartup <code>true</code>, if the given plugin should be
     *        loaded at the startup.
     *
     * @exception PluginManagerException if an error occurs while loading or
     *            instantiating the plugin.
     */
    public void loadPlugin(PluginDescription description, URL pluginLocation,
        Boolean loadOnStartup)
        throws PluginManagerException
    {
        loadPlugins(new PluginEntry[]
            {
                new DefaultPluginEntry(pluginLocation.toString(), description)
            });
    }

    /**
     * Loads the plugin from the given location.
     *
     * @param plugins the plugin entries describing the plugins to be loaded
     *
     * @exception PluginManagerException if an error occurs while loading or
     *            instantiating the plugin.
     */
    public void loadPlugins(PluginEntry[] plugins)
        throws PluginManagerException
    {
        loadPlugins(plugins, null);
    }
    
    public void loadPlugins(PluginEntry[] plugins, ProgressViewer progressViewer) throws PluginManagerException {
    	loadPlugins(plugins, progressViewer, false);
    }

    /**
     * Loads the plugin from the given location.The progress made is displayed
     * with progressViewer.
     *
     * @param plugins the plugin entries describing the plugins to be loaded
     * @param progressViewer the progress viewer that display the progress made
     *
     * @exception PluginManagerException if an error occurs while loading or
     *            instantiating the plugin.
     */
    public void loadPlugins(PluginEntry[] plugins, ProgressViewer progressViewer, boolean doAutomatic)
        throws PluginManagerException
    {
        List messages = new LinkedList();

        ArrayList loadLater = new ArrayList();

        for(int i = 0; i < plugins.length; i++)
        {
        	if (plugins[i]==null) continue;
        	loadPlugin(plugins, progressViewer, messages, loadLater, i);
        }

        // load plugins with dependencies if they are satisfied
        int loaded = 1;
        int tryCnt = 0;
        while(loadLater.size() > 0 && tryCnt<1000) {
            loadDelayedPlugins(progressViewer, messages, loadLater);
            tryCnt++;
        }
        
        if (tryCnt>=1000) {
        	System.err.println("Internal error in loading delayed plugins.");
        	ErrorMsg.addErrorMessage("Internal error in loading delayed plugins.");
        }

        // check if all plugins could be loaded
        if(loadLater.size() > 0)
        {
            checkDependencies(doAutomatic, messages, loadLater);
        }

        savePrefs();

        // build error string and throw exception
        if(!messages.isEmpty())
        {
            String msg = "";

            for(Iterator itr = messages.iterator(); itr.hasNext();)
            {
                msg += ((String) itr.next() + "\n");
            }

            throw new PluginManagerException("Error during plugin loading: "+msg, msg.trim());
        }
    }

    /**
	 * @param doAutomatic
	 * @param messages
	 * @param loadLater
	 */
	private void checkDependencies(boolean doAutomatic, List messages, ArrayList loadLater) {
		for(int i = 0; i <= (loadLater.size() / 2); i += 2)
		{
		    PluginDescription desc = (PluginDescription) loadLater.get(i +
		            1);
		    List deps = desc.getDependencies();

		    if(doAutomatic ||
		        (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(
		            null,
		            "Some dependencies are not satisfied. Should an " +
		            "automatic search be performed?")))
		    {
		        if(!doAutomatic)
		        {
		            PluginDescriptionCollector collector = new ClassPathPluginDescriptionCollector();

		            entries = collector.collectPluginDescriptions();
		        }

		        doAutomatic = true;

		        boolean couldLoadDep = false;
		        boolean alreadyLoaded = true;

		        for(Iterator it = deps.iterator(); it.hasNext();)
		        {
		            PluginDependency dep = (PluginDependency) it.next();

		            if(!pluginEntries.containsKey(dep.getName()))
		            {
		                boolean couldLoad1 = false;
		                alreadyLoaded = false;

		                if (entries!=null)
		                for(Iterator enit = entries.iterator();
		                    enit.hasNext();)
		                {
		                    PluginEntry entry = (PluginEntry) enit.next();

		                    if(entry.getDescription().getName().equals(dep.getName()))
		                    {
		                        // successfully found a missing dep plugin
		                        try
		                        {
		                            //                                        doAutomatic = true;
		                            loadPlugins(new PluginEntry[] { entry });

		                            //                                        doAutomatic = false;
		                            couldLoad1 = true;
		                            couldLoadDep = true;

		                            //////                                  JOptionPane.showMessageDialog(null, 
		                            //////                                      "Succesfully loaded plugin: " +
		                            //////                                      entry.getDescription().getName());
		                            break;
		                        }
		                        catch(Exception e)
		                        {
		                            couldLoadDep = false;
		                            messages.add("Error during automatic" +
		                                "dependency solving: " + e);
		                        }
		                    }
		                }

		                if(!couldLoad1)
		                    couldLoadDep = false;
		            }
		        }

		        if(alreadyLoaded)
		        {
		            continue;
		        }

		        if(couldLoadDep)
		        {
		            try
		            {
		                URL pluginUrl = (URL) loadLater.get(i);
		                loadPlugin(desc, pluginUrl, Boolean.TRUE);
		            }
		            catch(Exception e)
		            {
		                messages.add("Error during automatic" +
		                    "dependency resolving: " + e);
		            }

		            continue;
		        }
		    }

		    messages.add("Plugin " + desc.getName() + " could not be " +
		        "loaded since one or more dependencies are not " +
		        "satisfied:");

		    for(Iterator it = deps.iterator(); it.hasNext();)
		    {
		        PluginDependency dep = (PluginDependency) it.next();

		        if(!pluginEntries.containsKey(dep.getName()))
		        {
		            messages.add("     " + dep.getName() + " (" +
		                dep.getMain() + ")");
		        }
		    }
		}
	}

	/**
	 * @param progressViewer
	 * @param messages
	 * @param loadLater
	 */
	private void loadDelayedPlugins(ProgressViewer progressViewer, List messages, ArrayList loadLater) {
	    int i=0;
		while(loadLater.size() > 0 && i<loadLater.size())
		{
		    PluginDescription desc = (PluginDescription) loadLater.get(i+1);
		    List deps = desc.getDependencies();
		    boolean satisfied = true;

		    for(Iterator it = deps.iterator(); it.hasNext();)
		    {
		        PluginDependency dep = (PluginDependency) it.next();

		        if(!pluginEntries.containsKey(dep.getName()))
		        {
		            satisfied = false;

		            break;
		        }
		    }

		    if(satisfied)
		    {
		        try
		        {
		            addPlugin(desc, (URL) loadLater.get(i), Boolean.TRUE,
		                progressViewer);
		        }
		        catch(PluginManagerException pme)
		        {
		            messages.add(pme.getMessage());
		        }
		        catch(NullPointerException npe) {
		        	messages.add(npe.getMessage());
		        }
		        finally {
		            loadLater.remove(i);    // URL
		            loadLater.remove(i);  // PD
		            i=-2;
		        }
		    } 
		    i=i+2;
		}
	}

	/**
	 * @param plugins
	 * @param progressViewer
	 * @param messages
	 * @param loadLater
	 * @param i
	 */
	private void loadPlugin(PluginEntry[] plugins, ProgressViewer progressViewer, List<String> messages, ArrayList<Object> loadLater, int i) {
		String pluginLocation = plugins[i].getFileName();
		// System.out.println("Loading Plugin from: "+pluginLocation);
		try
		{
		    URL pluginUrl = new URL(pluginLocation);
		    PluginDescription desc = plugins[i].getDescription();
		    
		    if (desc==null) return;

		    //                    PluginHelper.readPluginDescription(pluginUrl);
		    List deps = null;
		    if (desc!=null) deps = desc.getDependencies();

		    if((deps == null) || deps.isEmpty())
		    {
		        addPlugin(desc, pluginUrl, Boolean.TRUE, progressViewer);
		    }
		    else
		    {
		        // check if deps are satisfied
		        boolean satisfied = true;

		        for(Iterator it = deps.iterator(); it.hasNext();)
		        {
		            PluginDependency dep = (PluginDependency) it.next();

		            if(!pluginEntries.containsKey(dep.getName()))
		            {
			            // System.out.println("Plugin "+desc.getName()+" needs "+dep.getName()+"!");

		                satisfied = false;
		                break;
		            }
		        }

		        if(satisfied)
		        {
		            try
		            {
		                addPlugin(desc, pluginUrl, Boolean.TRUE,
		                    progressViewer);
		            }
		            catch(PluginManagerException pme)
		            {
		                messages.add(pme.toString());
		            }
		        }
		        else
		        {
		            loadLater.add(pluginUrl);
		            loadLater.add(desc);
		        }
		    }
		}
		catch(PluginManagerException pme)
		{
		    messages.add(pme.toString());
		}
		catch(MalformedURLException mue) {
			messages.add(mue.toString());
		} catch(Exception e)
		{
		    messages.add(e.toString());
		}
	}

	/**
     * Loads the plugins which should be loaded on startup.
     *
     * @exception PluginManagerException if an error occurred while loading one
     *            of the plugins.
     */
    public void loadStartupPlugins()
        throws PluginManagerException
    {
        loadStartupPlugins(null);
    }

    /**
     * Loads the plugins which should be loaded on startup. The progress made
     * is displayed with progressViewer.
     *
     * @param progressViewer the progress viewer that display the progress made
     *
     * @exception PluginManagerException if an error occurred while loading one
     *            of the plugins.
     */
    public void loadStartupPlugins(ProgressViewer progressViewer)
        throws PluginManagerException
    {
        // load the user's standard plugins
        int numberOfPlugins = prefs.getInt("numberOfPlugins", 0);

        // If available initialize the progressViewer
        if(progressViewer != null)
            progressViewer.setMaximum(numberOfPlugins);

        List messages = new LinkedList();

        PluginEntry[] pluginEntries = new PluginEntry[numberOfPlugins];

        int cnt = 0;

        for(int i = 1; i <= numberOfPlugins; i++)
        {
            String pluginLocation = prefs.get("pluginLocation" + i, null);

            if(pluginLocation != null)
            {
                try
                {
                	URL pluginUrl = new URL(pluginLocation);
                	PluginDescription desc = PluginHelper.readPluginDescription(pluginUrl);
                    pluginEntries[cnt++] = new DefaultPluginEntry(pluginLocation,
                            desc);
                }
                catch(MalformedURLException mue)
                {
                	System.err.println(mue.getLocalizedMessage());
                    messages.add(mue.getMessage());
                }
            }
        }

        try
        {
            loadPlugins(pluginEntries, progressViewer);
        }
        catch(PluginManagerException pme)
        {
            messages.add(pme.getMessage());
        }

        // collect info of all exceptions into one exception
        if(!messages.isEmpty())
        {
            String msg = "";

            for(Iterator itr = messages.iterator(); itr.hasNext();)
            {
                msg += ((String) itr.next() + "\n");
            }

            throw new PluginManagerException("exception.loadStartup\n",
                msg.trim());
        }
    }

    /**
     * Removes the given plugin manager listener from the list of listeners.
     *
     * @param listener the listener to remove from the list of listeners.
     */
    public void removePluginManagerListener(PluginManagerListener listener)
    {
        boolean success = pluginManagerListeners.remove(listener);

        if(!success)
        {
            logger.warning("trying to remove a non existing" +
                " plugin manager listener");
        }
    }

    /**
     * Saves the plugin manager's prefs.
     *
     * @exception PluginManagerException if an error occurrs while saving the
     *            preferences.
     */
    public void savePrefs()
        throws PluginManagerException
    {
        if (prefs==null) return;
        try
        {
            // get rid of the old preferences ...
            prefs.clear();

            // search for all plugins, which should be loaded at startup
            // and put their urls into this list
            List plugins = new LinkedList();

            for(Iterator i = pluginEntries.values().iterator(); i.hasNext();)
            {
                PluginEntry e = (PluginEntry) i.next();

                if(e.getLoadOnStartup().equals(Boolean.TRUE))
                {
                    plugins.add(e.getPluginLocation());
                }
            }

            // and write the new ones
            prefs.putInt("numberOfPlugins", plugins.size());

            int count = 1;

            for(Iterator i = plugins.iterator(); i.hasNext();)
            {
                prefs.put("pluginLocation" + count, i.next().toString());
                count++;
            }
        }
        catch(Exception e)
        {
            throw new PluginManagerException("exception.SavePrefs",
                e.getMessage());
        }
    }

    /**
     * Adds the given plugin file to the list of plugins. The progress made is
     * displayed with progressViewer.
     *
     * @param description the name of the plugin's main class.
     * @param pluginLocation the location of the given plugin.
     * @param loadOnStartup <code>true</code> if the plugin should be loaded on
     *        the startup of the system, <code>false</code> otherwise.
     * @param progressViewer the progress viewer that display the progress made
     *
     * @throws PluginManagerException DOCUMENT ME!
     */
    private void addPlugin(PluginDescription description, 
    //        GenericPlugin plugin,
    URL pluginLocation, Boolean loadOnStartup, ProgressViewer progressViewer) throws PluginManagerException
    {
        //        assert plugin != null;
        assert description != null;

        // create an instance of the plugin's main class
        GenericPlugin plugin = createInstance(description, progressViewer);

        //        // add the plugin to the list of instanciated plugins.
        //        addPlugin(description, pluginInstance, pluginLocation, loadOnStartup);
        
        
        if (plugin==null) {
        	System.err.println("ERROR: COULD NOT CREATE PLUGIN");
        	if (description!=null) System.err.println("Description/Name: "+description.getName());
        	else System.err.println("Plugin Description is NULL");
        	if (pluginLocation!=null) System.err.println("PluginLocation: "+pluginLocation.toString());
        	else System.err.println("Plugin Location is NULL");
        	
        	String errMsg="<br>ERROR: COULD NOT CREATE PLUGIN<br>";

        	if (description!=null) errMsg+="Plugin Description/Name: "+description.getName()+"<br>";
        	else errMsg+="Plugin Description is NULL<br>";
        	if (pluginLocation!=null) errMsg+="PluginLocation: "+pluginLocation.toString()+"<br>";
        	else errMsg+="Plugin Location is NULL";
        	throw new PluginManagerException("Plugin Loading Failed", errMsg);
        }
        
        pluginEntries.put(description.getName(),
            new DefaultPluginEntry(description, plugin, loadOnStartup,
                pluginLocation));

        // inform all listeners about the new plugin.
       	firePluginAdded(plugin, description);
       	
       	// construct the path for the plugin in the preferences        
        // e.g. org.graffiti.plugins.io.graphviz.DOTSerializerPlugin becomes
        //      org/graffiti/plugins/io/graphviz/DOTSerializerPlugin
        String[] strings = StringSplitter.split(description.getMain(), ".");
        StringBuffer pluginNode = new StringBuffer();

        for(int i = 0; i < strings.length; i++)
        {
            pluginNode.append(strings[i]);

            if(i < (strings.length - 1))
            {
                pluginNode.append("/");
            }
        }

        if (prefs!=null) {
	        GravistoPreferences pluginPrefs = prefs.node("pluginPrefs/" +
	                pluginNode.toString());
	
	        // configure the plugin's preferences
	        if (plugin!=null)
	        	plugin.configure(pluginPrefs);
        }
    }

    /**
     * Creates an instance of the plugin from its description. The progress
     * made is displayed with progressViewer.
     *
     * @param description the description of the plugin to be instantiated
     * @param progressViewer the progress viewer that display the progress made
     *
     * @return the instantiated plugin.
     *
     * @exception PluginManagerException an error occurrs while loading or
     *            instantiating the plugin.
     */
    private GenericPlugin createInstance(PluginDescription description,
        ProgressViewer progressViewer)
        throws PluginManagerException
    {
    	
    	if (description==null) {
    		return null;
    	}
        if(isInstalled(description.getName()))
        {
      	  throw new PluginManagerException("Cause: plugin name already defined/plugin already loaded!");
        }

        // If available show statustext to the user
        if(progressViewer != null)
            progressViewer.setText("Loading " + description.getName() + "...");

        GenericPlugin pluginInstance;

        try
        { // to instanciate the plugin's main class
        	System.out.print(".");
            pluginInstance = (GenericPlugin) InstanceLoader.createInstance(description.getMain());
        }
        catch(InstanceCreationException ice)
        {
        	System.out.println("Instance Creation Exception: "+description.getMain());
            throw new PluginManagerException(ice.getMessage()+" (cause: "+ice.getCause().getMessage()+") ",
                description.toString());
        }
        catch(NoClassDefFoundError nce) {
        	System.out.println("No Class Definition Found: "+description.getMain());
        	throw new PluginManagerException(nce.getMessage()+" (cause: "+nce.getCause().getMessage()+") ",
                    description.toString());
        }

        // update status (if available).
        if(progressViewer != null)
        {
            progressViewer.setText("Loading " + description.getName() + ": OK");
            progressViewer.setValue(progressViewer.getValue() + 1);
        }

        return pluginInstance;
    }

    /**
     * Registers the plugin as a plugin manager listener, if it is of instance
     * <code>PluginManagerListener</code> and calls the
     * <code>pluginAdded</code> in all plugin manager listeners.
     *
     * @param plugin the added plugin.
     * @param desc the description of the added plugin.
     */
    private void firePluginAdded(GenericPlugin plugin, PluginDescription desc)
    {
        // register the plugin as a plugin manager listener, if needed
        if(plugin instanceof PluginManagerListener)
        {
            addPluginManagerListener((PluginManagerListener) plugin);
        }

        // Copy this list to prevent concurrent modification exceptions
        List listeners = new LinkedList();

        for(Iterator i = pluginManagerListeners.iterator(); i.hasNext();)
        {
            listeners.add(i.next());
        }

        for(Iterator i = listeners.iterator(); i.hasNext();)
        {
            PluginManagerListener listener = (PluginManagerListener) i.next();

            if(plugin != null) {
            	try {
            		listener.pluginAdded(plugin, desc);
            	} catch(Exception e) {
            	    ErrorMsg.addErrorMessage(e);
            	}
            }
        }
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
