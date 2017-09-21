package view;

import java.awt.Color;

import model.Block;

/** Class determines if the color a piece, based on the block. 
 * 
 * @author Brandon Blaschke
 *@version 12/2/16
 */
public final class DetermineColor {
    
    /** Custom orange color. */
    private static final Color ORANGE_COLOR = new Color(205, 132, 0);
    
    /** Custom cyan color. */
    private static final Color CYAN_COLOR = new Color(0, 178, 178);
    
    /** Utility class so does not to be created. */
    private DetermineColor() {
        
    }
    
    /** Finds color of a piece. 
     * @param theBlock Current Tetris piece.
     * @return Color of the piece. 
     */
    public static Color findColor(final Block theBlock) {
        
        //Default color variable. 
        Color blockColor = Color.BLACK;
        
        switch (theBlock) {
            
            case EMPTY:
                break;
                
            case I: 
                blockColor = CYAN_COLOR;
                break;
                
            case L:
                blockColor = ORANGE_COLOR;
                break;
            
            case J: 
                blockColor = Color.BLUE;
                break;
                
            case O:
                blockColor = Color.YELLOW;
                break;
            
            case T:
                blockColor = Color.MAGENTA;
                break;
                
            case Z: 
                blockColor = Color.RED;
                break;
                
            case S: 
                blockColor = Color.GREEN;
                break;
                
            default:
                break;

        }
        
        return blockColor; 
        
    }
    
    /** Finds the block type of a char. 
     * @param theChar Character being used by a piece
     * @return Block type corresponding to the char. 
     */
    public static Block findBlock(final char theChar) {
        
        //Default block
        Block block = Block.EMPTY;
        
        switch (theChar) {
            
            case 'I': 
                block = Block.I;
                break;
                
            case 'L':
                block = Block.L;
                break;
            
            case 'J': 
                block = Block.J;
                break;
                
            case 'O':
                block = Block.O;
                break;
            
            case 'T':
                block = Block.T;
                break;
                
            case 'Z': 
                block = Block.Z;
                break;
                
            case 'S': 
                block = Block.S;
                break;
                
            default:
                break;

        }
        
        return block;
    }
}
