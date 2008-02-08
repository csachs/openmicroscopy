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

/**
 *  see org.openmicroscopy.shoola.util.ui.component.ObservableComponent
 */

import javax.swing.event.ChangeListener;

public interface ObservableComponent {
	
	/**
     * Registers an observer with this component.
     * The observer will be notified of <i>every</i> state change.
     * 
     * @param observer The observer to register.
     * @throws NullPointerException If <code>observer</code> is 
     *                              <code>null</code>.
     * @see #removeChangeListener(ChangeListener)
     */
    public void addChangeListener(ChangeListener observer);
    
    /**
     * Removes an observer from the change notification list.
     * 
     * @param observer The observer to remove.
     * @throws NullPointerException If <code>observer</code> is 
     *                              <code>null</code>.
     * @see #addChangeListener(ChangeListener)
     */
    public void removeChangeListener(ChangeListener observer);

}
