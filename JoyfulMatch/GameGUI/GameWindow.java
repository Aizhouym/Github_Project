package JoyfulMatch.GameGUI;

import javax.swing.*;

import JoyfulMatch.MusicPlayer.BackgroundMusic;
import JoyfulMatch.MusicPlayer.MusicThread;
import java.awt.*;


public class GameWindow extends JFrame {
    private ImageIcon icon;
    GamePanel gamePanel;
    private JMenuBar menu;
    private JMenu gameMenu, rankMenu, musicMenu;
    private JMenuItem startItem, exitItem, music1, music2, music3;
    private String music1String, music2String, music3String;
    private BackgroundMusic musicPlayer;
    MusicThread musicThread;

    String[][] gameGrid;


    private void  initalizeComponents(){
        icon = new ImageIcon("E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/icon2.png");
        gamePanel = new GamePanel();
        menu = new JMenuBar();
        gameMenu = new JMenu("*游戏*");
        rankMenu = new JMenu("*排名*");
        musicMenu = new JMenu("*音乐*");
        startItem = new JMenuItem(" <开始游戏> ");
        exitItem = new JMenuItem(" <退出游戏> ");
        music1 = new JMenuItem("<1.Shape Of You>");
        music2 = new JMenuItem("<2.Closer>");
        music3 = new JMenuItem("<3.Die for you>");

        music1String = "E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/Shape of You.mp3";
        music2String = "E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/Closer.mp3";
        music3String = "E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/Die for You.mp3";
        

        musicPlayer = new BackgroundMusic();
        musicThread = new MusicThread();
        musicThread.setBackgroundMusic(musicPlayer);
    }
    
    private void createGUI(){
        SwingUtilities.invokeLater(() -> {
            //启动背景音乐线程
            musicThread.start();
            

            // 设置游戏菜单的字体
            Font menuFont = new Font("宋体", Font.BOLD, 15);
            Font menuFont2 = new Font("Arial", Font.BOLD, 15);
            gameMenu.setFont(menuFont);
            rankMenu.setFont(menuFont);
            musicMenu.setFont(menuFont);
            startItem.setFont(menuFont);
            startItem.addActionListener(e -> {

            }); //计时器开始计时

            exitItem.setFont(menuFont);
            exitItem.addActionListener(e -> {
                System.exit(0); //设置退出
            });
            //音乐切换
            music1.setFont(menuFont2);
            music2.setFont(menuFont2);
            music3.setFont(menuFont2);
            music1.addActionListener(e -> {
                musicPlayer.stop();
                MusicThread musicThread1 = new MusicThread();
                musicThread1.setBackgroundMusic(musicPlayer);
                musicThread1.setMusic(music1String);
                musicThread1.start();
            });
            
            music2.addActionListener(e -> {
                musicPlayer.stop();
                MusicThread musicThread2 = new MusicThread();
                musicThread2.setBackgroundMusic(musicPlayer);
                musicThread2.setMusic(music2String);
                musicThread2.start();
            });
            
            music3.addActionListener(e -> {
                musicPlayer.stop();
                MusicThread musicThread3 = new MusicThread();
                musicThread3.setBackgroundMusic(musicPlayer);
                musicThread3.setMusic(music3String);
                musicThread3.start();
            });

            gameMenu.add(startItem);
            gameMenu.add(exitItem);
            musicMenu.add(music1);
            musicMenu.add(music2);
            musicMenu.add(music3);
            menu.add(gameMenu);
            menu.add(rankMenu);
            menu.add(musicMenu);
            setJMenuBar(menu);
            
            // // 将图像绘制到面板中
            // drawGameGrid(gameGrid);
            
            // 将游戏面板添加到窗口中
            add(gamePanel);

            // 设置窗口属性
            setTitle("喵了个喵");
            setIconImage(icon.getImage());
            setSize(1250, 1000);
            setVisible(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setBounds(200, 0, 1250, 1000);
        });        
    }    

    public GameWindow() {
        // 初始化图像资源
        initalizeComponents();
        createGUI();
        
    }

    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow();
        gameWindow.setVisible(true);
    }

}
