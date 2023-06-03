package JoyfulMatch.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class ImageInit {
    
    public static List<BufferedImage> itemImageList = new ArrayList<BufferedImage>();
    //路径
    public static String ImagePath = "/Utilities/";
    //将图片初始化
    public static void init(){
    	String path = "";
        //图片初始化
        for(int i = 0; i < 10; i++){
            try {
            	path = ImagePath + "tile_" +  i + ".png";
            	itemImageList.add(ImageIO.read(ImageInit.class.getResource(path)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }

}   
