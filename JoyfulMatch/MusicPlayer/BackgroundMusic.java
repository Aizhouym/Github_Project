package JoyfulMatch.MusicPlayer;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class BackgroundMusic{
    
    private Player player;
    
    public void play(String musicString) {
        try {
            FileInputStream fis = new FileInputStream(musicString);
            BufferedInputStream bis = new BufferedInputStream(fis);
            
            player = new Player(bis);
            player.play();
        } catch (FileNotFoundException | JavaLayerException e) {
            e.printStackTrace();
        }
    }
    
    public void stop() {
        if (player != null) {
            player.close();
        }
    }
}


