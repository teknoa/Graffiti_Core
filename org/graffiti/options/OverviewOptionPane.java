//==============================================================================
//
//   OverviewOptionPane.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: OverviewOptionPane.java,v 1.2 2009/06/23 07:05:21 klukas Exp $

package org.graffiti.options;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

/**
 * The overview pane for the options dialog.
 *
 * @version $Revision: 1.2 $
 */
public class OverviewOptionPane
    extends AbstractOptionPane
{
    //~ Constructors ===========================================================

    /**
     * Constructor for OverviewOptionPane.
     */
    public OverviewOptionPane()
    {
        super(sBundle.getString("options.overview.title"));
    }

    //~ Methods ================================================================

    /*
     * @see org.graffiti.options.AbstractOptionPane#initDefault()
     */
    @Override
	protected void initDefault()
    {
        setLayout(new BorderLayout());

        // add a JEditorPane, which contains an overview html page.
        JEditorPane ep = new JEditorPane();

        try
        {
            ep.setPage(sBundle.getRes("options.overview.html"));
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }

        ep.setEditable(false);

        JScrollPane scroller = new JScrollPane(ep);
        scroller.setPreferredSize(new Dimension(400, 0));

        add(BorderLayout.CENTER, scroller);
    }

    /*
     * @see org.graffiti.options.AbstractOptionPane#saveDefault()
     */
    @Override
	protected void saveDefault()
    {
        /* do nothing */
    }

	/* (non-Javadoc)
	 * @see org.graffiti.options.OptionPane#getCategory()
	 */
	public String getCategory() {
		return "Gravisto Passau";
	}

	/* (non-Javadoc)
	 * @see org.graffiti.options.OptionPane#getOptionName()
	 */
	public String getOptionName() {
		return "Default Option";
	}

    /* (non-Javadoc)
     * @see org.graffiti.options.OptionPane#init(javax.swing.JComponent)
     */
    public void init(JComponent options) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.graffiti.options.OptionPane#save(javax.swing.JComponent)
     */
    public void save(JComponent options) {
        // TODO Auto-generated method stub
        
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
