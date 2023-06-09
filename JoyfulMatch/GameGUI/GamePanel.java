package JoyfulMatch.GameGUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import javax.swing.*;
import JoyfulMatch.common.Block;
import JoyfulMatch.common.ImageInit;
import JoyfulMatch.common.RankData;

import java.sql.*;


public class GamePanel extends JPanel {

    private int[][] imageMatrix;
    private int[][] beforeImageMatrix;
    private Block[][] blockMatrix;

    private ImageInit imageInit;
    private Image background;
    private Font headerFont;
    private long startTime;
    private int beforeScore;
    public  int score;
    private Timer timer;
    private long pausedTime;  // 记录暂停时的时间
    private boolean isPaused; // 记录当前是否处于暂停状态
    private boolean isOver ; //记录是否结束状态
    private boolean isLocked; //锁定panel
    public String name;
    public ArrayList<RankData> rankingData;
    public boolean HardMode = GameWindow.isHard;

    Random random = new Random();

    protected void init() {
        imageInit = new ImageInit();
        imageInit.init();
        background = new ImageIcon("E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/background6.jpg").getImage(); 
        // 随机生成图像矩阵
        
        imageMatrix = new int[7][10];
        beforeImageMatrix = new int[7][10];
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
        timer = new Timer(1000, e -> {
            repaint();
        });
        timer.start();

        //设置是否结束
        isOver = false;
        //设置面板是否锁定
        isLocked = false;
        //设置得分
        score = 0;
        beforeScore = 0;

        this.name = GameWindow.name;

        rankingData = new ArrayList<>();
    }

    public void setHardMode(boolean isHard){
        this.HardMode = isHard;
    }

    //计时器
    public void pauseTimer() {
        timer.stop();
        pausedTime = System.currentTimeMillis();
        isPaused = true;
    }
    
    public void resumeTimer() {
        if (isPaused) {
            long pauseDuration = System.currentTimeMillis() - pausedTime;
            startTime += pauseDuration;
            timer.start();
            isPaused = false;
        }
    }

    public void gameOver(){

        if(HardMode){

            try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/joyful_match?serverTimezone=Asia/Shanghai", "root", "12345678")){
                String insertQuery = "INSERT INTO rank_hard (Name, Score) VALUES (?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setString(1, GameWindow.name);
                insertStatement.setInt(2, score);
                insertStatement.executeUpdate();
            }catch (SQLException e) {
                e.printStackTrace();
            }

        }else{

            try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/joyful_match?serverTimezone=Asia/Shanghai", "root", "12345678")){
                String insertQuery = "INSERT INTO rank_easy (Name, Score) VALUES (?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setString(1, GameWindow.name);
                insertStatement.setInt(2, score);
                insertStatement.executeUpdate();

            }catch (SQLException e) {
                e.printStackTrace();
            }


        }



    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public int getScore(){
        return this.score;
    }

    public void shuffItem(){
        if (!isOver){
            int[][] shuffledMatrix = new int[imageMatrix.length][imageMatrix[0].length];
    
            // 洗牌算法打乱原始矩阵
            ArrayList<Integer> numbers = new ArrayList<>();
            for (int i = 0; i < imageMatrix.length; i++) {
                for (int j = 0; j < imageMatrix[0].length; j++) {
                    numbers.add(imageMatrix[i][j]);
                }
            }
            
            Collections.shuffle(numbers);
            
            // 更新imageMatrix为打乱后的矩阵
            int index = 0;
            for (int i = 0; i < imageMatrix.length; i++) {
                for (int j = 0; j < imageMatrix[0].length; j++) {
                    shuffledMatrix[i][j] = numbers.get(index++);
                }
            }
            
            // 更新图像矩阵和方块数组
            imageMatrix = shuffledMatrix;
            for (int row = 0; row < imageMatrix.length; row++) {
                for (int col = 0; col < imageMatrix[0].length; col++) {
                    int num = imageMatrix[row][col];
                    blockMatrix[row][col].setImageNumber(num);
                    blockMatrix[row][col].setRow(row);
                    blockMatrix[row][col].setCol(col);
                }
            }
    
            checkAndProcessElimination();
            // 重绘面板
            repaint();
        }
    }//随机打乱对应的panel图片

    public void backItem(){
        if (!isOver){
            if (beforeImageMatrix != null) {
                imageMatrix = Arrays.stream(beforeImageMatrix).map(int[]::clone).toArray(int[][]::new);
                
                //恢复score
                score = beforeScore;
                // 更新blockMatrix
                for (int row = 0; row < imageMatrix.length; row++) {
                    for (int col = 0; col < imageMatrix[0].length; col++) {
                        int num = imageMatrix[row][col];
                        blockMatrix[row][col].setImageNumber(num);
                        blockMatrix[row][col].setRow(row);
                        blockMatrix[row][col].setCol(col);
                    }
                }
                
                // 重绘面板
                repaint();
            }
        }
    }//返回到交换前的状态


    //实现初始化不会有三个相同image出现
    protected int getRandomImage(int row, int col) {
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
    
    protected int countSameImagesRow(int row, int num) {
        int count = 0;
        
        for (int col = 0; col < imageMatrix[0].length; col++) {
            if (imageMatrix[row][col] == num) {
                count++;
            }
        }
        
        return count;
    }
    
    protected int countSameImagesCol(int col, int num) {
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
        long currentTime = isPaused ? pausedTime : System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        String timeText = "时间：" + elapsedTime / 1000 + "秒";
        String scoreText = "得分: " + score;
        // int textHeight = g.getFontMetrics().getHeight();
        g.drawString(timeText, 300, startY - 8);
        g.drawString(scoreText, 700, startY - 8);


        if(!HardMode){
            // 判断时间是否超过120秒
            if (elapsedTime >= 120000) {
                //isOver为 true
                isOver = true;
                isLocked = true;
                timer.stop();
            }
        }

        if(isOver){
            String over = "  游戏结束  ";
            g.setFont(new Font("宋体", Font.BOLD, 50));
            g.drawString(over, 500, 500);
            String rank = " 请查看排名  ";
            g.setFont(new Font("宋体", Font.BOLD, 50));
            g.drawString(rank, 500, 550);
            gameOver();
            rankingData = fetchRankingData();
        }

    }

    public void setMouseListener() {
        addMouseListener(new MouseAdapter() {
            
            private Block selectedBlock = null;
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!isLocked){
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
                
            }
        });
    }
    
    protected void swapBlocks(Block block1, Block block2) {
        // 交换操作前保存当前状态
        beforeImageMatrix = Arrays.stream(imageMatrix).map(int[]::clone).toArray(int[][]::new);
        //记录交换前的分数
        beforeScore = score;

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
    
    protected void checkAndProcessElimination(){
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

    protected ArrayList<Block> findHorizontalMatches() {
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
    
    protected ArrayList<Block> findVerticalMatches() {
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

    protected void fillEmptySpacesAndDropBlocks() {
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

    protected ArrayList<RankData> fetchRankingData() {
        ArrayList<RankData> rankingData = new ArrayList<>();
        String easy_mode = "SELECT Name, Score FROM rank_easy ORDER BY Score DESC";
        String hard_mode = "SELECT Name, Score FROM rank_hard ORDER BY Score DESC";
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/joyful_match?serverTimezone=Asia/Shanghai", "root", "12345678")) {
            //建立string 
            String query;
            if(HardMode){
                query = hard_mode;
            }else{
                query = easy_mode;
            }

            // 建立执行query
            // Statement statement = connection.createStatement();
            PreparedStatement statement = connection.prepareStatement(query);

            // 获取对应的数据
            ResultSet resultSet = statement.executeQuery();

            // Process the query results and populate the rankingData list
            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                int score = resultSet.getInt("Score");
                RankData rankData = new RankData(name, score);
                rankingData.add(rankData);
            }
    
            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rankingData;
    }
        
    public GamePanel() {
        init();
        setMouseListener();
    }

}