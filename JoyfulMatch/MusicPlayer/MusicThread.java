package JoyfulMatch.MusicPlayer;

public class MusicThread extends Thread{
    //继承线程，如果只是实现runnbale接口，在创建新的对象时仍会占据主线程。
    
    private String  defaultMusic = "E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/Shape of You.mp3";
    private BackgroundMusic backgroundMusic;

    public void setBackgroundMusic(BackgroundMusic musicPlayer){
        this.backgroundMusic = musicPlayer;
    }

    public void setMusic(String  musicName){
        this.defaultMusic = musicName;
    }

    @Override
    public void run() {
        backgroundMusic.play(defaultMusic);
    }

}
