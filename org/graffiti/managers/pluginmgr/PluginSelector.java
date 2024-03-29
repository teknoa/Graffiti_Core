// ==============================================================================
//
// PluginSelector.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================
// $Id: PluginSelector.java,v 1.8 2010/12/22 13:05:33 klukas Exp $

package org.graffiti.managers.pluginmgr;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.graffiti.core.ImageBundle;
import org.graffiti.core.StringBundle;

/**
 * Represents a plugin selector. A simple dialog to pick the name of a plugin.
 * The dialog filters already loaded plugins from the list of plugins.
 * 
 * @version $Revision: 1.8 $
 */
public class PluginSelector
					extends JDialog
					implements ActionListener, MouseListener, ListSelectionListener,
					WindowListener {
	// ~ Static fields/initializers =============================================
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The logger for the current class. */
	
	// ~ Instance fields ========================================================
	
	/** The <code>ImageBundle</code> of the plugin selector. */
	protected ImageBundle iBundle = ImageBundle.getInstance();
	
	/** The <code>StringBundle</code> of the plugin seletor. */
	protected StringBundle sBundle = StringBundle.getInstance();
	
	/** The buttons of this dialog. */
	private JButton cancel;
	
	/** The buttons of this dialog. */
	private JButton ok;
	
	/** The description of the list. */
	private JLabel description;
	
	/** The list of this dialog. */
	private JList list;
	
	/** The scroll pane of the JList. */
	private JScrollPane scrolledList;
	
	/** The plugin description of the selected item. */
	private Object[] selectedItems;
	
	// ~ Constructors ===========================================================
	
	/**
	 * Constructs a new plugin selector from the given plugin description
	 * collector.
	 * 
	 * @param parent
	 *           the parent dialog.
	 * @param pluginDescriptionCollector
	 *           the collector of plugin descriptions.
	 */
	@SuppressWarnings("unchecked")
	public PluginSelector(PluginManagerDialog parent,
						PluginDescriptionCollector pluginDescriptionCollector) {
		super(parent, true);
		
		// setSize(420, 320);
		setResizable(true);
		
		setTitle(sBundle.getString("selector.title"));
		
		List plugins = null;
		
		try {
			parent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			plugins = createPluginDescriptionList(pluginDescriptionCollector);
		} finally {
			parent.setCursor(Cursor.getDefaultCursor());
		}
		
		// display only plugins, which are not loaded yet
		List filteredPlugins = new LinkedList();
		
		for (Iterator i = plugins.iterator(); i.hasNext();) {
			PluginEntry e = (PluginEntry) i.next();
			
			String name = e.getDescription().getName();
			
			if (!parent.getPluginManager().isInstalled(name)) {
				filteredPlugins.add(e);
			}
		}
		
		Object[] fplugs = filteredPlugins.toArray();
		Arrays.sort(fplugs, new EntryComparator());
		list = new JList(fplugs);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setCellRenderer(new PluginDescriptionCellRenderer());
		scrolledList = new JScrollPane(list);
		scrolledList.setPreferredSize(new Dimension(400, 250));
		
		cancel = new JButton(sBundle.getString("selector.button.cancel"));
		cancel.setIcon(iBundle.getIcon("selector.button.cancel.icon"));
		
		description = new JLabel(sBundle.getString("selector.list.title"));
		description.setPreferredSize(new Dimension(200, 20));
		
		ok = new JButton(sBundle.getString("selector.button.ok"));
		ok.setIcon(iBundle.getIcon("selector.button.ok.icon"));
		ok.setEnabled(false);
		
		getRootPane().setDefaultButton(ok);
		
		defineLayout();
		addListeners();
		pack();
		setLocationRelativeTo(parent);
	}
	
	// ~ Methods ================================================================
	
	/**
	 * Returns true, if the list is empty.
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean isEmpty() {
		return list.getModel().getSize() == 0;
	}
	
	/**
	 * Returns the selected plugin entries.
	 * 
	 * @see PluginEntry
	 */
	public PluginEntry[] getSelectedItems() {
		if ((selectedItems == null) || (selectedItems.length == 0)) {
			return null;
		}
		
		PluginEntry[] e = new PluginEntry[selectedItems.length];
		
		for (int i = 0; i < selectedItems.length; i++) {
			e[i] = (PluginEntry) selectedItems[i];
		}
		
		return e;
	}
	
	/**
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (src == cancel) {
			dispose();
		} else
			if (src == ok) {
				okSelected();
			}
	}
	
	/**
	 * Updates the ok button.
	 * 
	 * @param e
	 *           DOCUMENT ME!
	 */
	public void checkEnableOK(ListSelectionEvent e) {
		ok.setEnabled(list.getSelectedIndex() != -1);
	}
	
	/**
	 * @see java.awt.event.MouseListener#mouseClicked(MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			okSelected();
		}
	}
	
	/**
	 * @see java.awt.event.MouseListener#mouseEntered(MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
		// do nothing
	}
	
	/**
	 * @see java.awt.event.MouseListener#mouseExited(MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		// do nothing
	}
	
	/**
	 * @see java.awt.event.MouseListener#mousePressed(MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		// do nothing
	}
	
	/**
	 * @see java.awt.event.MouseListener#mouseReleased(MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		// do nothing
	}
	
	/**
	 * Called, if ok is selected.
	 */
	public void okSelected() {
		selectedItems = list.getSelectedValues();
		
		dispose();
	}
	
	/**
	 * @see javax.swing.event.ListSelectionListener#valueChanged(ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent e) {
		checkEnableOK(e);
	}
	
	/**
	 * @see java.awt.event.WindowListener#windowActivated(WindowEvent)
	 */
	public void windowActivated(WindowEvent arg0) {
		// do nothing
	}
	
	/**
	 * @see java.awt.event.WindowListener#windowClosed(WindowEvent)
	 */
	public void windowClosed(WindowEvent arg0) {
		// do nothing
	}
	
	/**
	 * @see java.awt.event.WindowListener#windowClosing(WindowEvent)
	 */
	public void windowClosing(WindowEvent arg0) {
		dispose();
	}
	
	/**
	 * @see java.awt.event.WindowListener#windowDeactivated(WindowEvent)
	 */
	public void windowDeactivated(WindowEvent arg0) {
		// do nothing
	}
	
	/**
	 * @see java.awt.event.WindowListener#windowDeiconified(WindowEvent)
	 */
	public void windowDeiconified(WindowEvent arg0) {
		// do nothing
	}
	
	/**
	 * @see java.awt.event.WindowListener#windowIconified(WindowEvent)
	 */
	public void windowIconified(WindowEvent arg0) {
		// do nothing
	}
	
	/**
	 * @see java.awt.event.WindowListener#windowOpened(WindowEvent)
	 */
	public void windowOpened(WindowEvent arg0) {
		// do nothing
	}
	
	/**
	 * Adds the listeners to the dialog.
	 */
	private void addListeners() {
		cancel.addActionListener(this);
		ok.addActionListener(this);
		list.addMouseListener(this);
		list.addListSelectionListener(this);
		addWindowListener(this);
	}
	
	/**
	 * Generates and returns the plugin description list.
	 * 
	 * @see PluginEntry
	 */
	private List<?> createPluginDescriptionList(
						PluginDescriptionCollector collector) {
		return collector.collectPluginDescriptions();
	}
	
	/**
	 * Defines the layout of this dialog.
	 */
	private void defineLayout() {
		getContentPane().setLayout(new GridBagLayout());
		
		GridBagConstraints labelConstraints = new GridBagConstraints();
		labelConstraints.gridx = 0;
		labelConstraints.gridy = 0;
		labelConstraints.gridwidth = GridBagConstraints.REMAINDER;
		labelConstraints.gridheight = 1;
		labelConstraints.fill = GridBagConstraints.BOTH;
		labelConstraints.anchor = GridBagConstraints.WEST;
		labelConstraints.weightx = 1.0;
		labelConstraints.weighty = 0.0;
		labelConstraints.insets = new Insets(8, 8, 0, 8);
		getContentPane().add(description, labelConstraints);
		
		GridBagConstraints listConstraints = new GridBagConstraints();
		listConstraints.gridx = 0;
		listConstraints.gridy = 1;
		listConstraints.gridwidth = 4;
		listConstraints.gridheight = 1;
		listConstraints.fill = GridBagConstraints.BOTH;
		listConstraints.anchor = GridBagConstraints.CENTER;
		listConstraints.weightx = 1.0;
		listConstraints.weighty = 1.0;
		listConstraints.insets = new Insets(8, 8, 8, 8);
		getContentPane().add(scrolledList, listConstraints);
		
		GridBagConstraints okConstraints = new GridBagConstraints();
		okConstraints.gridx = 2;
		okConstraints.gridy = 2;
		okConstraints.gridwidth = 1;
		okConstraints.gridheight = 1;
		okConstraints.anchor = GridBagConstraints.EAST;
		okConstraints.insets = new Insets(0, 8, 8, 8);
		getContentPane().add(ok, okConstraints);
		
		GridBagConstraints cancelConstraints = new GridBagConstraints();
		cancelConstraints.gridx = 3;
		cancelConstraints.gridy = 2;
		cancelConstraints.gridwidth = 1;
		cancelConstraints.gridheight = 1;
		cancelConstraints.anchor = GridBagConstraints.EAST;
		cancelConstraints.insets = new Insets(0, 8, 8, 8);
		getContentPane().add(cancel, cancelConstraints);
	}
	
	// ~ Inner Classes ==========================================================
	
	/**
	 * Renders the plugin description in a <code>JList</code>.
	 */
	static class PluginDescriptionCellRenderer
						extends DefaultListCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * Constructs a new plugin description cell renderer.
		 */
		public PluginDescriptionCellRenderer() {
		}
		
		/**
		 * Returns the component at the given index in the list.
		 * 
		 * @param list
		 *           DOCUMENT ME!
		 * @param value
		 *           DOCUMENT ME!
		 * @param modelIndex
		 *           DOCUMENT ME!
		 * @param isSelected
		 *           DOCUMENT ME!
		 * @param cellHasFocus
		 *           DOCUMENT ME!
		 * @return DOCUMENT ME!
		 */
		@Override
		public Component getListCellRendererComponent(JList list, Object value,
							int modelIndex, boolean isSelected, boolean cellHasFocus) {
			Component c = super.getListCellRendererComponent(list, value,
								modelIndex, isSelected, cellHasFocus);
			
			PluginEntry e = (PluginEntry) value;
			
			// TODO setIcon() (perhaps, but creating an instance of the plugin
			// to get the plugin's icon is somehow a bad idea)
			setText(displayString(e.getDescription()));
			setToolTipText(displayToolTip(e));
			
			return c;
		}
		
		/**
		 * DOCUMENT ME!
		 * 
		 * @param d
		 *           DOCUMENT ME!
		 * @return DOCUMENT ME!
		 */
		public static String displayString(PluginDescription d) {
			return d.getName(); // + " - " + d.getMain();
			
			// return d.getName() + "  (" + d.getVersion() + ")";
		}
		
		/**
		 * DOCUMENT ME!
		 * 
		 * @param e
		 *           DOCUMENT ME!
		 * @return DOCUMENT ME!
		 */
		public static String displayToolTip(PluginEntry e) {
			PluginDescription d = e.getDescription();
			
			return "<html>Version:     " + d.getVersion() +
								"<br>Description: " + d.getDescription() + "<br>Available:   " +
								d.getAvailable() + "<br>Main class:  " + d.getMain() +
								"<br>Author:      " + d.getAuthor() + "<br><br>" + "Loaded from: " +
								e.getFileName() + "</html>";
		}
	}
	
	/**
	 * DOCUMENT ME!
	 * 
	 * @author $Author: klukas $
	 * @version $Revision: 1.8 $ $Date: 2010/12/22 13:05:33 $
	 */
	class EntryComparator
						implements Comparator<Object> {
		/**
		 * Compares to entries via <code>getDescription().getName()</code>. A
		 * String starting with an underscore ("_") precedes all other
		 * strings.
		 * 
		 * @see java.util.Comparator#compare(Object, Object)
		 */
		public int compare(Object o1, Object o2) {
			String s1 = ((PluginEntry) o1).getDescription().getName();
			String s2 = ((PluginEntry) o2).getDescription().getName();
			
			if (s1.startsWith("_")) {
				if (s2.startsWith("_")) {
					return s1.substring(1).compareTo(s2.substring(1));
				} else {
					return -1;
				}
			} else
				if (s2.startsWith("_")) {
					return +1;
				} else {
					return s1.compareTo(s2);
				}
			
			// return ((Entry)o1).getDescription().getName().compareTo(
			// ((Entry)o2).getDescription().getName());
		}
	}
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
