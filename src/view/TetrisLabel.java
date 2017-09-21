package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

/** Custom JLabel class that displays the right
 * format for the text on screen. 
 * @author Brandon Blaschke 
 * @version 12/9/16
 *
 */
public class TetrisLabel extends JLabel {

    /** Generated serial version UID.  */
    private static final long serialVersionUID = 2794073355949444018L;
    
    /** Creates a new JLabel with Verdana font, color white, and a size.
     * 
     * @param theText Text to be displayed
     * @param theFontSize Font size. 
     */
    TetrisLabel(final String theText, final int theFontSize) {
        
        super(theText);
        setFont(new Font("Verdana ", Font.PLAIN, theFontSize));
        setForeground(Color.WHITE);
    }
}
