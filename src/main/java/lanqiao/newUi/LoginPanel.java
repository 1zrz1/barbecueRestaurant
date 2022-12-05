package lanqiao.newUi;


import lanqiao.service.UserService;
import lanqiao.service.impl.UserServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class LoginPanel extends JFrame {
    private JPanel loginPanel,registerPanel,currentPanel,parentPanel;
    private JLabel label, usernameLable, passwordLable,accountType;
    private JTextField usernameField;
    private JTextField passwordField;
    private JRadioButton consumerRbtn, storeRbtn;
    private ButtonGroup buttonGroup;
    private ImageIcon backgroundIcon = new ImageIcon("src/main/java/lanqiao/newUi/Assna.jpg");
    private ImageIcon titleIcon=new ImageIcon("D:\\maven\\Projiect\\src\\main\\java\\main\\烧烤.png");
    private JButton  loginBtn,resetBtn,exitBtn,registerBtn,backloginBtn,sureRegisterBtn;
    private Font ChineseFont = new Font("宋体", Font.BOLD, 20);
    private Font EnglishFont=new Font("Times New Roman",Font.PLAIN,20);
    private Color color1=new Color(255, 92, 54);
    int k;
  //  private boolean loginFlag;

   // LoginPanel lp;

    public LoginPanel(){
        setTitle("烧烤店");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        this.setIconImage(titleIcon.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
        /* 设置窗口背景色 */
        parentPanel=(JPanel) getContentPane();
        label = new JLabel(backgroundIcon);
        getLayeredPane().add(label, Integer.valueOf(Integer.MIN_VALUE));
        label.setBounds(0, 0, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
        parentPanel.setOpaque(false); // contentPane设置成透明的
        currentPanel=displayLoginPanel();
        parentPanel.add(currentPanel);
        setVisible(true);
    }
    JPanel displayLoginPanel(){
        loginPanel=new JPanel();
        loginPanel.setOpaque(false);//将面板设置为透明
        loginPanel.setLayout(null);

        /* 副标题 */
        JLabel SutitleLabel = new JLabel("烧烤店 登录界面");
        SutitleLabel.setBounds(130,30,300,50);
        SutitleLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
        SutitleLabel.setForeground(color1);
        loginPanel.add(SutitleLabel);
        /* 账户类型 单选按钮*/
        accountType = new JLabel("账户类型:");
        accountType.setBounds(120, 100, 100, 28);
        accountType.setForeground(color1);
        accountType.setFont(ChineseFont);
        loginPanel.add(accountType);

        consumerRbtn = new JRadioButton("员工");
        consumerRbtn.setBounds(220, 107, 20, 15);
        consumerRbtn.setBackground(new Color(250,211,161));
        consumerRbtn.addActionListener(
                e -> {
                    k=3;
                }
        );
        JLabel consumerLable = new JLabel("员工");
        consumerLable.setForeground(Color.red);
        consumerLable.setBounds(240, 107, 40, 15);
        loginPanel.add(consumerLable);

        storeRbtn = new JRadioButton("老板");
        storeRbtn.setBounds(300, 107, 20, 15);
        storeRbtn.setBackground(new Color(250,211,161));
        storeRbtn.addActionListener(
                e -> {
                    k=8;
                }
                );
        JLabel storeLable = new JLabel("老板");
        storeLable.setBounds(320, 107, 30, 15);
        storeLable.setForeground(Color.red);
        loginPanel.add(storeLable);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(storeRbtn);
        buttonGroup.add(consumerRbtn);
        loginPanel.add(storeRbtn);
        loginPanel.add(consumerRbtn);

        /* 用户名和密码输入部分 */
        //用户名
        usernameLable = new JLabel("用户名:");
        usernameLable.setForeground(color1);
        usernameLable.setBounds(120, 140, 100, 28);
        usernameLable.setFont(ChineseFont);
        loginPanel.add(usernameLable);

        usernameField = new JTextField();
        usernameField.setBounds(200, 140, 150, 23);
        usernameField.setFont(ChineseFont);
        loginPanel.add(usernameField);

        //密码
        passwordLable = new JLabel("密  码:");
        passwordLable.setBounds(120, 180, 100, 28);
        passwordLable.setForeground(color1);
        passwordLable.setFont(ChineseFont);

        passwordField = new JPasswordField();
        passwordField.setBounds(200, 182, 150, 23);
        loginPanel.add(passwordLable);
        loginPanel.add(passwordField);
        passwordField.addActionListener(
                e -> {
                    loginBtn.doClick();
                    usernameField.setText("");
                    passwordField.setText("");
                }
        );

        /* 登录按钮 */
        loginBtn = new JButton("登录");
        loginBtn.setBackground(color1);
        loginBtn.setForeground(Color.YELLOW);
        loginBtn.setFocusPainted(false);
        loginBtn.setFont(ChineseFont);
        loginBtn.setBounds(100, 250, 75, 32);
        loginPanel.add(loginBtn);
        loginBtn.addActionListener(
                e -> {
                    String username = usernameField.getText();
                    String password = passwordField.getText();
                    UserService userService =new UserServiceImpl();


                    if (k==3){
                        String type =consumerRbtn.getText();
                        boolean result = userService.login(username,password,type);
                        if (result) {
                            usernameField.setText("");
                            passwordField.setText("");
                            SnewMain snm = new SnewMain();
                            snm.init();
                            setVisible(false);

                        } else {
                            usernameField.setText("");
                            passwordField.setText("");
                            JOptionPane.showMessageDialog(null, "登陆失败，用户名或密码错误");
                        }
                    }else if(k==8){
                        String type =storeRbtn.getText();
                        boolean result = userService.login(username,password,type);
                        if (result) {
                            usernameField.setText("");
                            passwordField.setText("");
                            newMain nm = new newMain();
                            nm.init();
                            setVisible(false);

                        } else {
                            usernameField.setText("");
                            passwordField.setText("");
                            JOptionPane.showMessageDialog(null, "登陆失败，用户名或密码错误");
                        }
                    }


                    /*
                    1.拿到用户名和密码
                    2.把用户名和密码传入Service的login
                    3.Service的login中调用Dao的login
                    4.Dao的login真正实现登录（写JDBC代码）
                     */
                }
        );


        /* 重置按钮 */
        resetBtn = new JButton("重置");
        resetBtn.setBackground(color1);
        resetBtn.setForeground(Color.YELLOW);
        resetBtn.setFocusPainted(false);
        resetBtn.setFont(ChineseFont);
        resetBtn.setBounds(200, 250, 75, 32);
        loginPanel.add(resetBtn);
        resetBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {setCursor(new Cursor(12));}

            @Override
            public void mouseExited(MouseEvent e) {setCursor(new Cursor(0));}
            public void mousePressed(MouseEvent e)  {
                usernameField.setText("");
                passwordField.setText("");
            }
        });
        /* 退出按钮 */
        exitBtn=new JButton("退出");
        exitBtn.setBackground(color1);
        exitBtn.setForeground(Color.YELLOW);
        exitBtn.setFocusPainted(false);
        exitBtn.setFont(ChineseFont);
        exitBtn.setBounds(300, 250, 75, 32);
        loginPanel.add(exitBtn);
        exitBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(12));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(0));
            }
            public void mousePressed(MouseEvent e)  {
                System.exit(0);// 退出系统
            }
        });
        return loginPanel;
    }

    public static void main(String[] args) {
        LoginPanel lps =new LoginPanel();
    }
}