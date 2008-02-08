package ui;

/*
 *------------------------------------------------------------------------------
 *  Copyright (C) 2006-2007 University of Dundee. All rights reserved.
 *
 *
 * 	This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 *------------------------------------------------------------------------------
 *	author Will Moore will@lifesci.dundee.ac.uk
 */

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeListener;

import actions.ClearFieldsAllAction;
import actions.ClearFieldsHighltdAction;
import actions.CloseFileAction;
import actions.LoadDefaultsAllAction;
import actions.LoadDefaultsHighltdAction;
import actions.MultiplyValuesAction;
import actions.NewFileAction;
import actions.OpenFileAction;
import actions.OpenWwwFileAction;
import actions.PrintExportAllAction;
import actions.PrintExportHighltd;
import actions.RedoAction;
import actions.RedoActionNoNameRefresh;
import actions.SaveFileAction;
import actions.SaveFileAsAction;
import actions.UndoAction;
import actions.UndoActionNoNameRefresh;

import tree.DataFieldNode;
import tree.Tree.Actions;
import xmlMVC.XMLModel;

public class Controller 
	extends AbstractComponent
	implements SelectionObserver, XMLUpdateObserver {

	protected XMLModel model;
	protected XMLView view;
	protected JFrame frame = null;
	
	/** Identifies the <code>New File action</code>. */
    static final Integer     NEW_FILE = new Integer(0);
    
	/** Identifies the <code>Open File action</code>. */
    static final Integer     OPEN_FILE = new Integer(1);
    
	/** Identifies the <code>Open File action</code>. */
    static final Integer     OPEN_WWW_FILE = new Integer(2);
    
	/** Identifies the <code>Save File action</code>. */
    static final Integer     SAVE_FILE = new Integer(3);
    
    /** Identifies the <code>Save-File-As action</code>. */
    static final Integer     SAVE_FILE_AS = new Integer(4);
    
    /** Identifies the <code>Print-Export all action</code>. */
    static final Integer     PRINT_EXPORT_ALL = new Integer(5);
    
    /** Identifies the <code>Print-Export action</code>. */
    static final Integer     PRINT_EXPORT_HIGHLT = new Integer(6);
    
    /** Identifies the <code>Load Defaults action</code>. */
    static final Integer     LOAD_DEFAULTS_ALL = new Integer(7);
    
    /** Identifies the <code>Load Defaults Highltd action</code>. */
    static final Integer     LOAD_DEFAULTS_HIGHLT = new Integer(8);
    
    /** Identifies the <code>Clear Fields All action</code>. */
    static final Integer     CLEAR_FIELDS_ALL = new Integer(9);
    
    /** Identifies the <code>Clear Fields Highltd action</code>. */
    static final Integer     CLEAR_FIELDS_HIGHLT = new Integer(10);
    
    /** Identifies the <code>Multiply values action</code>. */
    static final Integer     MULTIPLY_VALUES = new Integer(11);
    
    /** Identifies the <code>Undo action</code>. */
    static final Integer     UNDO = new Integer(12);
    /** Identifies the <code>Undo action </code>. - Name is not shown (use for button) */
    static final Integer     UNDO_NO_NAME = new Integer(13);
    
    /** Identifies the <code>Redo action</code>. */
    static final Integer     REDO = new Integer(14);
    /** Identifies the <code>Redo action</code>. - Name is not shown (use for button) */
    static final Integer     REDO_NO_NAME = new Integer(15);
    
    /** Identifies the <code>CloseFile action</code>. */
    static final Integer     CLOSE_FILE = new Integer(16);
    
    
    /** Maps actions ids onto actual <code>Action</code> object. */
    private Map<Integer, Action>	actionsMap;
	
	public Controller (XMLModel model, XMLView view) {
	
		this.model = model;
		// listen for changes in selection AND changes in XML (should only be observing one really!)
		model.addSelectionObserver(this);
		model.addXMLObserver(this);
		this.view = view;
		actionsMap = new HashMap<Integer, Action>();
		createActions();
	}
	
	
	/** Helper method to create all the UI actions. */
    private void createActions()
    {
        actionsMap.put(NEW_FILE, new NewFileAction(model));
        actionsMap.put(OPEN_FILE, new OpenFileAction(model));
        actionsMap.put(OPEN_WWW_FILE, new OpenWwwFileAction(model));
        actionsMap.put(SAVE_FILE, new SaveFileAction(model));
        actionsMap.put(SAVE_FILE_AS, new SaveFileAsAction(model));
        actionsMap.put(PRINT_EXPORT_ALL, new PrintExportAllAction(model));
        actionsMap.put(PRINT_EXPORT_HIGHLT, new PrintExportHighltd(model));
        actionsMap.put(LOAD_DEFAULTS_ALL, new LoadDefaultsAllAction(model));
        actionsMap.put(LOAD_DEFAULTS_HIGHLT, new LoadDefaultsHighltdAction(model));
        actionsMap.put(CLEAR_FIELDS_ALL, new ClearFieldsAllAction(model));
        actionsMap.put(CLEAR_FIELDS_HIGHLT, new ClearFieldsHighltdAction(model));
        actionsMap.put(MULTIPLY_VALUES, new MultiplyValuesAction(model));
        actionsMap.put(UNDO, new UndoAction(model));
        actionsMap.put(UNDO_NO_NAME, new UndoActionNoNameRefresh(model));
        actionsMap.put(REDO, new RedoAction(model));
        actionsMap.put(REDO_NO_NAME, new RedoActionNoNameRefresh(model));
        actionsMap.put(CLOSE_FILE, new CloseFileAction(model));
        
    }
    
    /**
     * Returns the action corresponding to the specified id.
     * 
     * @param id One of the flags defined by this class.
     * @return The specified action.
     */
    Action getAction(Integer id) { return actionsMap.get(id); }

    
    /**
	 * called by the model when selection is changed.
	 * Notifies all observers of a change.
	 */
	public void selectionChanged() {
		this.fireStateChange();
	}

	public void xmlUpdated() {
		selectionChanged();
	}
	
}
