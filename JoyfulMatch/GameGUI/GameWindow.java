package JoyfulMatch.GameGUI;

import javax.swing.*;

import JoyfulMatch.MusicPlayer.BackgroundMusic;
import JoyfulMatch.MusicPlayer.MusicThread;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Random;

public class GameWindow extends JFrame {
    private HashMap<String, ImageIcon> imageMap;
    private ImageIcon image1, image2, image3, image4, image5, image6, image7, image8, image9; 
    private JPanel gamePanel;
    private JMenuBar menu;
    private JMenu gameMenu, rankMenu, musicMenu;
    private JMenuItem startItem, exitItem, music1, music2, music3;
    private String music1String, music2String, music3String;
    private BackgroundMusic musicPlayer;
    MusicThread musicThread;
    String[][] gameGrid;

    private void  initalizeComponents(){

        image1 = new ImageIcon("E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/cat1.png");
        image2 = new ImageIcon("E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/cat2.png");
        image3 = new ImageIcon("E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/cat3.png");
        image4 = new ImageIcon("E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/cat4.png");
        image5 = new ImageIcon("E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/cat5.png");
        image6 = new ImageIcon("E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/cat6.png");
        image7 = new ImageIcon("E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/cat7.png");
        image8 = new ImageIcon("E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/cat8.png");
        image9 = new ImageIcon("E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/cat9.png");
        imageMap = new HashMap<>();
        imageMap.put("image1", image1);
        imageMap.put("image2", image2);
        imageMap.put("image3", image3);
        imageMap.put("image4", image4);
        imageMap.put("image5", image5);
        imageMap.put("image6", image6);
        imageMap.put("image7", image7);
        imageMap.put("image8", image8);
        imageMap.put("image9", image9);
        gamePanel = new JPanel(new GridLayout(3, 3));
        gameGrid = generateGameGrid();
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

    private String[][] generateGameGrid() {
        String[] imageArray = {"image1", "image2", "image3", "image4", "image5", "image6", "image7", "image8", "image9"};
        String[][] gameGrid = new String[3][3];
        Random random = new Random();
    
        for (int row = 0; row < gameGrid.length; row++) {
            for (int col = 0; col < gameGrid[row].length; col++) {
                int randomIndex = random.nextInt(imageArray.length);
                gameGrid[row][col] = imageArray[randomIndex];
            }
        }
        
        return gameGrid;
    }
    

    private void drawGameGrid(String[][] gameGrid) {
        // 遍历游戏面板的行和列
        for (int row = 0; row < gameGrid.length; row++) {
            for (int col = 0; col < gameGrid[row].length; col++) {
                String imageName = gameGrid[row][col];
                ImageIcon image = imageMap.get(imageName);

                // 创建一个标签，将图像设置为标签的图标

                JLabel label = new JLabel(image);
                label.setName("label_" + row + "_" + col);
                // System.out.println(label.getName());

                // 设置标签的尺寸
                // label.setPreferredSize(new Dimension(50, 50));
                label.addMouseListener(new MouseAdapter() {
                    private JLabel selectedLabel;
    
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JLabel clickedLabel = (JLabel) e.getSource();
    
                        // 如果没有选中的图像，则将点击的图像标记为选中
                        if (selectedLabel == null) {
                            selectedLabel = clickedLabel;
                            selectedLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                        } else {
                            // 获取选中图像和点击图像的行列索引
                            Point selectedPoint = getGridPosition(selectedLabel);
                            Point clickedPoint = getGridPosition(clickedLabel);
    
                            // 如果点击的图像与选中的图像相邻，则执行位置交换
                            if (isAdjacent(selectedPoint, clickedPoint)) {
                                swapImages(selectedPoint, clickedPoint);
                                // 更新交换后的图像
                                selectedLabel.setIcon(imageMap.get(gameGrid[selectedPoint.x][selectedPoint.y]));
                                clickedLabel.setIcon(imageMap.get(gameGrid[clickedPoint.x][clickedPoint.y]));
                            }
    
                            // 重置选中的图像
                            selectedLabel.setBorder(null);
                            selectedLabel = null;
                        }
                    }
                });
                
                // 将标签添加到游戏面板中的对应位置
                gamePanel.add(label);
            }
        }
    }



    private Point getGridPosition(JLabel label) {
        String[] nameParts = label.getName().split("_");
        int row = Integer.parseInt(nameParts[1]);
        int col = Integer.parseInt(nameParts[2]);

        return new Point(row, col);
    }
    
    private boolean isAdjacent(Point point1, Point point2) {
        // 检查行相邻或列相邻
        boolean flag =  (Math.abs(point1.x - point2.x) == 1 && point1.y == point2.y)
                || (point1.x == point2.x && Math.abs(point1.y - point2.y) == 1);
        
            
        return flag;
    }
    
    private void swapImages(Point point1, Point point2) {
        String temp = gameGrid[point1.x][point1.y];
        gameGrid[point1.x][point1.y] = gameGrid[point2.x][point2.y];
        gameGrid[point2.x][point2.y] = temp;

            
        JLabel label1 = (JLabel) gamePanel.getComponent(point1.y + point1.x * gameGrid[point1.x].length);
        JLabel label2 = (JLabel) gamePanel.getComponent(point2.y + point2.x * gameGrid[point2.x].length);
        
        String[] nameParts1 = label1.getName().split("_");
        String[] nameParts2 = label2.getName().split("_");
        label1.setName("label_" + nameParts2[1] + "_" + nameParts2[2]);
        label2.setName("label_" + nameParts1[1] + "_" + nameParts1[2]);

        SwingUtilities.invokeLater(() -> {
            gamePanel.revalidate();
            gamePanel.repaint();
        });
        
    }
    
    private void createGUI(){
        SwingUtilities.invokeLater(() -> {
            // 设置窗口属性
            setTitle("喵了个喵");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setBounds(450, 250, 600, 600);
            setSize(600, 600);
            setVisible(true);
            // 设置游戏菜单的字体
            Font menuFont = new Font("宋体", Font.BOLD, 15);
            Font menuFont2 = new Font("Arial", Font.BOLD, 15);

            gameMenu.setFont(menuFont);
            rankMenu.setFont(menuFont);
            musicMenu.setFont(menuFont);
            startItem.setFont(menuFont);
            exitItem.setFont(menuFont);
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
            
            // 将图像绘制到面板中
            drawGameGrid(gameGrid);
            
            // 将游戏面板添加到窗口中
            add(gamePanel);
            
            //启动背景音乐线程
            musicThread.start();
        });        
    }    

    public GameWindow() {
        // 初始化图像资源
        initalizeComponents();
        createGUI();
        
    }

}
