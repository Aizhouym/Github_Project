package  JoyfulMatch.common;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import JoyfulMatch.GameGUI.GamePanel;

public class Block{
    private int row;
    private int col;
    private int startX = 28;
    private int startY = 60;
    private int dx = 0;
    private int dy = 0;
    private int odx = 0;
    private int ody = 0;
    private int imageNumber;
    private GamePanel gamePanel;
    private int width = 115;
    private int height = 115;
    private int gap = 3;
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

    public boolean isSelected(){
        return selected;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }


    public boolean containsPoint(int x, int y) {
        int startX = this.startX + this.col * (width + gap);
        int startY = this.startY + this.row * (height + gap);
        Rectangle blockBounds = new Rectangle(startX, startY, width, height);
        return blockBounds.contains(x, y);
    }
    
}