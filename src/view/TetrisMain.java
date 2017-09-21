/* 
 * TCSS 305 – Autumn 2016  
 * Assignment 6 – Tetris
 */
package view;

import java.awt.EventQueue;

/** Class runs the GUI to play the game. 
 * 
 * @author rogka_000
 *@version 12/2/16
 */
public final class TetrisMain {
    
    /** Creates class to run GUI. */
    private TetrisMain() {
        throw new IllegalStateException();
    }

    /**
     * Running the GUI.
     * 
     * @param theArgs Command prompt strings (unused in program).
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new TetrisGUI().start();
            }
        });
    }

}
