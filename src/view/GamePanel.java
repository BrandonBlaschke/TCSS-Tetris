/* 
 * TCSS 305 – Autumn 2016  
 * Assignment 6 – Tetris
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.Block;
import model.Board;

/** Class draws Tetris shapes and game board. 
 * 
 * @author Brandon Blaschke 
 * @version 12/2/16
 */
public class GamePanel extends JPanel implements Observer {

    /** Generated serial version UID. */
    private static final long serialVersionUID = 1860979716621182121L;
    
    /** Width of JPanel. */
    private static final int WIDTH = 300;
    
    /** Height of JPanel. */
    private static final int HEIGHT = 600; 
    
    /** Dimensions. */
    private static final Dimension DIMENSIONS = new Dimension(WIDTH, HEIGHT);
    
    /** Gradient for background. */
    private static final GradientPaint GRADIENT = 
                    new GradientPaint(0, 0, Color.BLACK, 0, 500, new Color(42, 212, 255));
    
    /** Stroke thickness. */
    private static final int STROKE = 5;
    
    /** Multiplier to remove initial rows. */
    private static final int INIT_ROW = 5; 
    
    /** Grid module. */
    private int myGridMod; 
    
    /** Scale to draw. */
    private int myScale;
    
    /** The game board. */
    private Board myBoard; 
    
    /** String representation of the board. */
    private String myStringBoard; 
    
    /** Control of key events. */
    private boolean myKeysWork; 
    
    /** Grid control. */
    private boolean myGridShow;
    
    /** Creates new GamePanel. 
     * @param theBoard Board for the game. 
     * */
    public GamePanel(final Board theBoard) {
        
        super();
        addKeyListener(new MyKeyListener());
        
        setBackground(Color.GRAY);
        myBoard = theBoard;
        myScale = WIDTH / theBoard.getWidth();
        myGridMod = theBoard.getWidth();
        myKeysWork = false;
        this.setPreferredSize(DIMENSIONS);
        myStringBoard = "";
    }
    
    /** Draws the Tetris board and pieces. 
     * theGraphics Graphics used to draw the board. 
     */
    @Override 
    public void paintComponent(final Graphics theGraphics) {
        
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics; 
        
        //For better quality objects to be drawn. 
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        //Create Background colors.
        g2d.setStroke(new BasicStroke(STROKE));
        g2d.setPaint(GRADIENT);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);
        g2d.setPaint(Color.BLACK);
        g2d.drawRect(-1, 0, WIDTH, HEIGHT);
        
        
        //Variables used in loop. 
        int x = 0;
        int y = 0;
        Block block = Block.EMPTY;
        
        //Draw blocks
        for (int i = 0; i < myStringBoard.length(); i++) {
            
            final char c =  myStringBoard.charAt(i);
            
            if ('I' <= c && c <= 'Z') {
                
                x = (i % myGridMod) * myScale;
                y = myScale * ((int) i / myGridMod);
                
                block = DetermineColor.findBlock(c);
                g2d.setPaint(DetermineColor.findColor(block));
                g2d.fillRect(x, y, myScale, myScale);
                g2d.setPaint(Color.BLACK);
                g2d.drawRect(x, y, myScale, myScale);
            }
            
        }
        
        //Draw the grid on the game panel
        if (myGridShow) {
            g2d.setStroke(new BasicStroke(2));
            g2d.setPaint(Color.GRAY);
            
            // X axis lines
            for (int i = 0; i < WIDTH; i++) {

                if (i % myScale == 0) {

                    g2d.drawLine(i, 0, i, HEIGHT);
                }
            }
            
            //Y axis lines
            for (int i = 0; i < HEIGHT; i++) {
                
                if (i % myScale == 0) {
                    
                    g2d.drawLine(0, i, WIDTH, i);
                }
            }
        }
        
    }
    
    @Override
    public void update(final Observable theO, final  Object theArg) {
        
        if (theArg instanceof String) {
            
            //Reassign the scale and gridMod. 
            myScale = WIDTH / myBoard.getWidth();
            myGridMod = myBoard.getWidth();
            
            String boardString = (String) theArg;
            
            //Remove characters that are not needed in drawing the board. 
            boardString = boardString.substring((myBoard.getWidth() + 2) * INIT_ROW);
            boardString = boardString.replace("-", "");
            boardString = boardString.replace("|", "");
            boardString = boardString.replace("\n", "");
            myStringBoard = boardString; 
        }
        
        repaint();
        
    }
    
    /** Enable or disable key functions. 
     * @param theKey True keys enabled, False keys disabled.
     */
    public void setKeys(final boolean theKey) {
        myKeysWork = theKey;
    }
    
    /** Enable or disable grid.
     * @param theGrid True if grid shown, False for no grid.
     */
    public void setGrid(final boolean theGrid) {
        myGridShow = theGrid;
    }
    
    /** Set new board. 
     * @param theBoard New board to be set to. 
     * */
    public void setBoard(final Board theBoard) {
        myBoard = theBoard; 
    }

    /** Class listens to key events and controls game pieces. 
     * @author Brandon Blaschke 
     * @version 12/2/16
     */
    private class MyKeyListener extends KeyAdapter   {
        
        @Override
        public void keyPressed(final KeyEvent theE) { 
            
            // Control the movement of pieces.
            if (myKeysWork) {
                if (theE.getKeyCode() == KeyEvent.VK_LEFT) {

                    myBoard.left();
                } else if (theE.getKeyCode() == KeyEvent.VK_RIGHT) {

                    myBoard.right();
                } else if (theE.getKeyCode() == KeyEvent.VK_UP) {

                    myBoard.rotate();
                } else if (theE.getKeyCode() == KeyEvent.VK_DOWN) {

                    myBoard.down();
                } else if (theE.getKeyCode() == KeyEvent.VK_SPACE) {

                    myBoard.drop();
                }
            }
        }
        
    }
}
