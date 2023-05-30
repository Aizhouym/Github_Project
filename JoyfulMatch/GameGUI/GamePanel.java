package JoyfulMatch.GameGUI;

import java.awt.*;
import java.util.Random;
import javax.swing.*;
import JoyfulMatch.Block;
import JoyfulMatch.ImageInit;

public class GamePanel extends JPanel {
    private int[][] imageMatrix;
    private Block[][] blockMatrix;
    private ImageInit imageInit;
    private Image background;
    private Font headerFont;
    private long startTime;

    private void init() {
        imageInit = new ImageInit();
        imageInit.init();

        background = new ImageIcon("E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/background2.jpg").getImage(); 
        // 随机生成图像矩阵
        Random random = new Random();
        imageMatrix = new int[7][10];
        for (int row = 0; row < imageMatrix.length; row++) {
            for (int col = 0; col < imageMatrix[0].length; col++) {
                int num = random.nextInt(9);
                imageMatrix[row][col] = num;
            }
        }

        // 设置标题字体
        headerFont = new Font("宋体", Font.BOLD, 30);

        //设置时间
        startTime = System.currentTimeMillis();
        // 启动定时器，每秒触发重绘
        Timer timer = new Timer(1000, e -> {
            repaint();
        });
        timer.start();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);

        // 计算面板的尺寸和间隙
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int gap = 3; // 间隙大小

        // 计算单个图像的尺寸
        int imageWidth = 115;
        int imageHeight = 115;

        // 计算图像矩阵在面板中的起始位置
        int matrixWidth = imageMatrix[0].length * (imageWidth + gap) - gap;
        int matrixHeight = imageMatrix.length * (imageHeight + gap) - gap;
        int startX = (panelWidth - matrixWidth) / 2;
        int startY = (panelHeight - matrixHeight) / 2;

        // 绘制蓝色矩形框
        g.setColor(Color.CYAN);
        g.fillRect(startX - gap, startY - gap, matrixWidth + 2 * gap, matrixHeight + 2 * gap);

        // 绘制图像
        for (int row = 0; row < imageMatrix.length; row++) {
            for (int col = 0; col < imageMatrix[0].length; col++) {
                int index = imageMatrix[row][col];
                Image image = ImageInit.imageList.get(index);
                int x = startX + col * (imageWidth + gap);
                int y = startY + row * (imageHeight + gap);
                g.drawImage(image, x, y, this);
            }
        }

        // 绘制标题文本
        g.setFont(headerFont);
        g.setColor(Color.RED);
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        String timeText = "时间：" + elapsedTime / 1000 + "秒";
        String scoreText = "得分: ";
        int textHeight = g.getFontMetrics().getHeight();
        g.drawString(timeText, 300, startY - textHeight);
        g.drawString(scoreText, 700, startY - textHeight);


    }

    public GamePanel() {
        init();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1250, 1000);
        
        GamePanel  gameFrame = new GamePanel();
        frame.add(gameFrame);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }
}