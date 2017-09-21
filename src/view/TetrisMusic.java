package view;

import java.nio.file.Paths;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/** Class will be able to play music.
 * @author Brandon Blaschke 
 * @version 12/9/16
 */
public class TetrisMusic {
    
    /** Media player for playing music. */
    private final MediaPlayer myMediaPlayer;
    
    /** Set if muted. */
    private boolean myMute; 
    
    /** Creates music class to be played. 
     * @param theFile File location in project.
     */
    public TetrisMusic(final String theFile) {
        
        //Needed to initialize the JFX to auto create media player. 
        final JFXPanel jfk = new JFXPanel();
        final Media media = new Media(Paths.get(theFile).toUri().toString());
        myMediaPlayer = new MediaPlayer(media);
        myMute = false;
    }

    /** Plays given music. */
    public void playMusic() {
        
        //Repeats music. 
        myMediaPlayer.setOnEndOfMedia(new Runnable() {
            private final MediaPlayer myMp = myMediaPlayer;
            @Override
            public void run() {
                myMp.seek(Duration.ZERO);
            }
            
        });
        
        if (!myMute) {
            myMediaPlayer.play();
        }
    }
    
    /** Stops current music from playing. */
    public void stopMusic() {
        
        if (!myMute) {
            myMediaPlayer.stop();
        }
    }
    
    /** Set if music is muted or not. 
     * @param theMute True if muted, false if not. 
     */
    public void setMute(final boolean theMute) {
        myMute = theMute;
        
        if (theMute) {
            
            myMediaPlayer.stop();
        } else {
            
            myMediaPlayer.play();
        }
    }

}
