package JoyfulMatch.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class GameImage {
    public GameImage(){}

    public static BufferedImage getImg(String path){
        //try - catch 捕获
        try {
            BufferedImage img = ImageIO.read(GameImage.class.getResource(path));
            return img;
        }
        //异常处理，打印异常
        catch (IOException e) {
            e.printStackTrace();
        }
        //没找到则返回空
        return null;
    }

    public static ImageIcon getImg2(String path){
        
        try {
        	BufferedImage img = ImageIO.read(GameImage.class.getResource(path));
            return new ImageIcon(img);
        }
        //异常处理，打印异常
        catch (IOException e) {
            e.printStackTrace();
        }
        //没找到则返回空
        return null;
    }
    
    //将指定的图片加载成map的形式返回(BufferedImage)
    public static HashMap<String, BufferedImage> getImageMapByIcon(String path,List<String> nameList) {
    	HashMap<String, BufferedImage> target = new HashMap<>();
    	BufferedImage img= null;
    	String name="";
    	String key="";
    		for(int i=0;i<nameList.size();i++){
    			name = (String)nameList.get(i);
    			key = name.split("\\.")[0];
    			img = GameImage.getImg(path+nameList.get(i));
    			target.put(key, img);
    	}
    	return target;
	}
    
    //将指定的图片加载成map的形式返回(ImageIcon)
    public static HashMap<String, ImageIcon> getImageMapByIcon2(String path, List<String> nameList) {
    	HashMap<String, ImageIcon> target = new HashMap<>();
    	ImageIcon img= null;
    	String name="";
    	String key="";
    		for(int i=0;i<nameList.size();i++){
    			name = (String)nameList.get(i);
    			key = name.split("\\.")[0];
    			img = GameImage.getImg2(path+nameList.get(i));
    			target.put(key, img);
    	}
    	return target;
	}
}
