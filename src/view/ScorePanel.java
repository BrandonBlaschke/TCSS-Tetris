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

/** Class draws a JPanel with the text on the board. 
 * 
 * @author Brandon Blaschke 
 * @version 12/2/16
 *
 */
public class ScorePanel extends JPanel implements Observer {

    /** Generated serial version UID.  */
    private static final long serialVersionUID = 2890890771986609917L;
    
    /** Font size. */
    private static final int FONT_SIZE = 30;
    
    /** Background color. */
    private static final Color BG_COLOR = new Color(212, 42, 255); 
    
    /** Dimension of box. */ 
    private static final Dimension DIM = new Dimension(150, 100);
    
    /**Indent for labels. */
    private static final String INDENT = " ";
    
    /** Score addition. */
    private static final int SCORE_ADD = 10;
    
    /** JLabel for the score. */
    private final TetrisLabel myLabel;
    
    /** JLabel for the score int. */
    private final TetrisLabel myScoreLabel;
    
    /** Score of game. */
    private int myScore; 
    
    /** Creates a new score panel. */
    public ScorePanel() {
        
        super();
        myScore = 0;
        myLabel = new TetrisLabel(" Score", FONT_SIZE);
        myScoreLabel = new TetrisLabel(INDENT + myScore, FONT_SIZE);
        settingUp();
    }
    
    /** Sets up the JPanel. */
    private void settingUp() {
        setPreferredSize(DIM);
        setAlignmentX(JLabel.CENTER_ALIGNMENT);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        setBackground(BG_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(myLabel);
        add(Box.createGlue());
        add(myScoreLabel);
        
    }

    @Override
    public void update(final Observable theO, final Object theArg) {
        
        if (theArg instanceof Integer[]) {
            
            final Integer[] lines = (Integer[]) theArg;
            
            int multiplyer = 0; 
            for (int i = 0; i < lines.length; i++) {
                multiplyer++;
            }
            
            myScore += SCORE_ADD * multiplyer; 
            myScoreLabel.setText(INDENT + myScore);
            repaint();
        }
    }
    
    /** Resets the games score. */
    public void resetScore() {
        
        myScore = 0;
        myScoreLabel.setText(INDENT + myScore);
    }
    
}
