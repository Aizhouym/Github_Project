package JoyfulMatch.GameGUI;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameCover extends JFrame {
    private JLabel background;
    private JLabel worning;
    private ImageIcon image1;
    private ImageIcon image2;
    private ImageIcon image3;
    private ImageIcon image4;
    private static JTextField account;
    private static JPasswordField password;
    private JButton signUp;
    private JButton logIn;
    private ImageIcon icon;
    public static String name;

    public GameCover() {
        initalizeComponents();
        createGUI();
        setLinstener();
    }
    //initalize
    private void initalizeComponents() {
        image1 = new ImageIcon("E:/Github_Project/JoyfulMatch/Utilities/background4.jpg");
        background = new JLabel(image1);
        image2 = new ImageIcon("E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/logIn.png");
        logIn = new JButton(image2);
        image3 = new ImageIcon("E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/signUp.png");
        signUp = new JButton(image3);
        image4 = new ImageIcon("E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/worning2.png");
        worning = new JLabel(image4);
        account = new JTextField(10);
        password = new JPasswordField(10);
        icon = new ImageIcon("E:/Github_JoyfulMatch/Github_Project/JoyfulMatch/Utilities/Icon2.png");

    }
    
    private void createGUI() {
        // 设置窗体大小并显示
        setTitle("喵了个喵");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1850, 1050); //img 大小为：1800x1000
        setVisible(true);
        setLocationRelativeTo(null);  // 将窗口居中显示
        
        setLayout(new BorderLayout());

        // 在contentPane中添加游戏区域和其他游戏元素的组件
        setContentPane(background);
        account.setBounds(1200,30,250,30);
        add(account);
        password.setBounds(1200, 90, 250, 30);
        add(password);
        logIn.setBounds(1392, 139, 130, 53);
        add(logIn);
        signUp.setBounds(1239, 139, 130, 53);
        add(signUp);
        worning.setBounds(1050,875,200,200);
        add(worning);
       
    }

    
    public void setLinstener(){
        String Name = account.getText();
        name = Name;
        char[] Password_char = password.getPassword();
        String Password = new String(Password_char);
        // String Password = password.fetPassword().toString(),返回的是char数组默认的字符串，而并非密码中的内容 
        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                // 在这里将Name和Password插入到数据库的user表中
                // 例如使用JDBC进行数据库操作
                //加入serverTime时区为东八区
                try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/joyful_match?serverTimezone=Asia/Shanghai", "root", "12345678")) {
                    // 检查用户名是否已存在
                    String checkQuery = "SELECT * FROM user WHERE name = ?";
                    PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
                    checkStatement.setString(1, Name);
                    ResultSet checkResultSet = checkStatement.executeQuery();
    
                    if (checkResultSet.next()) {
                        // 用户名已存在，弹出警告框
                        JOptionPane.showMessageDialog(null, "Username already exists", "Registration Failed", JOptionPane.WARNING_MESSAGE);
                    } else {
                        // 用户名不存在，执行插入操作
                        String insertQuery = "INSERT INTO user (name, password) VALUES (?, ?)";
                        PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                        insertStatement.setString(1, Name);
                        insertStatement.setString(2, Password);
                        insertStatement.executeUpdate();
                        // 插入成功的处理逻辑
                        Object[] options = { "EasyMode", "HardMode" };
                        int modeSelection = JOptionPane.showOptionDialog(null, "Select the game mode", "signup successful",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    
                        dispose(); // 关闭当前窗口
                        if (modeSelection == 0){
                            GameWindow gameWindow = new GameWindow();
                            gameWindow.setVisible(true);
                        }else{

                        }

                        // SwingUtilities.invokeLater(GameWindow::new);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // 数据库操作失败的处理逻辑
                }
            }
        });
        
        logIn.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/joyful_match?serverTimezone=Asia/Shanghai", "root", "12345678")) {
                String query = "SELECT * FROM user WHERE name = ? AND password = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, Name);
                statement.setString(2, Password);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    // 用户名和密码匹配，进行游戏跳转等操作

                    Object[] options = { "EasyMode", "HardMode" };
                    int modeSelection = JOptionPane.showOptionDialog(null, "Select the game mode", "Login successful",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    
                    dispose(); // 关闭当前窗口
                    if (modeSelection == 0){
                        GameWindow gameWindow = new GameWindow();
                        gameWindow.setVisible(true);
                    }else{
                        
                    }
    
                    // GameWindow gameWindow = new GameWindow();

                    // gameWindow.setGameMode(modeSelection == 0 ? GameMode.EASY : GameMode.HARD); // 根据选择的模式设置游戏模式
                    // gameWindow.setVisible(true);
                } else {
                    // 用户名和密码不匹配，弹出警告框
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Login Failed", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                // 数据库操作失败的处理逻辑
            }
            }
        });

    }

    
}

