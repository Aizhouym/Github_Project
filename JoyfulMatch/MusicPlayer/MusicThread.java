package JoyfulMatch.MusicPlayer;

public class MusicThread extends Thread{
    //继承线程，如果只是实现runnbale接口，在创建新的对象时仍会占据主线程。
    
    private String  defaultMusic = "E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/music/eurpoean/Die For You.mp3";
    private BackgroundMusic backgroundMusic;

    public void setBackgroundMusic(BackgroundMusic musicPlayer){
        this.backgroundMusic = musicPlayer;
    }

    public void setMusic(String  musicName){
        this.defaultMusic = musicName;
    }

    @Override
    public void run() {
        // backgroundMusic.play(defaultMusic);
        try {
            while (true) {
                backgroundMusic.play(defaultMusic);
                // 等待音乐播放结束
                synchronized (backgroundMusic) {
                    backgroundMusic.wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }//循环播放音乐
    }


}
