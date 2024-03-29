// ==============================================================================
//
// PluginManagerDialog.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================
// $Id: PluginManagerDialog.java,v 1.8 2010/12/22 13:05:33 klukas Exp $

package org.graffiti.managers.pluginmgr;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import org.graffiti.core.ImageBundle;
import org.graffiti.core.StringBundle;
import org.graffiti.util.PluginHelper;

/**
 * DOCUMENT ME!
 * 
 * @version $Revision: 1.8 $
 */
public class PluginManagerDialog
					extends JDialog
					implements ActionListener, ListSelectionListener, TableModelListener {
	// ~ Static fields/initializers =============================================
	
	private static final long serialVersionUID = 1L;
	
	// ~ Instance fields ========================================================
	
	/** The <code>ImageBundle</code> of this plugin manager dialog. */
	protected ImageBundle iBundle = ImageBundle.getInstance();
	
	/** The <code>StringBundle</code> of this plugin manager dialog. */
	protected StringBundle sBundle = StringBundle.getInstance();
	
	/** The button ui components. */
	private JButton add;
	
	private JButton invertSelection;
	
	/** The button ui components. */
	private JButton close;
	
	/** The button ui components. */
	private JButton search;
	
	/**
	 * The label ui components of this dialog, which contain the actual
	 * information.
	 */
	private JLabel author;
	
	/**
	 * The label ui components of this dialog, which contain the actual
	 * information.
	 */
	private JLabel version;
	
	/** The plugins table ui component. */
	private JTable pluginsTable;
	
	/**
	 * The text area components of this dialog, which contain the actual
	 * information.
	 */
	private JTextArea available;
	
	/**
	 * The text area components of this dialog, which contain the actual
	 * information.
	 */
	private JTextArea description;
	
	/** The list selection model of of the plugins table ui component. */
	private ListSelectionModel pluginsSelectionModel;
	
	/** The plugin manager. */
	private PluginManager pluginManager;
	
	/** The model of this dialog's plugins table ui component. */
	private PluginsTableModel pluginsTableModel;
	
	/** The last file in the open dialog. */
	private String lastFile;
	
	/** <code>true</code>, iff the user has modified the plugin list. */
	private boolean modifiedPluginList;
	
	// ~ Constructors ===========================================================
	
	/**
	 * Constructs a new plugin manager dialog.
	 * 
	 * @param parent
	 *           a reference to the parent frame.
	 * @param pluginManager
	 *           the plugin manager to edit the preferences from.
	 */
	public PluginManagerDialog(Frame parent, PluginManager pluginManager) {
		super(parent, true);
		
		this.pluginManager = pluginManager;
		modifiedPluginList = false;
		
		setTitle(sBundle.getString("dialog.title"));
		
		pluginsTableModel = new PluginsTableModel();
		
		// this is the dialogs "root" panel
		// this is needed, because we want the dialogs "root" panel
		// to have an empty border.
		JPanel dialogPane = new JPanel(new BorderLayout());
		
		dialogPane.add(createListAndInfoView(), BorderLayout.CENTER);
		dialogPane.add(createButtonBar(), BorderLayout.SOUTH);
		dialogPane.setBorder(BorderFactory.createCompoundBorder(
							BorderFactory.createEmptyBorder(5, 5, 5, 5),
							dialogPane.getBorder()));
		
		getContentPane().add(dialogPane, BorderLayout.CENTER);
		
		updateGUI();
		
		pluginsTableModel.addTableModelListener(this);
		
		pack();
		setLocationRelativeTo(parent);
	}
	
	// ~ Methods ================================================================
	
	/**
	 * Returns the plugin manager of this dialog.
	 * 
	 * @return the plugin manager of this dialog.
	 */
	public PluginManager getPluginManager() {
		return pluginManager;
	}
	
	/**
	 * DOCUMENT ME!
	 * 
	 * @param e
	 *           DOCUMENT ME!
	 */
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (src == search) {
			searchPlugins();
		} else
			if (src == add) {
				addPlugin();
			} else
				if (src == close) {
					close();
				} else
					if (src == invertSelection) {
						invertCheckboxes();
					}
	}
	
	/**
	 * Called, iff the user presses the &quot;close&quot; button.
	 */
	public void close() {
		try {
			pluginManager.savePrefs();
		} catch (PluginManagerException pme) {
			showWarningMessage(pme.getMessage());
		}
		
		if (modifiedPluginList) {
			showWarningMessage(sBundle.getString("message.restart.editor"));
		}
		
		dispose();
	}
	
	/**
	 * Called, if the selection in the list changes.
	 * 
	 * @param e
	 *           DOCUMENT ME!
	 */
	public void selectionChanged(ListSelectionEvent e) {
		updateGUI();
	}
	
	private void invertCheckboxes() {
		if (!pluginsTableModel.isEmpty()) {
			for (int i = 0; i < pluginsTableModel.getRowCount(); i++) {
				Boolean current = (Boolean) pluginsTableModel.getValueAt(i, 2);
				pluginsTableModel.setValueAt(new Boolean(!current.booleanValue()), i, 2);
			}
		}
	}
	
	/**
	 * Displays this dialog.
	 */
	@Override
	public void setVisible(boolean show) {
		if (show) {
			pluginsTableModel.updateModel();
			updateGUI();
			
			if (!pluginsTableModel.isEmpty()) {
				pluginsSelectionModel.setLeadSelectionIndex(0);
			}
		}
		super.setVisible(show);
	}
	
	/**
	 * Called if the contents of the pluginsTable changed.
	 * 
	 * @param e
	 *           DOCUMENT ME!
	 */
	public void tableChanged(TableModelEvent e) {
		updateGUI();
	}
	
	/**
	 * Called, if the selection changed and/or the ui elements have to be
	 * updated.
	 */
	public void updateGUI() {
		if (pluginsSelectionModel.isSelectionEmpty()) {
			version.setText(" ");
			author.setText(" ");
			description.setText(" ");
			available.setText(" ");
		} else {
			PluginDescription desc = pluginsTableModel.getPluginDescription(pluginsSelectionModel.getMinSelectionIndex());
			
			version.setText(desc.getVersion());
			author.setText(desc.getAuthor());
			description.setText(desc.getDescription());
			available.setText(desc.getAvailable());
		}
	}
	
	/**
	 * Called, if a value in the list changes.
	 * 
	 * @param e
	 *           DOCUMENT ME!
	 */
	public void valueChanged(ListSelectionEvent e) {
		// ignore extra events
		if (e.getValueIsAdjusting()) {
			return;
		}
		
		updateGUI();
	}
	
	/**
	 * Displays a dialog with the given error message.
	 * 
	 * @param msg
	 *           the message to display in the error dialog.
	 */
	protected void showErrorMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg,
							sBundle.getString("message.error.title"), JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Displays a dialog with the given warning message.
	 * 
	 * @param msg
	 *           the warning message to display in the dialog.
	 */
	protected void showWarningMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg,
							sBundle.getString("message.warning.title"),
							JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * DOCUMENT ME!
	 * 
	 * @param pluginLocation
	 *           DOCUMENT ME!
	 */
	private void addPlugin(String pluginLocation) {
		try {
			URL pluginUrl = new URL(pluginLocation);
			PluginDescription desc = PluginHelper.readPluginDescription(pluginUrl);
			pluginManager.loadPlugin(desc, pluginUrl, Boolean.TRUE);
			pluginsTableModel.updateModel();
		} catch (PluginManagerException pem) {
			// pem.printStackTrace();
			showErrorMessage(pem.getMessage());
		} catch (MalformedURLException mue) {
			showErrorMessage(mue.getMessage());
		}
	}
	
	/**
	 * Opens a file selector dialog and tries to add the specified plugin file
	 * to the plugin list.
	 */
	private void addPlugin() {
		JFileChooser chooser = new JFileChooser(lastFile);
		chooser.addChoosableFileFilter(new PluginFileFilter("jar"));
		chooser.addChoosableFileFilter(new PluginFileFilter("zip"));
		chooser.addChoosableFileFilter(new PluginFileFilter("xml"));
		chooser.addChoosableFileFilter(new PluginFileFilter(
							new String[] { "xml", "jar", "zip" }));
		
		int retVal = chooser.showDialog(this,
							sBundle.getString("dialog.button.ok"));
		
		if (retVal == JFileChooser.APPROVE_OPTION) {
			File f = chooser.getSelectedFile();
			lastFile = f.getAbsolutePath();
			
			String fileName = "file:" + f.getAbsolutePath();
			
			addPlugin(fileName);
		}
	}
	
	/**
	 * DOCUMENT ME!
	 * 
	 * @param plugins
	 *           DOCUMENT ME!
	 */
	private void addPlugins(PluginEntry[] plugins) {
		if (plugins != null) {
			// for (int i = 0; i < plugins.length; i++) {
			// addPlugin(plugins[i].getFileName());
			// }
			try {
				pluginManager.loadPlugins(plugins);
			} catch (PluginManagerException e) {
				showErrorMessage(e.getMessage());
			}
			
			pluginsTableModel.updateModel();
		}
	}
	
	/**
	 * Creates and returns the button bar of this dialog.
	 * 
	 * @return the button bar of this dialog.
	 */
	private JPanel createButtonBar() {
		JPanel buttonBar = new JPanel(true);
		
		search = new JButton(sBundle.getString("dialog.button.search"));
		search.setIcon(iBundle.getIcon("dialog.button.add.icon"));
		search.addActionListener(this);
		
		add = new JButton(sBundle.getString("dialog.button.add"));
		add.setIcon(iBundle.getIcon("dialog.button.add.icon"));
		add.setToolTipText(sBundle.getString("dialog.button.add.tooltip"));
		add.addActionListener(this);
		
		invertSelection = new JButton(sBundle.getString("dialog.button.invert"));
		invertSelection.setToolTipText(sBundle.getString("dialog.button.invert.tooltip"));
		invertSelection.addActionListener(this);
		
		close = new JButton(sBundle.getString("dialog.button.close"));
		close.setIcon(iBundle.getIcon("dialog.button.close.icon"));
		close.setToolTipText(sBundle.getString("dialog.button.close.tooltip"));
		close.addActionListener(this);
		
		getRootPane().setDefaultButton(close);
		
		buttonBar.add(search);
		buttonBar.add(add);
		buttonBar.add(invertSelection);
		buttonBar.add(close);
		
		return buttonBar;
	}
	
	/**
	 * Creates and returns the plugin list and the plugin information view.
	 * 
	 * @return a panel, which contains the plugin list and the plugin
	 *         information view.
	 */
	private JPanel createListAndInfoView() {
		JPanel view = new JPanel(new BorderLayout());
		
		pluginsTable = new JTable(pluginsTableModel);
		
		// set the preferred width of the table's columns
		TableColumn column = null;
		
		for (int i = 0; i < pluginsTable.getColumnCount(); i++) {
			column = pluginsTable.getColumnModel().getColumn(i);
			
			if (i == 0) {
				column.setPreferredWidth(18);
				column.setMaxWidth(18);
				column.setMinWidth(18);
			} else
				if (i == 2) {
					column.setPreferredWidth(100);
					column.setMaxWidth(100);
					column.setMinWidth(50);
				}
		}
		
		pluginsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		pluginsSelectionModel = pluginsTable.getSelectionModel();
		pluginsSelectionModel.addListSelectionListener(this);
		
		JScrollPane listScroller = new JScrollPane(pluginsTable);
		listScroller.setPreferredSize(new Dimension(320, 200));
		listScroller.setBorder(BorderFactory.createBevelBorder(
							BevelBorder.LOWERED));
		
		JPanel listLowered = new JPanel(new GridLayout(1, 1));
		listLowered.add(listScroller);
		
		listLowered.setBorder(BorderFactory.createTitledBorder(
							sBundle.getString("dialog.list.title")));
		
		view.add(listLowered, BorderLayout.CENTER);
		
		JPanel infoVA = new JPanel(new BorderLayout());
		
		version = new JLabel();
		version.setBorder(BorderFactory.createTitledBorder(sBundle.getString(
							"dialog.info.version")));
		
		author = new JLabel();
		author.setBorder(BorderFactory.createTitledBorder(sBundle.getString(
							"dialog.info.author")));
		
		available = new JTextArea();
		available.setBackground(author.getBackground());
		available.setEditable(false);
		available.setLineWrap(true);
		available.setPreferredSize(new Dimension(200, 100));
		available.setBorder(BorderFactory.createTitledBorder(sBundle.getString(
							"dialog.info.available")));
		
		description = new JTextArea();
		description.setBackground(author.getBackground());
		description.setEditable(false);
		description.setLineWrap(true);
		description.setPreferredSize(new Dimension(200, 100));
		description.setBorder(BorderFactory.createTitledBorder(
							sBundle.getString("dialog.info.description")));
		
		infoVA.add(version, BorderLayout.NORTH);
		infoVA.add(author, BorderLayout.SOUTH);
		
		JPanel infoAD = new JPanel(new GridLayout(0, 1));
		
		infoAD.add(available);
		infoAD.add(description);
		
		JPanel info = new JPanel(new BorderLayout());
		info.add(infoVA, BorderLayout.NORTH);
		info.add(infoAD, BorderLayout.CENTER);
		
		view.add(info, BorderLayout.EAST);
		
		return view;
	}
	
	/**
	 * Called, if the search button has been pressed.
	 */
	private void searchPlugins() {
		PluginDescriptionCollector collector = new ClassPathPluginDescriptionCollector();
		
		PluginSelector selector = new PluginSelector(this, collector);
		
		if (selector.isEmpty()) {
			JOptionPane.showMessageDialog(this,
								sBundle.getString("selector.noPluginsFound"));
			
			return;
		}
		
		selector.setVisible(true);
		
		addPlugins(selector.getSelectedItems());
	}
	
	// ~ Inner Classes ==========================================================
	
	/**
	 * The list model of the dialog's jlist component.
	 * 
	 * @version $Revision: 1.8 $
	 */
	protected class PluginsTableModel
						extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		
		@SuppressWarnings("unchecked")
		private List pluginEntries;
		
		/**
		 * Constructs a new table model from the plugin managers plugin list.
		 */
		@SuppressWarnings("unchecked")
		public PluginsTableModel() {
			pluginEntries = new ArrayList();
			updateModel();
		}
		
		/**
		 * @see javax.swing.table.TableModel#isCellEditable(int, int)
		 */
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return columnIndex == 2;
		}
		
		/**
		 * Returns the class at the given column.
		 * 
		 * @param columnIndex
		 *           DOCUMENT ME!
		 * @return the class at the given column.
		 */
		@SuppressWarnings("unchecked")
		@Override
		public Class getColumnClass(int columnIndex) {
			return getValueAt(0, columnIndex).getClass();
		}
		
		/**
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		public int getColumnCount() {
			return 3;
		}
		
		/**
		 * @see javax.swing.table.TableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int columnIndex) {
			if (columnIndex == 0) {
				return sBundle.getString("table.column.icon");
			} else
				if (columnIndex == 1) {
					return sBundle.getString("table.column.pluginName");
				} else
					if (columnIndex == 2) {
						return sBundle.getString("table.column.loadOnStartup");
					} else {
						return super.getColumnName(columnIndex);
					}
		}
		
		/**
		 * Returns true if the plugin list is empty.
		 * 
		 * @return DOCUMENT ME!
		 */
		public boolean isEmpty() {
			return pluginEntries.size() == 0;
		}
		
		/**
		 * Returns the plugin description at the given index in the table.
		 * 
		 * @param index
		 *           DOCUMENT ME!
		 * @return the plugin description at the given index in the table.
		 */
		public PluginDescription getPluginDescription(int index) {
			return ((PluginEntry) pluginEntries.get(index)).getDescription();
		}
		
		/**
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		public int getRowCount() {
			return pluginEntries.size();
		}
		
		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			if (columnIndex == 2) {
				// pluginsSelectionModel.getMinSelectionIndex()
				String pluginName = (pluginsTableModel.getPluginDescription(rowIndex)).getName();
				
				pluginManager.setLoadOnStartup(pluginName, (Boolean) value);
				modifiedPluginList = true;
				fireTableCellUpdated(rowIndex, columnIndex);
			}
		}
		
		/**
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt(int rowIndex, int columnIndex) {
			PluginEntry entry = (PluginEntry) pluginEntries.get(rowIndex);
			
			if (columnIndex == 0) {
				if (entry == null || entry.getPlugin() == null)
					return null;
				ImageIcon i = entry.getPlugin().getIcon();
				if (i == null)
					return null;
				i.setImage(i.getImage().getScaledInstance(
									16, 16, Image.SCALE_SMOOTH));
				return i;
			} else
				if (columnIndex == 1) {
					return entry.getDescription().getName();
				} else
					if (columnIndex == 2) {
						return entry.getLoadOnStartup();
					} else {
						return "wrong column: " + columnIndex;
					}
		}
		
		/**
		 * Returns a human readable string of this model.
		 * 
		 * @return DOCUMENT ME!
		 */
		@Override
		public String toString() {
			return pluginEntries.toString();
		}
		
		@SuppressWarnings("unchecked")
		public void updateModel() {
			pluginEntries.clear();
			
			// pluginEntries.addAll(pluginManager.getPluginEntries());
			Object[] plugs = pluginManager.getPluginEntries().toArray();
			Arrays.sort(plugs, new EntryComparator());
			
			for (int i = 0; i < plugs.length; i++) {
				pluginEntries.add(plugs[i]);
			}
			
			fireTableDataChanged();
		}
	}
	
	/**
	 * @author $Author: klukas $
	 * @version $Revision: 1.8 $ $Date: 2010/12/22 13:05:33 $
	 */
	@SuppressWarnings("unchecked")
	class EntryComparator
						implements Comparator {
		/**
		 * @see java.util.Comparator#compare(Object, Object)
		 */
		public int compare(Object o1, Object o2) {
			return ((PluginEntry) o1).getDescription().getName().compareTo(((PluginEntry) o2).getDescription()
								.getName());
		}
	}
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
