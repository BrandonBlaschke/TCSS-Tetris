package view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/** Class displays information on a JPanel. 
 * @author Brandon Blaschke
 * @version 12/9/16
 *
 */
public class InfoPanel extends JPanel implements Observer {
    
    /** Generated serial version UID.  */
    private static final long serialVersionUID = -7444043264421214177L;
    
    /** Font size. */
    private static final int FONT_SIZE = 20;
    
    /** Background color. */
    private static final Color BG_COLOR = new Color(212, 42, 255); 
    
    /** Dimension of box. */ 
    private static final Dimension DIM = new Dimension(150, 125);
    
    /** Level cap for lines to next level. */
    private static final int LEVEL_CAP = 5;
    
    /** String for lines cleared. */
    private static final String CLEARED_STRING = " Cleared: ";
    
    /** String for current level. */
    private static final String LEVEL_STRING = " Level: ";
    
    /** String for lines to go. */
    private static final String LVL_TO_GO = " Next Level: ";
    
    /** Initial timer delay. */
    //Set to 510 to off set the first level delay. 
    private static final int DELAY = 512; 
    
    /** Delay multiplier. */
    private static final int DELAY_MULT = 12;
    
    /** Timer delay cap. */
    private static final int DELAY_CAP = 350;
    
    /** JLabel for the score. */
    private final TetrisLabel myLabel;
    
    /** Label for current level. */
    private final TetrisLabel myLevelLabel;

    /** Lines to next game. */
    private final TetrisLabel myLinesToGo;
    
    /** Lines cleared JLabel. */
    private final TetrisLabel myClearedLabel;
    
    /** Lines cleared. */
    private int myLinesCleared;
    
    /** Lines to go. */
    private int myIntLinesToGo;
    
    /** Current level. */
    private int myLevel;
    
    /** Timer of the game. */
    private final Timer myTimer; 
    
    /** Creates the info JPanel. 
     * @param theTimer Timer of the game. 
     */
    public InfoPanel(final Timer theTimer) {
        
        super();
        myLinesCleared = 0;
        myLevel = 1;
        myIntLinesToGo = LEVEL_CAP;
        myLabel = new TetrisLabel(" Information", FONT_SIZE);
        myClearedLabel = new TetrisLabel(CLEARED_STRING + 0, FONT_SIZE);
        myLevelLabel = new TetrisLabel(LEVEL_STRING + 1, FONT_SIZE);
        myLinesToGo = new TetrisLabel(LVL_TO_GO + LEVEL_CAP, FONT_SIZE);
        myTimer = theTimer; 
        settingUp();
    }
    
    /** Sets up the InfoPanel. */
    private void settingUp() {
        setPreferredSize(DIM);
        setAlignmentX(JLabel.CENTER_ALIGNMENT);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        setBackground(BG_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        //Layout out of panel
        add(myLabel);
        add(Box.createGlue());
        add(myClearedLabel);
        add(Box.createGlue());
        add(myLevelLabel);
        add(Box.createGlue());
        add(myLinesToGo);
    }

    @Override
    public void update(final Observable theO, final Object theArg) {
        
        if (theArg instanceof Integer[]) {
            
            final Integer[] lines = (Integer[]) theArg;
            
            int totalLines = 0; 
            for (int i = 0; i < lines.length; i++) {
                totalLines++;
            }
            
            //Lines cleared
            myLinesCleared += totalLines;
            myClearedLabel.setText(CLEARED_STRING + myLinesCleared);
            
            //Current level
            myLevel = (int) (myLinesCleared + LEVEL_CAP) / LEVEL_CAP;
            myLevelLabel.setText(LEVEL_STRING + myLevel);
            
            //Lines to go
            myIntLinesToGo = LEVEL_CAP - myLinesCleared % LEVEL_CAP;
            myLinesToGo.setText(LVL_TO_GO + myIntLinesToGo);
            
            //Set Timer delay
            if (DELAY - myLevel * DELAY_MULT <= DELAY_CAP) {
                
                myTimer.setDelay(DELAY_CAP);
            } else {
                myTimer.setDelay(DELAY - myLevel * DELAY_MULT);
            }
            
        }
        
    }
    
    /** Resets the lines cleared, level, and lines to go. */
    public void restInformation() {
        
        myLinesCleared = 0;
        myLevel = 1;
        myIntLinesToGo = LEVEL_CAP;
        
        myClearedLabel.setText(CLEARED_STRING + 0);
        myLevelLabel.setText(LEVEL_STRING + 1);
        myLinesToGo.setText(LVL_TO_GO + myIntLinesToGo);
        
        myTimer.setDelay(DELAY - DELAY_MULT);
    }

}
