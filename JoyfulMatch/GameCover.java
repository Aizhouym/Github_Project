package JoyfulMatch;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;


public class GameCover extends JFrame {
    private JButton startButton;
    private JLabel background;
    private JLabel worning;
    private ImageIcon image1;
    private ImageIcon image2;
    private ImageIcon image3;
    private JTextField account;

    public GameCover() {
        initializeComponents();
        createGUI();
        // setLinstener();
    }
    
    private void initializeComponents() {
        image1 = new ImageIcon("E:/Github_Project/JoyfulMatch/Utilities/background5.jpg");
        background = new JLabel(image1);
        image2 = new ImageIcon("E:/Github_Project/JoyfulMatch/Utilities/start.png");
        startButton = new JButton(image2);
        image3 = new ImageIcon("E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/worning2.png");
        worning = new JLabel(image3);
        account = new JTextField(10);
    }
    
    private void createGUI() {
        // 设置窗体大小并显示
        setTitle("喵了个喵");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(image1.getIconWidth(), image1.getIconHeight()); //img 大小为：1800x1000
        setVisible(true);
        setLocationRelativeTo(null);  // 将窗口居中显示
        
        setLayout(new BorderLayout());

        // 在gamePanel中添加游戏区域和其他游戏元素的组件
        setContentPane(background);
        startButton.setBounds(1200, 50, 250, 90);
        add(startButton);
        worning.setBounds(1500,100,160,158);
        add(worning);
        account.setBounds(100,100,100,10);
        add(account);
    }

    public static void main(String[] args) {
        GameCover gameCover = new GameCover();
        gameCover.setVisible(true);
    }
}

