/* 
 * TCSS 305 – Autumn 2016  
 * Assignment 6 – Tetris
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Board;

/**Creates the GUI of the Tetris game. 
 * 
 * @author Brandon Blaschke
 * @version 12/2/16
 */
public class TetrisGUI implements Observer {
    
    /** Delay for Timer in milliseconds. */
    private static final int DELAY = 500;
    
    /** Space between panels. */
    private static final int SPACE = 15; 
    
    /** Background color. */
    private static final Color BG_COLOR = Color.WHITE;
    
    /** The JFrame for the game. */
    private final JFrame myJFrame; 
    
    /** Image icon. */
    private final ImageIcon myIcon;
    
    /** Timer. */
    private final Timer myTimer;
    
    /** Game Board. */
    private Board myBoard; 
    
    /** Game Panel. */
    private final GamePanel myGamePanel;
    
    /** Score Panel. */
    private final ScorePanel myScorePanel;
    
    /** Panel displays the next piece. */
    private final NextPanel myNextPanel;
    
    /** Info Panel. */
    private final InfoPanel myInfoPanel;
    
    /** Tetris Menu Bar. */
    private final TetrisMenuBar myMenuBar; 
    
    /** Creates the GUI class. */
    public TetrisGUI() {
        
        myJFrame = new JFrame("Tetris");
        myIcon = new ImageIcon("images//tetris_logo.png");
        myTimer = new Timer(DELAY, setTimerUp());
        myBoard = new Board();
        myGamePanel = new GamePanel(myBoard);
        myScorePanel = new ScorePanel();
        myNextPanel = new NextPanel();
        myInfoPanel = new InfoPanel(myTimer);
        myMenuBar = new TetrisMenuBar(myBoard, myTimer, myJFrame, 
                                      myGamePanel, this, myInfoPanel, myScorePanel);
    }
    
    /** Creates ActinonListener for Timer. 
     * @return Timer ActionListener.
     */
    private ActionListener setTimerUp() {
        final ActionListener taskPerformer = new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theE) {
                myBoard.down();
            }
        };
        
        return taskPerformer;
    }
    
    /** Runs the GUI. */
    public void start() {
        
        //Game Panel
        myGamePanel.setFocusable(true);
        myBoard.addObserver(myGamePanel);
        myBoard.addObserver(this);
        
        //Panels for GUI
        final JPanel centerPanel = new JPanel();
        final JPanel eastPanel = new JPanel();
        eastPanel.setBackground(BG_COLOR);
        
        final JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
        
        //----Display panels 
        
        //Score of the game
        myBoard.addObserver(myScorePanel);
        
        //Next piece panel
        myBoard.addObserver(myNextPanel);
        
        //Info panel
        myBoard.addObserver(myInfoPanel);
        
        //Box Panel Layout
        boxPanel.add(myScorePanel);
        boxPanel.add(Box.createVerticalStrut(SPACE));
        boxPanel.add(myNextPanel);
        boxPanel.add(Box.createVerticalStrut(SPACE));
        boxPanel.add(myInfoPanel);
        
        //adding the panels. 
        centerPanel.add(myGamePanel, BorderLayout.WEST);
        eastPanel.add(boxPanel, BorderLayout.CENTER);
        
        //JFrame set up
        myJFrame.setJMenuBar(myMenuBar);
        
        myJFrame.add(myGamePanel, BorderLayout.WEST);
        myJFrame.add(eastPanel, BorderLayout.EAST);
        
        myJFrame.setIconImage(myIcon.getImage());
        myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myJFrame.setBackground(Color.GRAY);
        myJFrame.setResizable(false);
        myJFrame.pack();
        
        //Center in screen 
        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        myJFrame.setLocation((int) dim.getWidth() / 2 - myJFrame.getWidth() / 2,
                             (int) dim.getHeight() / 2 - myJFrame.getHeight() / 2);
        myJFrame.setVisible(true);
    }
    
    /** Set the dimensions of the board. 
     * @param theWidht Width of the board. 
     * @param theHeight Height of the board. 
     */
    public void setBoard(final int theWidht, final int theHeight) {
        
        //Assign new board
        myBoard = new Board(theWidht, theHeight);
        myBoard.newGame();
        myGamePanel.setBoard(myBoard);
        myMenuBar.setBoard(myBoard);
        
        //Add observers back 
        myBoard.addObserver(myNextPanel);
        myBoard.addObserver(myScorePanel);
        myBoard.addObserver(myGamePanel);
        myBoard.addObserver(myInfoPanel);

    }
    
    @Override
    public void update(final Observable theO, final Object theArg) {
        
        if (theArg instanceof Boolean) {
            
            JOptionPane.showMessageDialog(myJFrame, 
                                          "GAME OVER", "Game Over", JOptionPane.PLAIN_MESSAGE,
                                          new ImageIcon("images//tetris_logo_over.png"));
        }
    }

}
