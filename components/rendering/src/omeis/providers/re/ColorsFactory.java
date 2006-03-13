/*
 * omeis.providers.re.ColorsFactory
 *
 *------------------------------------------------------------------------------
 *
 *  Copyright (C) 2004 Open Microscopy Environment
 *      Massachusetts Institute of Technology,
 *      National Institutes of Health,
 *      University of Dundee
 *
 *
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public
 *    License along with this library; if not, write to the Free Software
 *    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *------------------------------------------------------------------------------
 */

package omeis.providers.re;




//Java imports

//Third-party libraries

//Application-internal dependencies
import ome.model.core.Channel;
import omeis.providers.re.metadata.ChannelBindings;

/** 
 * Utility class to determine the color usually associated to a specified
 * channel w.r.t. its emission wavelength.
 *
 * @author  Jean-Marie Burel &nbsp;&nbsp;&nbsp;&nbsp;
 * 				<a href="mailto:j.burel@dundee.ac.uk">j.burel@dundee.ac.uk</a>
 * @author  <br>Andrea Falconi &nbsp;&nbsp;&nbsp;&nbsp;
 * 				<a href="mailto:a.falconi@dundee.ac.uk">
 * 					a.falconi@dundee.ac.uk</a>
 * @version 2.2
 * <small>
 * (<b>Internal version:</b> $Revision: 1.6 $ $Date: 2005/07/05 16:13:52 $)
 * </small>
 * @since OME2.2
 */
public class ColorsFactory
{
    
    /** Identifies the red value in the RGBA-array. */
    static final int            RED = 0;
    
    /** Identifies the green value in the RGBA-array. */
    static final int            GREEN = 1;
    
    /** Identifies the blue value in the RGBA-array. */
    static final int            BLUE = 2;
    
    /** Identifies the alpha value in the RGBA-array. */
    static final int            ALPHA = 3;
    
    /** Lower bound of the blue interval. */
    private static final int    BLUE_MIN = 400;
    
    /** Upper bound of the blue interval. */
    private static final int    BLUE_MAX = 500;
    
    /** Lower bound of the green interval. */
    private static final int    GREEN_MIN = 501;
    
    /** Upper bound of the green interval. */
    private static final int    GREEN_MAX = 600;
    
    /** Lower bound of the red interval. */
    private static final int    RED_MIN = 601;
    
    /** Upper bound of the red interval. */
    private static final int    RED_MAX = 700;
    
    /** RGBA-array corresponding to the RED color. */
    private static final int[]  RED_COLOR;
    
    /** RGBA-array corresponding to the GREEN color. */
    private static final int[]  GREEN_COLOR;
    
    /** RGBA-array corresponding to the BLUE color. */
    private static final int[]  BLUE_COLOR;
    
    /** Initializes RGB-arrays. */
    static {
        RED_COLOR = new int[4];
        RED_COLOR[RED] = 255;
        RED_COLOR[GREEN] = 0;
        RED_COLOR[BLUE] = 0;
        RED_COLOR[ALPHA] = ChannelBindings.DEFAULT_ALPHA;
        GREEN_COLOR = new int[4];
        GREEN_COLOR[RED] = 0;
        GREEN_COLOR[GREEN] = 255;
        GREEN_COLOR[BLUE] = 0;
        GREEN_COLOR[ALPHA] = ChannelBindings.DEFAULT_ALPHA;
        BLUE_COLOR = new int[4];
        BLUE_COLOR[RED] = 0;
        BLUE_COLOR[GREEN] = 0;
        BLUE_COLOR[BLUE] = 255;
        BLUE_COLOR[ALPHA] = ChannelBindings.DEFAULT_ALPHA;
    }
    
    /**
     * Returns <code>true</code> if the emission wavelength is in 
     * the blue color band, <code>false</code> otherwise.
     * 
     * @param emWavelenght  The value of the emission wavelength
     * @return See above.
     */
    private static boolean rangeBlue(int emWavelenght)
    {
        if (emWavelenght <= BLUE_MAX && emWavelenght >= BLUE_MIN) return true;
        return false;
    }
    
    /**
     * Returns <code>true</code> if the emission wavelength is in 
     * the green color band, <code>false</code> otherwise.
     * 
     * @param emWave  The value of the emission wavelength
     * @return See above.
     */
    private static boolean rangeGreen(int emWave)
    {
        if (emWave >= GREEN_MIN && emWave <= GREEN_MAX) 
            return true;
        return false;
    }
    
    /**
     * Returns <code>true</code> if the emission wavelength is in 
     * the red color band, <code>false</code> otherwise.
     * 
     * @param emWave  The value of the emission wavelength
     * @return See above.
     */
    private static boolean rangeRed(int emWave)
    {
        if (emWave >= RED_MIN && emWave <= RED_MAX) return true;
        return false;
    }
    
    /**
     * Determines the color usually associated to the specified 
     * emission wavelenght.
     * 
     * @param channel A channel.
     * @return  Returns an rgb array corresponding to the color.
     */
    private static int[] getColor(Channel channel)
    {
    	int emWave = channel.getLogicalChannel().getEmissionWave().intValue();
        if (rangeBlue(emWave)) return BLUE_COLOR;
        if (rangeGreen(emWave)) return GREEN_COLOR;
        if (rangeRed(emWave)) return RED_COLOR;
        return null;
    }
    
    /**
     * Determines the color usually associated to the specified 
     * emission wavelenght.
     * 
     * @param index The channel index.
     * @param channel The channel.
     * @return Returns an rgb array corresponding to the color.
     */
    public static int[] getColor(int index, Channel channel)
    {
        int[] c = ColorsFactory.getColor(channel);
        if (c != null) return c;
        switch (index)
        {
        	case  0: return BLUE_COLOR;
        	case  1: return GREEN_COLOR;
        	default: return RED_COLOR;
        }
    }
}
