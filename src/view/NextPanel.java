package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Block;
import model.MovableTetrisPiece;

/** Class creates a JPanel that shows the next piece on the board.
 * 
 * @author Brandon Blaschke
 * @version 12/2/16
 *
 */
public class NextPanel extends JPanel implements Observer {
    
    /** Generated serial version UID.  */
    private static final long serialVersionUID = 6108123800894468548L;
    
    /** Font size. */
    private static final int FONT_SIZE = 25;
    
    /** Background color. */
    private static final Color BG_COLOR = new Color(212, 42, 255); 
    
    /** Dimension of box. */ 
    private static final Dimension DIM = new Dimension(150, 100);
    
    /** Scale of blocks. */
    private static final int SCALE = 18;
    
    /** Module for dividing the grid. */
    private static final int GRID_MOD = 4;
    
    /** Put in middle of panel x-axis. */
    private static final int MIDX = 50;
    
    /** Put in middle of panel y-axis. */
    private static final int MIDY = 45;
    
    /** Stroke thickness. */
    private static final int STROKE = 5;
    
    /** JLabel for the score. */
    private final JLabel myLabel;
    
    /** Block to be drawn. */
    private Block myBlock;
    
    /** String to draw shapes. */
    private String myStringBlock;
    
    /** Creates the NextPanel. */
    public NextPanel() {
        
        super();
        myLabel = new JLabel("Next");
        myBlock = Block.EMPTY;
        myStringBlock = "";
        settingUp();
    }
    
    /** Sets up the JPanel. */
    private void settingUp() {
        setPreferredSize(DIM);
        setAlignmentX(JLabel.CENTER_ALIGNMENT);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        setBackground(BG_COLOR);
        myLabel.setFont(new Font("Verdana ", Font.PLAIN, FONT_SIZE));
        myLabel.setForeground(Color.WHITE);
        add(myLabel);
        
    }
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics; 
        
        //For better quality objects to be drawn. 
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        //Color of block
        final Color blockColor = DetermineColor.findColor(myBlock);
        
        for (int i = 0; i < myStringBlock.length(); i++) {
           
            final char c = myStringBlock.charAt(i);
            
            if ('I' <= c && c <= 'Z') {
                
                final int x = (i % GRID_MOD) * SCALE + MIDX;
                final int y = SCALE * ((int) i / GRID_MOD) + MIDY;
                
                g2d.setPaint(Color.BLACK);
                g2d.setStroke(new BasicStroke(STROKE));
                g2d.setPaint(blockColor);
                g2d.fillRect(x, y, SCALE, SCALE);
                g2d.setPaint(Color.BLACK);
                g2d.drawRect(x, y, SCALE, SCALE);
            }
        }
    }

    @Override
    public void update(final Observable theO, final Object theArg) {
        
        if (theArg instanceof MovableTetrisPiece) {
            
            //Get block piece
            final MovableTetrisPiece mt = (MovableTetrisPiece) theArg;
            myBlock = mt.getBlock();
            
            //Take out the string characters not needed. 
            myStringBlock = mt.toString();
            myStringBlock = myStringBlock.replace("\n", "");
            //Remove first 4 strings in front of block. 
            myStringBlock = myStringBlock.substring(GRID_MOD);
            repaint();
        }
    }

}
