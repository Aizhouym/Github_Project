package JoyfulMatch.GameGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

import com.mysql.cj.x.protobuf.MysqlxResultset.Row;

import JoyfulMatch.common.Block;
import JoyfulMatch.common.ImageInit;


public class GamePanel extends JPanel implements ActionListener {

    public Block[][] blockMatrix;
    private Block selectedBlock;
    private ImageInit imageInit;
    private Image background;
    private Font headerFont;
    private long startTime;
    public static int curCount=0;//分数
    ArrayList indexs;
    public String  gameFlag = "";
    private Thread mainThread = null;
    private int time = 0;
    public static final int ROWS = 7;//行
	public static final int COLS = 10;//列


    private void init() {

        indexs = new ArrayList<Integer>();
        blockMatrix = new Block[7][10];
        gameFlag = "start";

        imageInit = new ImageInit();
        imageInit.init();

        initImageMatrix();

        initBlock();

    	//添加鼠标事件监控
		createMouseListener();

        background = new ImageIcon("E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/background2.jpg").getImage(); 

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

    private void initBlock(){

        // 随机生成图像矩阵
        Random random = new Random();
        Block block;
		int index = 0;
		int temp=0;

		for (int i = 0; i <ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				temp = Integer.valueOf(String.valueOf(indexs.get(index)));
				block = new Block(i, j, temp, this);
				blockMatrix[i][j]=block;
				index++;
			}
		}
    }

    private void initImageMatrix(){
        Random random = new Random();
		int n ;
		while(true){//
			n = random.nextInt(10);//随机从6张图片下标中选取[0-10]
			indexs.add(n);
			if(indexs.size()==70){
				break;
			}
		}
    }

    //鼠标事件的创建
	private void createMouseListener() {
		MouseAdapter mouseAdapter = new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!"start".equals(gameFlag)) return ;
				
				int x = e.getX();
				int y = e.getY();
				Block block;
				for (int i = 0; i <ROWS; i++) {
					for (int j = 0; j < COLS; j++) {
						block = blockMatrix[i][j];
						if(block==null)continue;
						
						if(block.isPoint(x, y)){
							
							if(selectedBlock==null){
								selectedBlock = block ;
								block.setSelected(true);
							}else {
								int dir= checkTran(block);
								if(dir!=0&&dir!=4){//相邻才能交换
									tran(block,dir);
								}else {//不是相邻则当前取消选择
									selectedBlock.setSelected(false);
									block.setSelected(true);
									selectedBlock = block ;
								}
							}
							return ;//直接跳出
						}
					}
				}
			}
		};
		addMouseMotionListener(mouseAdapter);
		addMouseListener(mouseAdapter);
	}

    //相邻才能交换
	private int checkTran(Block block) {
		if(block.getpIndex()==selectedBlock.getpIndex()){//相同的不交换
			return 4;
		}
		
		int row = selectedBlock.getRow();
		int col = selectedBlock.getCol();
		
		int row1 = block.getRow();
		int col1 = block.getCol();
		
		if(col == col1){//在上下
			if(row1 + 1 == row|| row1 - 1 == row){
				return 2;
			}
		}
		if(row == row1){//在左右
			if( col1 + 1 == col || col1 - 1 == col){
				return 1;
			}
		}

		return 0;
	}
	//检查路径
	protected void tran(Block block,int dir) {
		Block temp = selectedBlock;
		selectedBlock.setSelected(false);
		selectedBlock= null;
		
		int row = block.getRow();
		int col = block.getCol();
		int row1= temp.getRow();
		int col1 = temp.getCol();
		if(dir == 1){//横向交换，对应横向移动
			block.setCol(col1);
			temp.setCol(col);
		}else {//纵向交换，对应纵向移动
			block.setRow(row1);
			temp.setRow(row);
		}
		//交换在2维数组中的对应位置
		blockMatrix[row][col]= temp;
		blockMatrix[row1][col1]= block;
		
		block.move(dir);
		temp.move(dir);
	}
	

    // //刷新线程，用来重新绘制页面
	// int n = 0;
	// private class RefreshThread implements Runnable {
	// 	@Override
	// 	public void run() {
	// 		while (true) {
	// 			if ("start".equals(gameFlag)) {
	// 				repaint();
					
	// 				n++;
	// 				if(n==5){
	// 					time++;
	// 					n=0;
	// 				}
	// 				if(time>300){
	// 					if(curCount<=3000){
	// 						gameOver();
	// 					}else {
	// 						gameWin();
	// 					}
	// 				}
	// 			}
				
	// 			try {
	// 				Thread.sleep(200);
	// 			} catch (InterruptedException e) {
	// 			}
	// 		}
	// 	}
	// }

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
        int matrixWidth = blockMatrix[0].length * (imageWidth + gap) - gap;
        int matrixHeight = blockMatrix.length * (imageHeight + gap) - gap;
        int startX = (panelWidth - matrixWidth) / 2;
        int startY = (panelHeight - matrixHeight) / 2;

        // 绘制蓝色矩形框
        g.setColor(Color.WHITE);
        g.fillRect(startX - gap, startY - gap, matrixWidth + 2 * gap, matrixHeight + 2 * gap);

        // 绘制图像
        Block block;
        for (int row = 0; row < blockMatrix.length; row++) {
            for (int col = 0; col < blockMatrix[0].length; col++) {
                // int index = imageMatrix[row][col];
                // Image image = ImageInit.imageList.get(index);
                // int x = startX + col * (imageWidth + gap);
                // int y = startY + row * (imageHeight + gap);
                // g.drawImage(image, x, y, this);
                block = blockMatrix[row][col];
                if(block != null){
                    block.draw(g);
                }
            }
        }

        // 绘制标题文本
        g.setFont(headerFont);
        g.setColor(Color.RED);
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        String timeText = "时间：" + elapsedTime / 1000 + "秒";
        String scoreText = "得分: "+ curCount;
        // int textHeight = g.getFontMetrics().getHeight();
        g.drawString(timeText, 300, startY-8);
        g.drawString(scoreText, 700, startY-8);

    }





    public GamePanel() {
        init();
        
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }



}