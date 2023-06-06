package JoyfulMatch.GameGUI;

import javax.swing.*;

import JoyfulMatch.MusicPlayer.BackgroundMusic;
import JoyfulMatch.MusicPlayer.MusicThread;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import JoyfulMatch.common.RankData;


public class GameWindow extends JFrame {
    private ImageIcon icon;
    GamePanel gamePanel;
    private JMenuBar menu;
    private JMenu gameMenu, rankMenu, musicMenu, chineseMusicMenu, japaneseKoreanMusicMenu, westernMusicMenu, toolMenu;
    private JMenuItem pasueItem, exitItem, rank_easyItem, rank_hardItem, music1, music2, music3, music4, music5, music6, music7, shuffItem, backItem;
    private String music1String, music2String, music3String,music4String,music5String,music6String,music7String;
    private BackgroundMusic musicPlayer;
    ArrayList<RankData> easy_rankingDatas, hard_rankingDatas;
    MusicThread musicThread;
    String[][] gameGrid;
    public static String name;
    private boolean isPaused = false; // 用于跟踪时间是否暂停
    public static boolean isHard = false; //默认为easy

    private void  initalizeComponents(){
        icon = new ImageIcon("E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/icon2.png");

        gamePanel = new GamePanel();
        menu = new JMenuBar();
        gameMenu = new JMenu("*游戏*");
        rankMenu = new JMenu("*排名*");
        musicMenu = new JMenu("*音乐*");
        toolMenu = new JMenu("*道具*");
        pasueItem = new JMenuItem(" <暂停> ");
        exitItem = new JMenuItem(" <结束> ");
        rank_easyItem = new JMenuItem("<easy_mode ranking>");
        rank_hardItem = new JMenuItem("<hard_mode ranking>");
        chineseMusicMenu = new JMenu("*华语*");
        japaneseKoreanMusicMenu = new JMenu("*日韩*");
        westernMusicMenu = new JMenu("*欧美*");

        music1 = new JMenuItem("<1.Shape Of You>");
        music2 = new JMenuItem("<2.Closer>");
        music3 = new JMenuItem("<3.Die for you>");
        music4 = new JMenuItem("<4.最后一页>");
        music5 = new JMenuItem("<5.给你呀>");
        music6 = new JMenuItem("<6.我知道>");
        music7 = new JMenuItem("<7.谎言>");

        shuffItem = new JMenuItem("<1.打乱> ");
        backItem = new JMenuItem("<2.反悔> ");


        music1String = "E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/music/eurpoean/Shape of You.mp3";
        music2String = "E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/music/eurpoean/Closer.mp3";
        music3String = "E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/music/eurpoean/Die for You.mp3";
        music4String = "E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/music/china/江语晨 - 最后一页.mp3";
        music5String = "E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/music/china/蒋小呢 - 给你呀.mp3";
        music6String = "E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/music/china/By2 - 我知道.mp3";
        music7String = "E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/music/korean/BIGBANG - 谎言.mp3";
        

        musicPlayer = new BackgroundMusic();
        musicThread = new MusicThread();
        musicThread.setBackgroundMusic(musicPlayer);

        easy_rankingDatas = new ArrayList<>();
        hard_rankingDatas = new ArrayList<>();
    }
    
    private void createGUI(){
        SwingUtilities.invokeLater(() -> {
            //启动背景音乐线程
            musicThread.start();
            

            // 设置游戏菜单的字体
            Font menuFont = new Font("宋体", Font.BOLD, 16);
            Font menuFont2 = new Font("Arial", Font.BOLD, 15);
            gameMenu.setFont(menuFont);
            rankMenu.setFont(menuFont);
            musicMenu.setFont(menuFont);
            chineseMusicMenu.setFont(menuFont);
            japaneseKoreanMusicMenu.setFont(menuFont);
            westernMusicMenu.setFont(menuFont);
            toolMenu.setFont(menuFont);
           
            pasueItem.setFont(menuFont);
            pasueItem.addActionListener(e -> {
                if (isPaused) {
                    // 恢复时间
                    gamePanel.resumeTimer();
                    isPaused = false;
                
                } else {
                    // 暂停时间
                    gamePanel.pauseTimer();
                    isPaused = true;
                }
            }); //计时器开始计时

            exitItem.setFont(menuFont);
            exitItem.addActionListener(e -> {
                if(!isHard){
                    System.exit(0); //设置退出
                }else{
                    gamePanel.gameOver();
                    System.exit(0);
                }
                
            });

            //rank设置
            rank_easyItem.setFont(menuFont2);
            rank_easyItem.addActionListener(e -> {
                easy_rankingDatas = gamePanel.rankingData;
                showRankingDialog(easy_rankingDatas);
            });

            rank_hardItem.setFont(menuFont2);
            rank_hardItem.addActionListener(e -> {

                hard_rankingDatas = gamePanel.fetchRankingData();
                showRankingDialog(hard_rankingDatas);
            });
            //音乐切换
            music1.setFont(menuFont2);
            music2.setFont(menuFont2);
            music3.setFont(menuFont2);
            music4.setFont(menuFont);
            music5.setFont(menuFont);
            music6.setFont(menuFont);
            music7.setFont(menuFont);

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

            music4.addActionListener(e -> {
                musicPlayer.stop();
                MusicThread musicThread4 = new MusicThread();
                musicThread4.setBackgroundMusic(musicPlayer);
                musicThread4.setMusic(music4String);
                musicThread4.start();
            });

            music5.addActionListener(e -> {
                musicPlayer.stop();
                MusicThread musicThread5 = new MusicThread();
                musicThread5.setBackgroundMusic(musicPlayer);
                musicThread5.setMusic(music5String);
                musicThread5.start();
            });

            music6.addActionListener(e -> {
                musicPlayer.stop();
                MusicThread musicThread6 = new MusicThread();
                musicThread6.setBackgroundMusic(musicPlayer);
                musicThread6.setMusic(music6String);
                musicThread6.start();
            });

            music7.addActionListener(e -> {
                musicPlayer.stop();
                MusicThread musicThread7 = new MusicThread();
                musicThread7.setBackgroundMusic(musicPlayer);
                musicThread7.setMusic(music7String);
                musicThread7.start();
            });

            //道具功能定义
            shuffItem.setFont(menuFont);
            shuffItem.addActionListener(e ->{
                gamePanel.shuffItem();
            });

            backItem.setFont(menuFont);
            backItem.addActionListener(e -> {
                gamePanel.backItem();

            });
            



            gameMenu.add(pasueItem);
            gameMenu.add(exitItem);
            rankMenu.add(rank_easyItem);
            rankMenu.add(rank_hardItem);
            chineseMusicMenu.add(music4);
            chineseMusicMenu.add(music5);
            chineseMusicMenu.add(music6);
            japaneseKoreanMusicMenu.add(music7);
            westernMusicMenu.add(music1);
            westernMusicMenu.add(music2);
            westernMusicMenu.add(music3);
            musicMenu.add(westernMusicMenu);
            musicMenu.add(chineseMusicMenu);
            musicMenu.add(japaneseKoreanMusicMenu);
            toolMenu.add(shuffItem);
            toolMenu.add(backItem);
            menu.add(gameMenu);
            menu.add(rankMenu);
            menu.add(musicMenu);
            menu.add(toolMenu);
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

    private void showRankingDialog(ArrayList<RankData> rankingData) {
        StringBuilder message = new StringBuilder();
        int rank = 1;
        Set<String> addedNames = new HashSet<>();
    
        // 遍历rankingData
        for (RankData rankData : rankingData) {
            if (!addedNames.contains(rankData.getName())) {
                message.append(rank).append(". ").append(rankData.getName()).append(": ").append(rankData.getScore()).append("\n");
                addedNames.add(rankData.getName());
                rank++;
            }
        }
    
        JOptionPane.showMessageDialog(this, message.toString(), "Ranking", JOptionPane.INFORMATION_MESSAGE);
    }


    public GameWindow(String name, int isHard) {
        // 初始化图像资源
        GameWindow.name = name;
        
        if(isHard == 1){
            GameWindow.isHard = true;
        }

        System.out.println(GameWindow.name);
        initalizeComponents();
        createGUI();

    }


}
