/*
 * org.openmicroscopy.shoola.agents.metadata.editor.AnalysisResultsItem 
 *
 *------------------------------------------------------------------------------
 *  Copyright (C) 2006-2010 University of Dundee. All rights reserved.
 *
 *
 * 	This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 *------------------------------------------------------------------------------
 */
package org.openmicroscopy.shoola.agents.metadata.util;


//Java imports
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

//Third-party libraries
import org.jdesktop.swingx.JXBusyLabel;

//Application-internal dependencies
import org.openmicroscopy.shoola.agents.metadata.IconManager;
import org.openmicroscopy.shoola.agents.metadata.MetadataViewerAgent;
import org.openmicroscopy.shoola.util.ui.UIUtilities;
import pojos.DataObject;
import pojos.FileAnnotationData;

/** 
 * Component used to indicate that a given analysis was done.
 *
 * @author Jean-Marie Burel &nbsp;&nbsp;&nbsp;&nbsp;
 * <a href="mailto:j.burel@dundee.ac.uk">j.burel@dundee.ac.uk</a>
 * @author Donald MacDonald &nbsp;&nbsp;&nbsp;&nbsp;
 * <a href="mailto:donald@lifesci.dundee.ac.uk">donald@lifesci.dundee.ac.uk</a>
 * @version 3.0
 * <small>
 * (<b>Internal version:</b> $Revision: $Date: $)
 * </small>
 * @since 3.0-Beta4
 */
public class AnalysisResultsItem 
	extends JPanel
	implements ActionListener
{

	/** Bound property indicating to view the results. */ 
	public static final String ANALYSIS_RESULTS_VIEW = "analysisResultsView";
	
	/** Bound property indicating to view the results. */ 
	public static final String ANALYSIS_RESULTS_DELETE = "analysisResultsDelete";
	
	/** Bound property indicating to cancel the loading. */ 
	public static final String ANALYSIS_RESULTS_CANCEL = "analysisResultsCancel";
	
	/** The size of the loading icon. */
	private static final Dimension SIZE = new Dimension(14, 14);
	
	/** The default text. */
	private static final String DEFAULT = "Results";
	
	/** Action id indicating to delete the results. */
	private static final int DELETE = 0;
	
	/** Action id indicating to view the results. */
	private static final int VIEW = 1;
	
	/** Action id indicating to view the results. */
	private static final int CANCEL = 2;
	
	/** The collection of attachments related to the objects. */
	private List<FileAnnotationData> attachments;
	
	/** The data object to host. */
	private DataObject data;
	
	/** The name space. */
	private String		nameSpace;
	
	/** Button indicating to display the results. */
	private JButton		resultsButton;
	
	/** Button indicating to delete the results. */
	private JButton		deleteButton;
	
	/** Button indicating to cancel the data loading. */
	private JButton		cancelButton;
	
	/** The loaded results. */
	private Map<FileAnnotationData, File> results;
	
	/** The time when the analysis was done. */
	private Timestamp time;
	
	/**
	 * Converts the specified name space.
	 * 
	 * @param nameSpace The value to handle.
	 * @return See above.
	 */
	private String convertNameSpace(String nameSpace)
	{
		if (nameSpace == null || nameSpace.trim().length() == 0)
			return DEFAULT;
		String[] values = UIUtilities.splitString(nameSpace);
		return values[values.length-1].toUpperCase();
	}
	
	/** Builds and lays out the UI. */
	private void buildGUI()
	{
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		add(resultsButton);
		add(deleteButton);
	}
	
	/** 
	 * Initializes the components. 
	 * 
	 * @param nameSpace The name space to use to determine the name.
	 */
	private void initComponents(String nameSpace)
	{
		attachments = new ArrayList<FileAnnotationData>();
		this.nameSpace = nameSpace;
		resultsButton = new JButton();
		resultsButton.setText(convertNameSpace(nameSpace));
		resultsButton.setOpaque(false);
		resultsButton.setForeground(UIUtilities.HYPERLINK_COLOR);
		resultsButton.setBackground(UIUtilities.BACKGROUND_COLOR);
		UIUtilities.unifiedButtonLookAndFeel(resultsButton);
		resultsButton.setActionCommand(""+VIEW);
		resultsButton.addActionListener(this);
		
		cancelButton = new JButton();
		cancelButton.setText("Cancel");
		cancelButton.setToolTipText("Cancel results loading.");
		cancelButton.setOpaque(false);
		cancelButton.setForeground(UIUtilities.HYPERLINK_COLOR);
		cancelButton.setBackground(UIUtilities.BACKGROUND_COLOR);
		UIUtilities.unifiedButtonLookAndFeel(cancelButton);
		cancelButton.setActionCommand(""+CANCEL);
		cancelButton.addActionListener(this);
		
		IconManager icons = IconManager.getInstance();
		deleteButton = new JButton(icons.getIcon(IconManager.DELETE_12));
		deleteButton.setBackground(UIUtilities.BACKGROUND_COLOR);
		UIUtilities.unifiedButtonLookAndFeel(deleteButton);
		deleteButton.setActionCommand(""+DELETE);
		deleteButton.addActionListener(this);
		deleteButton.setVisible(false);
		setBackground(UIUtilities.BACKGROUND_COLOR);
	}
	
	/**
	 * Creates a new instance.
	 * 
	 * @param data The data object to host.
	 * @param nameSpace The name space to use to determine the name.
	 */
	public AnalysisResultsItem(DataObject data, String nameSpace)
	{
		if (data == null) 
			throw new IllegalArgumentException("Object cannot be null.");
		this.data = data;
		initComponents(nameSpace);
		buildGUI();
	}
	
	/**
	 * Returns the name space.
	 * 
	 * @return See above.
	 */
	public String getNameSpace() { return nameSpace; }
	
	/**
	 * Adds a new attachment. 
	 * 
	 * @param file The attachment to add.
	 */
	public void addAttachment(FileAnnotationData file)
	{
		long userID = MetadataViewerAgent.getUserDetails().getId();
		if (time == null) {
			time = file.getLastModified();
			resultsButton.setToolTipText("Analysis run "+
					UIUtilities.formatTime(time));
		}
		if (file.getOwner().getId() == userID)
			deleteButton.setVisible(true);
		if (!attachments.contains(file))
			attachments.add(file);
	}
	
	/**
	 * Returns the data object hosted by this component.
	 * 
	 * @return See above.
	 */
	public DataObject getData() { return data; }
	
	/**
	 * Returns the attachments.
	 * 
	 * @return See above.
	 */
	public List<FileAnnotationData> getAttachments() { return attachments; }
	
	/**
	 * Sets the loaded files.
	 * 
	 * @param results The value to set.
	 */
	public void setLoadedFiles(Map<FileAnnotationData, File> results)
	{
		this.results = results;
	}
	
	/**
	 * Returns the loaded results.
	 * 
	 * @return See above.
	 */
	public Map<FileAnnotationData, File> getResults() { return results; }
	
	/**
	 * Indicates on-going loading or not.
	 * 
	 * @param load Pass <code>true</code> to load, <code>false</code> otherwise.
	 */
	public void notifyLoading(boolean load)
	{
		removeAll();
		if (load) {
			JXBusyLabel label = new JXBusyLabel(SIZE);
			label.setBusy(true);
			label.setEnabled(true);
			add(label);
			add(cancelButton);
		} else {
			//resultsButton.setEnabled(false);
			add(resultsButton);
			if (deleteButton.isVisible())
				add(deleteButton);
		}
		revalidate();
		repaint();
	}
	
	/**
	 * Fires a property when the user clicks to view or delete the object.
	 * @see ActionListener#actionPerformed(ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		int index = Integer.parseInt(e.getActionCommand());
		switch (index) {
			case DELETE:
				firePropertyChange(ANALYSIS_RESULTS_DELETE, null, this);
				break;
			case VIEW:
				firePropertyChange(ANALYSIS_RESULTS_VIEW, null, this);
				break;
			case CANCEL:
				firePropertyChange(ANALYSIS_RESULTS_CANCEL, null, this);
		}
	}
	
	/**
	 * Overridden to return the text associated to this component.
	 * @see {@link JMenuItem#toString()}
	 */
	public String toString()
	{
		return resultsButton.getText();
	}

}
