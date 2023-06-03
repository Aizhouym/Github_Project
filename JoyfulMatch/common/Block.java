package  JoyfulMatch.common;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import JoyfulMatch.GameGUI.GamePanel;

public class Block{
    private int row;
    private int col;
    private int startX = 28;
    private int startY = 60;
    private int dx = 0;//图形显示左上角x位置
	private int odx = 0;//图形更新后显示左上角x位置
	private int dy = 0;//图形显示左上角y位置
	private int ody = 0;//图形更新后显示左上角y位置
    private int dir = 1;//方向 
    private int pIndex = 0;//对应素材图片下标
    private int imageNumber;
    private GamePanel gamePanel;
    private BufferedImage image = null;//图片对象
	private GamePanel panel=null;//GamePanel
	private boolean alive=true;//是否存活
	private boolean selected = false;//是否选中
	private int moveFlag=0;//移动标示  0 不移动 1 横向移动 2纵向移动
	private int speed=15;//移动速度
    private int width = 115;
    private int height = 115;
    private int gap = 3;
    
    public Block(int row, int col, int imageNumber, GamePanel gamepanel){
        this.row = row;
        this.col = col;
        this.dx = startX + col*(width+3);
		this.dy = startY + row*(height+3);
        this.imageNumber = imageNumber;
        this.image = ImageInit.itemImageList.get(pIndex);
        this.gamePanel = gamepanel;

    }

    public void move(int d) {
		this.moveFlag = d;
		int dis = 0;
		if(this.moveFlag == 1){//横向交换，对应横向移动
			this.odx= startX + col*(115+3) + 10;
			dis = this.odx-this.dx;
		}else {
			this.ody=startY + row*(115+3) + 10;
			dis = this.ody-this.dy;
		}
	
		if(dis>0){//向下运动 、向右运动
			dir = 1;
		}else {
			dir = -1;
		}
	}


    	//绘制
	public void draw(Graphics g) {

		if(moveFlag!=0){
			if(this.moveFlag==1){//横向移动
				dx += dir*speed;//dx修改
				if(dir>0){
					if(dx >= odx){//运动到既定位置，停止
						dx = odx;
						moveFlag=0;
						// clear();
					}
				}else{//运动到既定位置，停止
					if(dx <= odx){
						dx = odx;
						moveFlag=0;
						// clear();
					}
				}
			}else {//纵向移动
				dy += dir*speed;
				if(dir>0){
					if(dy >= ody){//运动到既定位置，停止
						dy = ody;
						moveFlag=0;
						// clear();
					}
				}else{
					if(dy <= ody){//运动到既定位置，停止
						dy = ody;
						moveFlag=0;
						// clear();
					}
				}
			}
		}
		//index 默认是0，就是从图片中截取第一个
		int sx1 = 0;
		int sy1 = 0;
		//截取的右下角计算
		int sx2 = 115;
		int sy2 = 115;
	
		g.drawImage(this.image,dx, dy,dx+width,dy+height,sx1,sy1,sx2,sy2 ,null );
		
		
		if(selected){
			//绘制边框
			Color oColor = g.getColor(); 
			g.setColor(Color.pink);
			g.drawRect(dx, dy, 115, 115);
			g.setColor(oColor);
		}
	}
    
    // public int getRow(){
    //     return this.row;
    // }
    
    // public int getCol(){
    //     return this.col;
    // }

    // public void setImageNumber(int imageNumber) {
    //     this.imageNumber = imageNumber;
    // }    

    // public boolean isSelected(){
    //     return selected;
    // }

    // public void setSelected(boolean selected){
    //     this.selected = selected;
    // }

    // public boolean containsPoint(int x, int y) {
    //     int startX = this.startX + this.col * (width + gap);
    //     int startY = this.startY + this.row * (height + gap);
    //     Rectangle blockBounds = new Rectangle(startX, startY, width, height);
    //     return blockBounds.contains(x, y);
    // }


    private void createCard(int i,int j){
		Block card = new Block(i, j, new Random().nextInt(10), panel);
		panel.blockMatrix[i][j]=card;
	}

	//判断鼠标是否卡片范围内
	public boolean isPoint(int x,int y){
		//大于左上角，小于右下角的坐标则肯定在范围内
		if(x>this.dx && y >this.dy
			&& x<this.dx+this.width && y <this.dy+this.height){
			return  true;
		}
		return false;
	}
	
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public int getDx() {
		return dx;
	}
	public void setDx(int dx) {
		this.dx = dx;
	}
	public int getDy() {
		return dy;
	}
	public void setDy(int dy) {
		this.dy = dy;
	}
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public int getpIndex() {
		return pIndex;
	}
	public void setpIndex(int pIndex) {
		this.pIndex = pIndex;
	}
    

}