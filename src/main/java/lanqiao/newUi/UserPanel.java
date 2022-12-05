package lanqiao.newUi;

import lanqiao.bean.Users;
import lanqiao.service.UserService;
import lanqiao.service.impl.UserServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.security.Provider;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author 李冰冰
 * @Date 2022/12/04
 * @Version 17.0.5
 */

public class UserPanel extends JPanel {
     int k;
     DefaultTableModel model;
    // 用户管理面板
       JPanel init() throws SQLException, IOException {
        JPanel UserPanel=new JPanel();
        Color color1=new Color(51, 161, 201);
        Font ChineseFont = new Font("宋体", Font.BOLD, 16);
        UserPanel.setBackground(color1);
        UserPanel.setBounds(0,0,860,690);
        UserPanel.setLayout(null);



        JLabel usernameLable =new JLabel("搜索:");
        JTextField usernameField= new JTextField();

        JButton button1= new JButton();
        JButton button2= new JButton();
        JButton button3= new JButton();
        JButton button4= new JButton();
        JButton button5= new JButton();

        button1.setText("保存");
        UserPanel.add(button1);
        button1.setBounds(770, 30, 80, 25);

        button2.setText("编辑");
        UserPanel.add(button2);
        button2.setBounds(680, 30, 80, 25);
        button2.addActionListener(
                e -> {
                    k = 7;
                }
        );

        button3.setText("删除一行");
        UserPanel.add(button3);
        button3.setBounds(590, 30, 80, 25);

        button4.setText("增加一行");
        UserPanel.add(button4);
        button4.setBounds(500, 30, 80, 25);
        button4.addActionListener(
                e -> {
                   model.addRow(new Object[1]);
                }
        );

           button5.setText("刷新");
           UserPanel.add(button5);
           button5.setBounds(410, 30, 80, 25);




        usernameLable.setBounds(250, 610, 50, 25);
        usernameLable.setFont(ChineseFont);
        UserPanel.add(usernameLable);

        usernameField.setBounds(300,610,200,25);
        UserPanel.add(usernameField);

        JLabel jLabel=new JLabel();
        jLabel.setBounds(15,30,100,25);
        jLabel.setText("用户管理界面");
        jLabel.setFont(ChineseFont);
        UserPanel.add(jLabel);

        UserService userService =new UserServiceImpl();
        List<Users> usersList=userService.getAllUser();

        String[][] data  = new String[usersList.size()][3];
        //把集合里的数据放入Obejct这个二维数组
        for (int i = 0; i < usersList.size(); i++) {
            for (int j = 0; j < 3; j++) {
                data[i][0] = String.valueOf(usersList.get(i).getId());
                data[i][1] = usersList.get(i).getUsername();
                data[i][2] = usersList.get(i).getPassword();
            }
        }
        String head[] = {"id", "用户名", "密码"};

        model = new DefaultTableModel(data,head){
            public boolean isCellEditable(int row, int column) {
                if (k == 7) {
                    return true;
                }
                return false;
            }
        };
        JTable jTable = new JTable(model) ;
        jTable.getTableHeader().setPreferredSize(new Dimension(1, 30));

        // 滚动面板
        JScrollPane jscrollpane = new JScrollPane();
        jscrollpane.setBounds(0, 80, 860, 500);
        jscrollpane.setViewportView(jTable);// 把表格添加到滚动面板中
        jTable.setRowHeight(20);// 行高

        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        jTable.setDefaultRenderer(Object.class, r);// 每行内容居中显示

        UserPanel.add(jscrollpane);

        button3.addActionListener(
                e -> {

                    //             JOptionPane.showMessageDialog(null,"你确定要删除吗","警告",JOptionPane.ERROR_MESSAGE);
                    try{
                        model.removeRow(jTable.getSelectedRow());
                    }catch (Exception w){
                        JOptionPane.showMessageDialog(null,"请选中一行");
                    }

                }
        );

        button1.addActionListener(
                e -> {
                    try {
                    int rows = jTable.getRowCount();
                    int clo = jTable.getColumnCount();
                        //  System.out.println(rows);
                        // System.out.println(clo);
                    String b[][]=new String[rows][clo];
                    int l=0;
                    int k=0;
                    for(l=0;l<rows;l++){
                        b[l][0]=(String)model.getValueAt(l, 0);
                        b[l][1]=(String)model.getValueAt(l, 1);
                        b[l][2]=(String)model.getValueAt(l, 2);
                    }
                    /*
                    for(l=0;l<b.length;l++){
                        for(k=0;k<b[l].length;k++){
                            System.out.print("\t"+b[l][k]);
                        }
                        System.out.print("\n");
                    }
                     */
                        for (l = 0; l < b.length; l++) {
                            if (b[l][0] == null||b[l][2]==null) {
                               throw new Exception();
                            }
                        }
                    try {
                        userService.login("DELETE from users");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    String v[]=new String[clo];
                    int o=0;
                    try {
                        userService.pstmt(o,b,v);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                        JOptionPane.showMessageDialog(null,"保存成功");
                }catch(Exception ex){
                        JOptionPane.showMessageDialog(null,"请输入新id或新密码");
                        // System.exit(0);
                    }

                }
        );

           button5.addActionListener(
                   e -> {
                       button1.setEnabled(true);
                       button2.setEnabled(true);
                       button3.setEnabled(true);
                       button4.setEnabled(true);
                       usernameField.setText("");
                       int rows = jTable.getRowCount();
                  //     System.out.println("行数"+rows);
                       for (int i = 0; i < rows; i++) {
                           model.removeRow(0);
                       }
                       UserService userService1 =new UserServiceImpl();
                       String[][] data1=null;
                       try {
                           List<Users> usersList1=userService1.getAllUser();
                           data1  = new String[usersList1.size()][3];
                           for (int i = 0; i < usersList1.size(); i++) {
                               for (int j = 0; j < 3; j++) {
                                   data1[i][0] = String.valueOf(usersList1.get(i).getId());
                                   data1[i][1] = usersList1.get(i).getUsername();
                                   data1[i][2] = usersList1.get(i).getPassword();
                               }
                           }
                           /*
                           for (int l = 0; l < data1.length; l++) {
                               for (k = 0; k < data1[l].length; k++) {
                                   System.out.print("\t" + data1[l][k]);

                               }
                               System.out.print("\n");
                           }

                            */

                       } catch (SQLException ex) {
                           ex.printStackTrace();
                       } catch (IOException ex) {
                           ex.printStackTrace();
                       }
                       //把集合里的数据放入Obejct这个二维数组
                       for (int i = 0; i < data1.length ;i++) {
                           model.addRow(data1[i]);
                       }

                       JOptionPane.showMessageDialog(null,"刷新成功");
                   }
           );


           usernameField.addActionListener(
                   e -> {

                       String user= usernameField.getText();
                       String sql2 = "SELECT * FROM users where username='"+user+"'";
                       int rows = jTable.getRowCount();
                   //    System.out.println("行数"+rows);
                       for (int i = 0; i < rows; i++) {
                           model.removeRow(0);
                       }
                       UserService userService1 =new UserServiceImpl();
                       String[][] data2=null;
                       try {
                           List<Users> usersList2=userService1.getUser(sql2);
                           data2  = new String[usersList2.size()][3];
                           for (int i = 0; i < usersList2.size(); i++) {
                               for (int j = 0; j < 3; j++) {
                                   data2[i][0] = String.valueOf(usersList2.get(i).getId());
                                   data2[i][1] = usersList2.get(i).getUsername();
                                   data2[i][2] = usersList2.get(i).getPassword();
                               }
                           }
                           /*
                           for (int l = 0; l < data2.length; l++) {
                               for (k = 0; k < data2[l].length; k++) {
                                   System.out.print("\t" + data2[l][k]);

                               }
                               System.out.print("\n");
                           }
                           System.out.println(data2);

                            */

                       } catch (SQLException ex) {
                           ex.printStackTrace();
                       }
                       //把集合里的数据放入Obejct这个二维数组
                       for (int i = 0; i < data2.length ;i++) {
                           model.addRow(data2[i]);
                       }
                        button1.setEnabled(false);
                        button2.setEnabled(false);
                        button3.setEnabled(false);
                        button4.setEnabled(false);


                   }
           );


        return UserPanel;
    }
}
