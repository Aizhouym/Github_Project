package JoyfulMatch.MusicPlayer;

import java.io.File;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

//使用javaFx进行音乐播放
public class Player extends Application {
    String musicFile;

    public void setMusicFile(String musicPath){
        this.musicFile = musicPath;
    }

    private MediaPlayer mediaPlayer;


    @Override
    public void start(Stage primaryStage) {
        
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        // 其他 GUI 初始化和逻辑代码...
    }

    @Override
    public void stop() {
        mediaPlayer.stop();
    }

    public void launchPlayer() {
        launch();
    }
}
