package JoyfulMatch.GameGUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

import JoyfulMatch.common.Block;
import JoyfulMatch.common.ImageInit;


public class GamePanel extends JPanel {
    private int[][] imageMatrix;
    private Block[][] blockMatrix;
    // private Block selectedBlock;

    private ImageInit imageInit;
    private Image background;
    private Font headerFont;
    private long startTime;
    private int score;
    Random random = new Random();


    private void init() {
        imageInit = new ImageInit();
        imageInit.init();
        background = new ImageIcon("E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/background2.jpg").getImage(); 
        // 随机生成图像矩阵
        
        imageMatrix = new int[7][10];
        blockMatrix = new Block[7][10];

        for (int row = 0; row < imageMatrix.length; row++) {
            for (int col = 0; col < imageMatrix[0].length; col++) {
                int num = getRandomImage(row, col);
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

    private int getRandomImage(int row, int col) {
        int num = random.nextInt(9);
        int countRow = countSameImagesRow(row, num);
        int countCol = countSameImagesCol(col, num);
        
        while (countRow >= 2 || countCol >= 2) {
            num = random.nextInt(9);
            countRow = countSameImagesRow(row, num);
            countCol = countSameImagesCol(col, num);
        }
        
        return num;
    }
    
    private int countSameImagesRow(int row, int num) {
        int count = 0;
        
        for (int col = 0; col < imageMatrix[0].length; col++) {
            if (imageMatrix[row][col] == num) {
                count++;
            }
        }
        
        return count;
    }
    
    private int countSameImagesCol(int col, int num) {
        int count = 0;
        
        for (int row = 0; row < imageMatrix.length; row++) {
            if (imageMatrix[row][col] == num) {
                count++;
            }
        }
        
        return count;
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

    public void setMouseListener() {
        addMouseListener(new MouseAdapter() {
            
            private Block selectedBlock = null;
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
    
                // 计算被点击的方块在矩阵中的位置
                int row = (mouseY - Block.startY) / (Block.height +Block.gap);
                int col = (mouseX - Block.startX) / (Block.width + Block.gap);
    
                // 检查点击的方块是否在有效范围内
                if (row >= 0 && row < imageMatrix.length && col >= 0 && col < imageMatrix[0].length) {
                    Block clickedBlock = blockMatrix[row][col];
    
                    // 如果之前没有选择方块，则选中当前点击的方块
                    if (selectedBlock == null) {
                        selectedBlock = clickedBlock;
                        selectedBlock.setSelected(true);
                    } else {
                        // 否则，进行位置交换
                        swapBlocks(selectedBlock, clickedBlock);
                        selectedBlock.setSelected(false);
                        clickedBlock.setSelected(false);
                        selectedBlock = null;
                        // processEliminationAndFalling();
                    }
                }
            }
        });
    }
    
    private void swapBlocks(Block block1, Block block2) {
        int row1 = block1.getRow();
        int col1 = block1.getCol();
        int row2 = block2.getRow();
        int col2 = block2.getCol();
    
        // 检查两个方块的位置是否相邻且可以交换
        boolean isAdjacent = (Math.abs(row1 - row2) == 1 && col1 == col2) || (Math.abs(col1 - col2) == 1 && row1 == row2);
    
        // 检查两个方块的类型是否不同
        boolean areDifferentTypes = block1.getImageNumber() != block2.getImageNumber();
    
        if (isAdjacent && areDifferentTypes) {
            // 交换块在图像矩阵中的位置
            int temp = imageMatrix[row1][col1];
            imageMatrix[row1][col1] = imageMatrix[row2][col2];
            imageMatrix[row2][col2] = temp;
            
            // // 检查是否会发生消除
            // boolean willEliminate = hasPotentialElimination(block1, block2);
            
            // 交换块数组中的位置
            blockMatrix[row1][col1] = block2;
            blockMatrix[row2][col2] = block1;
    
            // 更新块的行列索引
            block1.setRow(row2);
            block1.setCol(col2);
            block2.setRow(row1);
            block2.setCol(col1);
    
            // 重绘面板
            repaint();
            
            //消除判断
            checkAndProcessElimination();

        }
    }
    
    private void checkAndProcessElimination(){
        ArrayList<Block> horizontalMatches = findHorizontalMatches();
        ArrayList<Block> verticalMatches = findVerticalMatches();

        int totalMatchSize = horizontalMatches.size() + verticalMatches.size();

        if (!horizontalMatches.isEmpty() || !verticalMatches.isEmpty()) {
            // 存在消除的情况
            for (Block block : horizontalMatches) {
                // 替换被消除的方块的图像编号
                int row = block.getRow();
                int col = block.getCol();
                imageMatrix[row][col] = 0;
            }

            for (Block block : verticalMatches) {
                // 替换被消除的方块的图像编号
                int row = block.getRow();
                int col = block.getCol();
                imageMatrix[row][col] = 0;
            }

            //进行block的下落以及填充。
            fillEmptySpacesAndDropBlocks();

            if (totalMatchSize >= 3 && totalMatchSize <= 5){
                score += (int) Math.pow(2, totalMatchSize - 3) * 5;;
            }else{
                score += 30; //超过五个以上block消除，每次得30分。
            }

            // 重绘面板
            repaint();
        }
    }//进行检查

    private ArrayList<Block> findHorizontalMatches() {
        ArrayList<Block> matches = new ArrayList<>();
    
        for (int row = 0; row < imageMatrix.length; row++) {
            int count = 1; // 记录连续相同方块的数量
            int num = imageMatrix[row][0]; // 当前比较的图像编号
    
            for (int col = 1; col < imageMatrix[0].length; col++) {
                if (num == imageMatrix[row][col]) {
                    count++;
                } else {
                    if (count >= 3) {
                        for (int i = col - count; i < col; i++) {
                            matches.add(blockMatrix[row][i]);
                        }
                    }
    
                    count = 1;
                    num = imageMatrix[row][col];
                }
            }
    
            if (count >= 3) {
                for (int i = imageMatrix[0].length - count; i < imageMatrix[0].length; i++) {
                    matches.add(blockMatrix[row][i]);
                }
            }
        }
    
        return matches;
    }//水平记录
    
    private ArrayList<Block> findVerticalMatches() {
        ArrayList<Block> matches = new ArrayList<>();
    
        for (int col = 0; col < imageMatrix[0].length; col++) {
            int count = 1;
            int num = imageMatrix[0][col];
    
            for (int row = 1; row < imageMatrix.length; row++) {
                if (num == imageMatrix[row][col]) {
                    count++;
                } else {
                    if (count >= 3) {
                        for (int i = row - count; i < row; i++) {
                            matches.add(blockMatrix[i][col]);
                        }
                    }
    
                    count = 1;
                    num = imageMatrix[row][col];
                }
            }
    
            if (count >= 3) {
                for (int i = imageMatrix.length - count; i < imageMatrix.length; i++) {
                    matches.add(blockMatrix[i][col]);
                }
            }
        }

        return matches;
    }//垂直记录

    private void fillEmptySpacesAndDropBlocks() {
        for (int col = 0; col < imageMatrix[0].length; col++) {
            int emptySpaces = 0;
    
            for (int row = imageMatrix.length - 1; row >= 0; row--) {
                if (imageMatrix[row][col] == 0) {
                    emptySpaces++;
                } else if (emptySpaces > 0) {
                    int newRow = row + emptySpaces;
                    imageMatrix[newRow][col] = imageMatrix[row][col];
                    blockMatrix[newRow][col].setImageNumber(imageMatrix[row][col]);
                    blockMatrix[newRow][col].setRow(newRow);
                    imageMatrix[row][col] = 0;
                }
            }
    
            for (int i = 0; i < emptySpaces; i++) {
                int num = random.nextInt(9);
                imageMatrix[i][col] = num;
                blockMatrix[i][col].setImageNumber(num);
                blockMatrix[i][col].setRow(i);
            }
        }
    
        // 检查并处理消除情况
        checkAndProcessElimination();
    }
    

    
    public GamePanel() {
        init();
        setMouseListener();
    }

}