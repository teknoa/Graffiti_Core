/*******************************************************************************
 * 
 *    Copyright (c) 2003-2007 Network Analysis Group, IPK Gatersleben
 * 
 *******************************************************************************/

package org;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;


public class MarkComponent extends JComponent {
	private static final long serialVersionUID = 1L;
	
	JLabel b1 = new JLabel();
	JLabel b2 = new JLabel();
	JLabel bb1 = new JLabel();
	JLabel bb2 = new JLabel();
	Color selCol = new Color(180, 180, 255);
	Color selColBB = null;
	JComponent comp;
	private boolean marked;

	private boolean requestFocus;

	public MarkComponent(JComponent comp, boolean marked, double width, boolean requestFocus) {
		
		this.comp = comp;
		this.requestFocus = requestFocus;
		
		setLayout(TableLayout.getLayout(new double[] {5, 1, width, 1, 5}, TableLayout.PREFERRED));
		add(b1, "0,0");
		add(bb1, "1,0");
		add(comp, "2,0");
		add(bb2, "3,0");
		add(b2, "4,0");
		this.marked = marked;
		updateMarked();
	}
	
	private void updateMarked() {
		b1.setOpaque(marked);
		b2.setOpaque(marked);
		bb1.setOpaque(marked);
		bb2.setOpaque(marked);
		if (marked) {
			b1.setBackground(selCol);
			b2.setBackground(selCol);
			bb1.setBackground(selColBB);
			bb2.setBackground(selColBB);
		} else {
			b1.setBackground(null);
			b2.setBackground(null);
			bb1.setBackground(null);
			bb2.setBackground(null);
		}
	}
	
	public void setMarkColor(Color c, Color gapColor) {
		this.selCol = c;
		this.selColBB = gapColor;
		updateMarked();
		b1.repaint();
		b2.repaint();
		bb1.repaint();
		bb2.repaint();
	}
	
	public void setMark(final boolean markedReq) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (marked == markedReq)
					return;
				marked = markedReq;
				updateMarked();
				repaint();
				if (marked && requestFocus)
					comp.requestFocusInWindow();
			}});
	}

	public static void initLinearMarkSequence(final MarkComponent markGotoFromEnd, final MarkComponent ... mark) {
		final ArrayList<MarkComponent> mcs = new ArrayList<MarkComponent>();
		for (MarkComponent mc : mark) {
			mcs.add(mc);
		}
		for (int i = 0; i<mcs.size(); i++) {
			MarkComponent mc = mcs.get(i);
			Component c = mc.comp;
			if (c instanceof JComboBox) {
				JComboBox jc = (JComboBox)c;
				final int ti = i;
				jc.addActionListener(getUpdateCommand(mcs, ti, markGotoFromEnd, mark));
				
			}
			if (c instanceof JCheckBox) {
				JCheckBox jc = (JCheckBox)c;
				final int ti = i;
				jc.addActionListener(getUpdateCommand(mcs, ti, markGotoFromEnd, mark));
				
			}
			if (c instanceof JButton) {
				JButton jb = (JButton)c;
				final int ti = i;
				jb.addActionListener(getUpdateCommand(mcs, ti, markGotoFromEnd, mark));
			}
		}
	}
	
	private static boolean invokePending = false;

	private static ActionListener getUpdateCommand(final ArrayList<MarkComponent> mcs, final int ti, final MarkComponent jump, final MarkComponent... mark) {
		return new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				invokePending = true;
				SwingUtilities.invokeLater(new Runnable(){
					public void run() {
						if (!invokePending)
							return;
						try {
							int idx = ti+1;
							if (idx>=mcs.size())
								idx = mcs.indexOf(jump);
							if (idx<0)
								idx = 0;
							MarkComponent.markComponent(mcs.get(idx), mark);
						} finally {
							invokePending = false;
						}
					}});
			}};
	}

	protected static void markComponent(
			MarkComponent markThis,
			MarkComponent ... allMarks) {
		for (MarkComponent mc : allMarks) {
			if (mc!=markThis)
				mc.setMark(false);
			else
				mc.setMark(true);
		}
	}
}
