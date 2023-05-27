package JoyfulMatch;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.scene.image.ImageView;

public class ImageInit {
    public static ArrayList<BufferedImage> imageList = new ArrayList<BufferedImage>();
    //imagePath
    public static String imagePath = "E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/cat";
    //init
    public static void init(){
        for(int i = 1; i < 10; i++){
            try {
                String image = imagePath + i + ".png";
                imageList.add(ImageIO.read(ImageView.class.getResource(image)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            

        }
    }

}   
