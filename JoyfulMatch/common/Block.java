package  JoyfulMatch.common;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import JoyfulMatch.GameGUI.GamePanel;

public class Block{
    private int row;
    private int col;
    public static int startX = 28;
    public static int startY = 60;
    private int imageNumber;
    private GamePanel gamePanel;
    public static int width = 115;
    public static int height = 115;
    public static int gap = 3;
    private boolean selected = false; //设置是否被选中
    
    public Block(int row, int col, int imageNumber, GamePanel jpanel){
        this.row = row;
        this.col = col;
        this.imageNumber = imageNumber;
        this.gamePanel = jpanel;
    }

    public void draw(Graphics g){
        Image image  = ImageInit.imageList.get(imageNumber);
        int x = startX + col * (width + gap);
        int y = startY + row * (height + gap);
        g.drawImage(image, x, y, gamePanel); 

        if (selected) {
            g.setColor(Color.RED);
            g.drawRect(x, y, width, height);
        }

    } 
    
    public int getRow(){
        return this.row;
    }
    
    public int getCol(){
        return this.col;
    }

    public void setImageNumber(int imageNumber) {
        this.imageNumber = imageNumber;
    }  
    
    public int getImageNumber(){
        return this.imageNumber;
    }

    public boolean isSelected(){
        return selected;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }


    public boolean containsPoint(int x, int y) {
        int startX = Block.startX + this.col * (width + gap);
        int startY = Block.startY + this.row * (height + gap);
        Rectangle blockBounds = new Rectangle(startX, startY, width, height);
        return blockBounds.contains(x, y);
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void handleClick(int x, int y) {
        if (containsPoint(x, y)) {
            setSelected(!isSelected()); // 切换选中状态
            gamePanel.repaint(); // 通知面板进行重绘
        }
    }
    
    
}