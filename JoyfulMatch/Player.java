package JoyfulMatch;

import java.io.File;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Player extends Application {
    String musicFile = "E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/Die For You.mp3";

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

    public static void main(String[] args) {
        launch(args);
    }

    public void launchPlayer() {
        launch();
    }
}
