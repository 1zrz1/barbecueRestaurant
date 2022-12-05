package lanqiao.newUi;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * @Author 李冰冰
 * @Date 2022/12/04
 * @Version 17.0.5
 */

// 库存管理面板
public class InventoryPanel {
    public static JPanel init(){
        JPanel inventoryPanel=new JPanel();
        Color color1=new Color(51, 161, 201);
        inventoryPanel.setBackground(color1);
        Font ChineseFont = new Font("宋体", Font.BOLD, 16);
        inventoryPanel.setBounds(0,0,860,690);
        inventoryPanel.setLayout(null);
        JLabel usernameLable =new JLabel("搜索:");
        JTextField usernameField= new JTextField();

        JButton button1= new JButton();
        JButton button2= new JButton();
        JButton button3= new JButton();

        button1.setText("text");
        inventoryPanel.add(button1);
        button1.setBounds(770, 30, 80, 25);
        button2.setText("text");
        inventoryPanel.add(button2);
        button2.setBounds(680, 30, 80, 25);
        button3.setText("text");
        inventoryPanel.add(button3);
        button3.setBounds(590, 30, 80, 25);

        usernameLable.setBounds(250, 610, 50, 25);
        usernameLable.setFont(ChineseFont);
        inventoryPanel.add(usernameLable);

        usernameField.setBounds(300,610,200,25);
        inventoryPanel.add(usernameField);

        JLabel jLabel=new JLabel();
        jLabel.setBounds(15,30,100,25);
        jLabel.setText("库存管理界面");
        jLabel.setFont(ChineseFont);
        inventoryPanel.add(jLabel);
        String head[] = {"id", "用户名", "密码"};
        Object[][] data = new Object[5][3];

        JTable jTable = new JTable(data, head);
        jTable.getTableHeader().setPreferredSize(new Dimension(1, 30));

        // 滚动面板
        JScrollPane jscrollpane = new JScrollPane();
        jscrollpane.setBounds(0, 80, 860, 500);
        jscrollpane.setViewportView(jTable);// 把表格添加到滚动面板中
        jTable.setRowHeight(20);// 行高

        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        jTable.setDefaultRenderer(Object.class, r);// 每行内容居中显示

        inventoryPanel.add(jscrollpane);
        return inventoryPanel;
    }
}
