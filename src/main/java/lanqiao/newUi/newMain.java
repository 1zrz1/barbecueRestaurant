package lanqiao.newUi;

/**
 * @Author 李冰冰
 * @Date 2022/12/04
 * @Version 17.0.5
 */

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class newMain {
    JPanel currentPanel;
    JPanel contentPanel;


    JButton rootBtn;
    JButton userBtn;
    JButton commodityBtn ;
    JButton inventoryBtn ;
    JButton purchaseBtn;
    JButton statsBtn;
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                newMain nm=new newMain();
                nm.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public void init(){
        JFrame mainFrame=new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("烧烤店管理系统");
        mainFrame.setSize(1000,690);
        mainFrame.setResizable(false); //固定窗口大小
        mainFrame.setLocationRelativeTo(null);
        Container parentJPanel = mainFrame.getContentPane();
        parentJPanel.setLayout(null);
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setSize(120,690);

         ImageIcon backgroundIcon = new ImageIcon("src/main/java/lanqiao/newUi/Assna.jpg");
        Color color1=new Color(51, 161, 201);

         rootBtn = new JButton("系统界面");
         userBtn = new JButton("用户管理");
         commodityBtn = new JButton("订单管理");
         inventoryBtn = new JButton("库存管理");
         purchaseBtn= new JButton("进货管理");
         statsBtn= new JButton("统计分析");

        menuPanel.add(rootBtn);
        menuPanel.add(userBtn);
        menuPanel.add(commodityBtn);
        menuPanel.add(inventoryBtn);
        menuPanel.add(purchaseBtn);
        menuPanel.add(statsBtn);
        rootBtn.setBounds(20,20,90,50);
        userBtn.setBounds(20,80,90,50);
        commodityBtn.setBounds(20,140,90,50);
        inventoryBtn.setBounds(20,200,90,50);
        purchaseBtn.setBounds(20,260,90,50);
        statsBtn.setBounds(20,320,90,50);

        contentPanel = new JPanel();
        contentPanel.setBounds(130,0,860,690);
        contentPanel.setLayout(null);

        JLabel label= new JLabel(backgroundIcon);
        JPanel rootcurrentPanel= new JPanel();
        mainFrame.getLayeredPane().add(label, Integer.valueOf(Integer.MIN_VALUE));
        label.setBounds(0, 0, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
        rootcurrentPanel.setBounds(0,0,860,690);
        rootcurrentPanel.setLayout(null);
        rootcurrentPanel.add(label);


        JLabel label1= new JLabel(backgroundIcon);
        mainFrame.getLayeredPane().add(label1, Integer.valueOf(Integer.MIN_VALUE));
        label1.setBounds(0, 0, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
        currentPanel =new JPanel();
        currentPanel.setBounds(0,0,860,690);
        currentPanel.setLayout(null);
        currentPanel.add(label1);



        contentPanel.add(currentPanel);

        parentJPanel.add(menuPanel);
        menuPanel.setBackground(color1);
        parentJPanel.add(contentPanel);

        rootBtn.addActionListener(
                e->{
                    contentPanel.remove(currentPanel);
                    currentPanel=rootcurrentPanel;
                    contentPanel.add(currentPanel);
                    parentJPanel.validate();
                    parentJPanel.repaint();// 重绘内容面板
                    rootBtn.setBackground(Color.ORANGE);
                    userBtn.setBackground(null);
                    commodityBtn.setBackground(null);
                    inventoryBtn.setBackground(null);
                    purchaseBtn.setBackground(null);
                    statsBtn.setBackground(null);
                }
        );

        userBtn.addActionListener(
                e->{
                    contentPanel.remove(currentPanel);
                    try {
                        currentPanel= new UserPanel().init();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    contentPanel.add(currentPanel);
                    parentJPanel.validate();
                    parentJPanel.repaint();// 重绘内容面板
                    userBtn.setBackground(Color.ORANGE);
                    rootBtn.setBackground(null);
                    commodityBtn.setBackground(null);
                    inventoryBtn.setBackground(null);
                    purchaseBtn.setBackground(null);
                    statsBtn.setBackground(null);
                }
        );
        commodityBtn.addActionListener(
                e->{
                    contentPanel.remove(currentPanel);
                    currentPanel= CommodityPanel.init();
                    contentPanel.add(currentPanel);
                    parentJPanel.validate();
                    parentJPanel.repaint();// 重绘内容面板
                    commodityBtn.setBackground(Color.ORANGE);
                    rootBtn.setBackground(null);
                    userBtn.setBackground(null);
                    inventoryBtn.setBackground(null);
                    purchaseBtn.setBackground(null);
                    statsBtn.setBackground(null);
                }
        );
        inventoryBtn.addActionListener(
                e->{
                    contentPanel.remove(currentPanel);
                    currentPanel= InventoryPanel.init();
                    contentPanel.add(currentPanel);
                    parentJPanel.validate();
                    parentJPanel.repaint();// 重绘内容面板
                    inventoryBtn.setBackground(Color.ORANGE);
                    rootBtn.setBackground(null);
                    userBtn.setBackground(null);
                    commodityBtn.setBackground(null);
                    purchaseBtn.setBackground(null);
                    statsBtn.setBackground(null);
                }
        );
        purchaseBtn.addActionListener(
                e->{
                    contentPanel.remove(currentPanel);
                    currentPanel= PurchasePanel.init();
                    contentPanel.add(currentPanel);
                    parentJPanel.validate();
                    parentJPanel.repaint();// 重绘内容面板
                    purchaseBtn.setBackground(Color.ORANGE);
                    rootBtn.setBackground(null);
                    userBtn.setBackground(null);
                    commodityBtn.setBackground(null);
                    inventoryBtn.setBackground(null);
                    statsBtn.setBackground(null);
                }
        );
        statsBtn.addActionListener(
                e->{
                    contentPanel.remove(currentPanel);
                    currentPanel= StatsPanel.init();
                    contentPanel.add(currentPanel);
                    parentJPanel.validate();
                    parentJPanel.repaint();// 重绘内容面板
                    statsBtn.setBackground(Color.ORANGE);
                    rootBtn.setBackground(null);
                    userBtn.setBackground(null);
                    commodityBtn.setBackground(null);
                    inventoryBtn.setBackground(null);
                    purchaseBtn.setBackground(null);
                }
        );
        mainFrame.setVisible(true);
    }
}


