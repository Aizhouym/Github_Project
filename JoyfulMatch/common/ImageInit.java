package JoyfulMatch.common;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class ImageInit {
    
    public static ArrayList<Image> imageList = new ArrayList<Image>();
    //imagePath
    public static String imagePath = "E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/cat";
    //init
    public static void init(){
        
        for(int i = 1; i < 10; i++){
            String image = imagePath + i + ".png";
            ImageIcon imageIcon = new ImageIcon(image);
            imageList.add(imageIcon.getImage());
        }
    }

}   
