package JoyfulMatch.GameGUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.*;
import JoyfulMatch.Block;
import JoyfulMatch.ImageInit;

public class GamePanel extends JPanel {
    private int[][] imageMatrix;
    private Block[][] blockMatrix;
    private Block selectedBlock;

    private ImageInit imageInit;
    private Image background;
    private Font headerFont;
    private long startTime;
    private int score;


    private void init() {
        imageInit = new ImageInit();
        imageInit.init();

        background = new ImageIcon("E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/background2.jpg").getImage(); 
        // 随机生成图像矩阵
        Random random = new Random();
        imageMatrix = new int[7][10];
        blockMatrix = new Block[7][10];

        for (int row = 0; row < imageMatrix.length; row++) {
            for (int col = 0; col < imageMatrix[0].length; col++) {
                int num = random.nextInt(9);
                imageMatrix[row][col] = num;
                blockMatrix[row][col] = new Block(row, col, num, this);
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

        //设置得分
        score = 0;
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
        g.setColor(Color.WHITE);
        g.fillRect(startX - gap, startY - gap, matrixWidth + 2 * gap, matrixHeight + 2 * gap);

        // 绘制图像
        for (int row = 0; row < imageMatrix.length; row++) {
            for (int col = 0; col < imageMatrix[0].length; col++) {
                // int index = imageMatrix[row][col];
                // Image image = ImageInit.imageList.get(index);
                // int x = startX + col * (imageWidth + gap);
                // int y = startY + row * (imageHeight + gap);
                // g.drawImage(image, x, y, this);
                Block block = blockMatrix[row][col];
                block.draw(g);

            }
        }

        // 绘制标题文本
        g.setFont(headerFont);
        g.setColor(Color.RED);
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        String timeText = "时间：" + elapsedTime / 1000 + "秒";
        String scoreText = "得分: "+ score;
        // int textHeight = g.getFontMetrics().getHeight();
        g.drawString(timeText, 300, startY-8);
        g.drawString(scoreText, 700, startY-8);

    }

    public void setMouseListener(){

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                // 遍历所有块，检查是否点击到了某个块
                for (int row = 0; row < blockMatrix.length; row++) {
                    for (int col = 0; col < blockMatrix[0].length; col++) {
                        Block block = blockMatrix[row][col];
                        if (block.containsPoint(mouseX, mouseY)) {
                            // 点击到了块，将其设置为选中块
                            block.setSelected(true);
                            selectedBlock = block;
                            break;
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                if (selectedBlock != null) {
                    // 遍历所有块，检查是否释放到了某个块
                    for (int row = 0; row < blockMatrix.length; row++) {
                        for (int col = 0; col < blockMatrix[0].length; col++) {
                            Block block = blockMatrix[row][col];
                            if (block != selectedBlock && block.containsPoint(mouseX, mouseY)) {
                                // 释放到了另一个块，进行交换
                                swapBlocks(selectedBlock, block);
                                break;
                            }
                        }
                    }
                    selectedBlock.setSelected(false);
                    selectedBlock = null;  // 重置选中块
                
                }
            }
        });
    }

    private void swapBlocks(Block block1, Block block2) {
        int row1 = block1.getRow();
        int col1 = block1.getCol();
        int row2 = block2.getRow();
        int col2 = block2.getCol();

        // 交换块在图像矩阵中的位置
        int temp = imageMatrix[row1][col1];
        imageMatrix[row1][col1] = imageMatrix[row2][col2];
        imageMatrix[row2][col2] = temp;

        // 交换块数组中的位置
        blockMatrix[row1][col1] = block2;
        blockMatrix[row2][col2] = block1;

        // 更新块的图像编号
        block1.setImageNumber(imageMatrix[row1][col1]);
        block2.setImageNumber(imageMatrix[row2][col2]);


        // 重绘面板
        repaint();
    }
    



    public GamePanel() {
        init();
        setMouseListener();
    }

    // public static void main(String[] args) {
    //     JFrame frame = new JFrame();
    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     frame.setSize(1250, 1000);
        
    //     GamePanel  gameFrame = new GamePanel();
    //     frame.add(gameFrame);
    //     frame.setVisible(true);
    //     frame.setLocationRelativeTo(null);
    //     frame.setResizable(false);
    // }
}