package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Board;

/** Creates MenuBar for GUI.
 * @author Brandon Blaschke 
 * @version 12/2/16
 *
 */
public class TetrisMenuBar extends JMenuBar {

    /**Generated serial version UID. */
    private static final long serialVersionUID = -8436264661459312329L;
    
    /** Major tick spacing for slider. */
    private static final int MAJOR_TICKS = 5; 
    
    /** Reference to the game board. */
    private Board myBoard;
    
    /** Reference to the game timer. */
    private final Timer myTimer; 
    
    /** Reference to the JFrame. */
    private final JFrame myJFrame; 
    
    /** Reference to the game panel. */
    private final GamePanel myGame;
    
    /** Reference to GUI. */
    private final TetrisGUI myGUI;
    
    /** Info panel. */
    private final InfoPanel myInfoPanel;
    
    /** Score panel. */
    private final ScorePanel myScorePanel;   
    
    /** Creates a TetrisMenuBar. 
     * @param theBoard Game Board being used. 
     * @param theTimer Timer being used by the game. 
     * @param theFrame JFrame reference. 
     * @param theGame GamePanel being used in the GUI. 
     * @param theGUI GUI reference. 
     * @param theInfo Information panel for game. 
     * @param theScore Score panel for game. 
     * */
    public TetrisMenuBar(final Board theBoard, final Timer theTimer,
                         final JFrame theFrame, final GamePanel theGame,
                         final TetrisGUI theGUI, final InfoPanel theInfo,
                         final ScorePanel theScore) {
        
        super();
        myBoard = theBoard;
        myTimer = theTimer; 
        myJFrame = theFrame;
        myGame = theGame;
        myGUI = theGUI;
        myInfoPanel = theInfo;
        myScorePanel = theScore;
        createMenuBar();
    }
    
    /** Helper method to create menu bar. */
    private void createMenuBar() {
        
        //Music player
        final TetrisMusic music = new TetrisMusic("sounds//tetris_music.mp3");
        
        //-----Control Menu
        final JMenu controlMenu = new JMenu("Control Game");
        controlMenu.setMnemonic(KeyEvent.VK_C);
        add(controlMenu);
        
        //Resume timer item
        final JMenuItem resumeItem = createResumeItem(music);
        controlMenu.add(resumeItem);
        
        //Pause Timer item
        final JMenuItem pauseItem = createPauseItem(music);
        controlMenu.add(pauseItem);
        
        //-----Settings 
        final JMenu gameMenu = new JMenu("Settings");
        gameMenu.setMnemonic(KeyEvent.VK_S);
        add(gameMenu);
        
        //Check box for grid
        final JCheckBoxMenuItem gridCheckItem = createGridCheck(myGame);
        gridCheckItem.setEnabled(false);
        
        //New Game item
        final JMenuItem newGameItem = createNewGameItem(pauseItem, resumeItem, 
                                                        gridCheckItem, music);
        gameMenu.add(newGameItem);
        
        //End Game item 
        final JMenuItem endGameItem = createEndGameItem(pauseItem, resumeItem, music);
        gameMenu.add(endGameItem);
        
        gameMenu.addSeparator();
        
        //Mute item
        final JCheckBoxMenuItem muteCheckBox = craeteMuteCheck(music);
        gameMenu.add(gridCheckItem);
        gameMenu.add(muteCheckBox);
        gameMenu.addSeparator();
        
        //Size of game
        final JMenuItem gameSizeMenu = createSliderItem();
        gameSizeMenu.setMnemonic(KeyEvent.VK_R);
        gameMenu.add(gameSizeMenu);
        
        //----Help Menu
        final JMenu helpMenu = new JMenu("Help"); 
        helpMenu.setMnemonic(KeyEvent.VK_H);
        add(helpMenu);
        
        //Controls item that shows JOptionsPane
        final JMenuItem controlsItem = createControlsItem();
        helpMenu.add(controlsItem);
        
        //Scoring Item 
        final JMenuItem scoringItem = createScoreItem();
        helpMenu.add(scoringItem);
        
        //Credits 
        final JMenuItem credits = createCredits("Music Credits");
        helpMenu.add(credits);
    }
    
    /** Creates new game JMenuItem. 
     * @return The new game menu item. 
     * @param thePause JMenuItem that pauses the game.
     * @param theResume JMenuItem that resumes the game.
     * @param theCheckBox JCheckBoxMenuItem that enables the grid.
     * @param theMusic Music player.   
     */
    private JMenuItem createNewGameItem(final JMenuItem thePause, final JMenuItem theResume,
                                        final JCheckBoxMenuItem theCheckBox, 
                                        final TetrisMusic theMusic) {
        
        final JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.setMnemonic(KeyEvent.VK_N);
        newGameItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theE) {
                
                myBoard.newGame();
                theMusic.playMusic();
                myTimer.start();
                myGame.setKeys(true);
                myInfoPanel.restInformation();
                myScorePanel.resetScore();
                thePause.setEnabled(true);
                theResume.setEnabled(true);
                theCheckBox.setEnabled(true);
                
            }
        });
        
        return newGameItem;
    }
    
    /** Creates end game JMenuItem. 
     * @return The end game menu item. 
     * @param thePause JMenuItem that pauses the game.
     * @param theResume JMenuItem that resumes the game.
     * @param theMusic Music for the game. 
     */
    private JMenuItem createEndGameItem(final JMenuItem thePause, 
                                        final JMenuItem theResume, 
                                        final TetrisMusic theMusic) {
        
        final JMenuItem newGameItem = new JMenuItem("End Game");
        newGameItem.setMnemonic(KeyEvent.VK_E);
        newGameItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theE) {
                
                myTimer.stop();
                myGame.setKeys(false);
                myGUI.update(myBoard, false);
                theMusic.stopMusic();
                thePause.setEnabled(false);
                theResume.setEnabled(false);
            }
        });
        
        return newGameItem;
    }
    
    /** Creates resume game JMenuItem.
     * @param theMusic Music for the game. 
     * @return The resume game menu item. 
     */
    private JMenuItem createResumeItem(final TetrisMusic theMusic) {
        
        final JMenuItem resumeItem = new JMenuItem("Resume");
        resumeItem.setMnemonic(KeyEvent.VK_R);
        resumeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
                                                         ActionEvent.CTRL_MASK));
        resumeItem.setEnabled(false);
        resumeItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theE) {
                
                    myTimer.start();
                    theMusic.playMusic();
                    myGame.setKeys(true);
            }
        });
        
        return resumeItem;
    }
    
    /** Creates pause game JMenuItem. 
     * @param theMusic Music for the game. 
     * @return The pause game menu item.
     */
    private JMenuItem createPauseItem(final TetrisMusic theMusic) {
        
        final JMenuItem pauseItem = new JMenuItem("Pause");
        pauseItem.setMnemonic(KeyEvent.VK_P);
        pauseItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        pauseItem.setEnabled(false);
        pauseItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theE) {
                
                myTimer.stop();
                theMusic.stopMusic();
                myGame.setKeys(false);
            }
        });
        
        return pauseItem;
    }
   
    /** Creates controls of game JMenuItem. 
     * @return The controls of game menu item. 
     */
    private JMenuItem createControlsItem() {
        
        final JMenuItem controlsItem = new JMenuItem("Control");
        controlsItem.setMnemonic(KeyEvent.VK_C);
        controlsItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theE) {
                
                JOptionPane.showMessageDialog(myJFrame, "Up-Arrow: Rotate piece \n"
                                + "Down-Arrow: Drop piece down one box\n"
                                + "Right-Arrow: Move piece one to the right\n"
                                + "Left-Arrow: Move piece on to the left\n"
                                + "Spacebar: Drop piece to the bottom of board.", "Controls",
                                JOptionPane.PLAIN_MESSAGE);
            }
        });
        
        return controlsItem; 
    }
    
    /** Creates JMenuItem of a scoring menu item. 
     * @return The scoring of the game menu item. 
     */
    private JMenuItem createScoreItem() {
        
        final JMenuItem scoringItem = new JMenuItem("Points");
        scoringItem.setMnemonic(KeyEvent.VK_P);
        scoringItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theE) {
                
                JOptionPane.showMessageDialog(myJFrame, "Score increases by 10 for each "
                                + "line cleared. Speed increases each level as well.\n", 
                                "Scoring", JOptionPane.PLAIN_MESSAGE);
            }
        });
        return scoringItem;
    }
    
    /** Create a JCheckBoxMenuItem for the grid. 
     * @param theGamePanel Reference to the game panel.
     * @return the JCheckBoxMenuItem. 
     */
    private JCheckBoxMenuItem createGridCheck(final GamePanel theGamePanel) {
        
        final JCheckBoxMenuItem checkBox = new JCheckBoxMenuItem("Grid");
        checkBox.setMnemonic(KeyEvent.VK_G);
        checkBox.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        checkBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theE) {
                theGamePanel.setGrid(checkBox.getState());
            }
            
        });
        
        return checkBox;
    }
    
    /** Create a JOptionPane for resizing the game. 
     * @return The slider pane.
     * */
    private JOptionPane createSliderPane() {
        
        final JOptionPane optionPane = new JOptionPane();
        final JSlider slider = new JSlider(SwingConstants.HORIZONTAL, 10, 30, 10);
        
        //Setting up the JSlider
        slider.setMajorTickSpacing(MAJOR_TICKS);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(final ChangeEvent theArg) {
                
                final JSlider source = (JSlider) theArg.getSource();
                
                if (!source.getValueIsAdjusting()) {
                    optionPane.setInputValue(source.getValue());
                }
            }
        });
        
        //Setting up the optionPane
        JOptionPane.showConfirmDialog(optionPane, slider, 
                                      "Size Ratio 2:1", JOptionPane.OK_CANCEL_OPTION);
        
        myGUI.setBoard(slider.getValue(), slider.getValue() * 2);
        myInfoPanel.restInformation();
        myScorePanel.resetScore();
        return optionPane; 
    }
    
    /** Creates slider option for menu. 
     * @return The slider. 
     */
    private JMenuItem createSliderItem() {
        
        final JMenuItem gameSizeMenu = new JMenuItem("Resize Game");
        gameSizeMenu.setMnemonic(KeyEvent.VK_R);
        gameSizeMenu.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theArg) {
                createSliderPane();
            }
            
        });
        
        return gameSizeMenu;
    }
    
    /** Creates credit JMenuItem to display credits.
     * @param theTitle Title of the JMenuItem.  
     * @return The JMenuItem with credits.
     */
    private JMenuItem createCredits(final String theTitle) {
        
        final JMenuItem credits = new JMenuItem(theTitle);
        credits.setMnemonic(KeyEvent.VK_M);
        credits.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theE) {
                
                JOptionPane.showMessageDialog(myJFrame, "Music URL "
                                + "\n\"http://downloads.khinsider.com/game-soundtracks/"
                                + "album/tetris-gameboy-rip-\""
                                + "\n tetris-gameboy-02.mp3", "Credits", 
                                JOptionPane.PLAIN_MESSAGE);
            }
        });
        
        return credits; 
    }
    
    /** Creates a JCheckBoxMenuItem for the mute action. 
     * @param theMusic TetrisMusic to be mute or played.
     * @return The mute check box item.  
     * */
    private JCheckBoxMenuItem craeteMuteCheck(final TetrisMusic theMusic) {
        
        final JCheckBoxMenuItem muteBox = new JCheckBoxMenuItem("Mute");
        muteBox.setMnemonic(KeyEvent.VK_M);
        muteBox.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
        muteBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theE) {
                
                if (muteBox.getState()) {
                    
                    theMusic.setMute(true);
                } else {
                    
                    theMusic.setMute(false);
                }
            }
            
        });
        
        return muteBox;
    }
    
    /** Sets the Board to a new Board object. 
     * @param theBoard The new Board. 
     */
    public void setBoard(final Board theBoard) {
        myBoard = theBoard;
    }

}
