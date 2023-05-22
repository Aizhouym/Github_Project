package JoyfulMatch;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Container;

public class GameWindow extends JFrame {
    private JPanel gamePanel;
    private JLabel scoreLabel;
    private JLabel background;
    private JButton startButton;


    public GameWindow() {
        initializeComponents();
        createGUI();
    }
    
    private void initializeComponents() {
        gamePanel = new JPanel();
        startButton = new JButton("Start");
        scoreLabel = new JLabel("Score: 0");
        ImageIcon image = new ImageIcon("E:/Github_Project/JoyfulMatch/Utilities/background4.jpg");
        background = new JLabel(image);
        ImageIcon image2 = new ImageIcon("E:/Github_Project/JoyfulMatch/Utilities/start.png");
        startButton = new JButton(image2);
    }
    
    private void createGUI() {
        // 设置窗体大小并显示
        setTitle("Happy Match");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(1800, 1000); //img 大小为：450x700
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);  // 将窗口居中显示

        Container container = new Container(); // 获取窗体容器
        container.setLayout(new BorderLayout());


        // 在gamePanel中添加游戏区域和其他游戏元素的组件
        setContentPane(background);
        container.add(startButton);
        
        
    
    }
    
    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow();
        gameWindow.setVisible(true);
    }
}

