package lanqiao.ui.impl;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import lanqiao.bean.Commodity;
import lanqiao.connection.Conn;
import lanqiao.service.CommodityService;
import lanqiao.service.impl.CommodityServiceImpl;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author 李冰冰
 * @Date 2022/12/02
 * @Version 17.0.5
 * 点餐界面
 */

public class OrderUiImpl extends JFrame implements CommodityService{
    CommodityService commodityService = new CommodityServiceImpl();
    JPanel currentcenterPanel;
    JLabel []SkewersLabels;
    JLabel []DrinkLabels;
    JLabel []staplefoodLabels;
    int currentSkewersLabelsI=-1;
    int currentDrinkLabelsI=-1;
    int currentstaplefoodI=-1;
    int DrinkNumber,SkewersNumber,staplefoodNumber;
    Object [][]DrinksData,staplefoodData,SkerwersData;
    DefaultTableModel tableModel;//表格模型
    JTable jTable;
    JLabel totalPriceLable;// 存放价格的标签
    int dinersNumber;//用餐人数
    JTextField numberField;//存放用餐人数
    // 测试运行
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                OrderUiImpl oui=new OrderUiImpl();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    String getPicAdress(String objectName){
        //读配置文件
        InputStream inputStream = Conn.class.getClassLoader().getResourceAsStream("config.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
        String accessKeyId = properties.getProperty("accessKeyId");
        String accessKeySecret = properties.getProperty("accessKeySecret");
        String bucketName =properties.getProperty("bucketName");
        String endpoint = properties.getProperty("endpoint");
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 指定签名URL过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 100 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.GET);
        req.setExpiration(expiration);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        return signedUrl.toString();
    };

    public int getDrinkNumber() throws SQLException{
        return commodityService.getSortNumber("饮品");
    }
    public int getstaplefoodNumber() throws SQLException{
        return commodityService.getSortNumber("主食");
    }
    public int getSkewersumber() throws SQLException{
        return commodityService.getSortNumber("烤串");
    }
    public Object[][] getDrinksData() throws SQLException{
        return commodityService.getSortpicAdress("饮品");
    }
    public Object[][] getstaplefoodData() throws SQLException{
        return commodityService.getSortpicAdress("主食");
    }
    public Object[][] getSkerwersData() throws SQLException{
        return commodityService.getSortpicAdress("烤串");
    }

    public OrderUiImpl() throws SQLException{
        OrderFood();
    }

    public void OrderFood() throws SQLException{
        /* 设置标题和图标 */
        setTitle("点餐");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1305,680));
        setLocationRelativeTo(null);
        setVisible(true);

        /* 左中右下面板 BorderLayout布局 */
        Container parentJPanel = getContentPane();
        parentJPanel.setLayout(new BorderLayout(10, 10));
        JPanel rightPanel = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        JScrollPane jscrollpane= new JScrollPane();
        parentJPanel.add(leftPanel, BorderLayout.WEST);
        parentJPanel.add(bottomPanel, BorderLayout.SOUTH);
        parentJPanel.add(jscrollpane, BorderLayout.CENTER);
        parentJPanel.add(rightPanel, BorderLayout.EAST);

        /*
            类别导航面板 左边 GridLayout布局
         */
        leftPanel.setLayout(new GridLayout(10, 1, 5, 5));
        leftPanel.setPreferredSize(new Dimension(100, 150));
        JButton skewersBtn = new JButton("烤串");
        JButton stapleFoodBtn = new JButton("主食");
        JButton drinkBtn = new JButton("饮品");
        drinkBtn.setBounds(new Rectangle(100, 50));
        leftPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.CYAN, 3), "菜单导航",
                TitledBorder.CENTER, TitledBorder.TOP, new Font("微软雅黑", Font.BOLD, 15)));
        leftPanel.add(skewersBtn);
        leftPanel.add(stapleFoodBtn);
        leftPanel.add(drinkBtn);

        /*
          菜式面板 中间 FlowLayout布局
        */
        currentcenterPanel=new JPanel(new BorderLayout());
        currentcenterPanel.setSize(400,400);
        JLabel lable1=new JLabel("Welcome to the Barbecue Restaurant");
        JLabel lable2=new JLabel("Please click on the menu navigation if you want to order");
        lable1.setFont(new Font("微软雅黑",Font.BOLD,30));
        lable2.setFont(new Font("微软雅黑",Font.BOLD,20));
        currentcenterPanel.setBackground(Color.CYAN);
        currentcenterPanel.add(lable1,BorderLayout.CENTER);
        currentcenterPanel.add(lable2,BorderLayout.SOUTH);

        currentcenterPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 5));
        jscrollpane.setViewportView(currentcenterPanel);
        jscrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);  //设置垂直滚动条在窗格上
        jscrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // 不设置水平滚动条

        /* 给菜式导航上的按钮绑定事件 */
        AtomicInteger currentNumber= new AtomicInteger(); // 当前图片数量
        // 烤串按钮
        skewersBtn.addActionListener(
                e->{
                    try {
                        currentcenterPanel=getSkewersPanel();
                        jscrollpane.setViewportView(currentcenterPanel);
                        int jscrollpaneWidth=jscrollpane.getWidth();// 获取0当前jscrollpane的宽度
                        int imgHeight=230;
                        currentNumber.set(getSkewersumber());
                        int panelHeight=setCenterPanelHeight(jscrollpaneWidth,currentNumber.get(),imgHeight);
                        currentcenterPanel.setPreferredSize(new Dimension(jscrollpane.getWidth(), panelHeight));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
        );
        // 主食按钮
        stapleFoodBtn.addActionListener(
                e->{
                    try {
                        currentcenterPanel=getstaplefoodPanel();
                        jscrollpane.setViewportView(currentcenterPanel);
                        int jscrollpaneWidth=jscrollpane.getWidth();// 获取当前jscrollpane的宽度
                        int imgHeight=230;
                        currentNumber.set(getstaplefoodNumber());
                        int panelHeight=setCenterPanelHeight(jscrollpaneWidth,currentNumber.get(),imgHeight);
                        currentcenterPanel.setPreferredSize(new Dimension(jscrollpane.getWidth(), panelHeight));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
        );
        // 饮品按钮
        drinkBtn.addActionListener(
                e-> {
                    try {
                        currentcenterPanel = getDrinkPanel();
                        jscrollpane.setViewportView(currentcenterPanel);
                        int jscrollpaneWidth=jscrollpane.getWidth();// 获取当前jscrollpane的宽度
                        int imgHeight=230;
                        currentNumber.set(getDrinkNumber());
                        int panelHeight=setCenterPanelHeight(jscrollpaneWidth,currentNumber.get(),imgHeight);
                        currentcenterPanel.setPreferredSize(new Dimension(jscrollpane.getWidth(), panelHeight));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                });

        /* 窗口大小改变 设置监听事件 */
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int jscrollpaneWidth=jscrollpane.getWidth();// 获取当前jscrollpane的宽度
                int imgHeight=230;
                int panelHeight=setCenterPanelHeight(jscrollpaneWidth,currentNumber.get(),imgHeight);
                currentcenterPanel.setPreferredSize(new Dimension(jscrollpane.getWidth(), panelHeight));
            }
        });

        /*
            右边面板 BorderLayout布局
        */
        rightPanel.setPreferredSize(new Dimension(300, 700));
        rightPanel.setLayout(new BorderLayout(0,2));
        JPanel panel1=new JPanel();// 放 选餐桌和人数 的面板
        rightPanel.add(panel1,BorderLayout.NORTH);
        JPanel panel2=new JPanel();// 已点菜式面板 的面板
        rightPanel.add(panel2,BorderLayout.CENTER);

        /* 选餐桌和人数的面板 GridLayout 布局 */
        panel1.setPreferredSize(new Dimension(300, 100));
        panel1.setLayout(new GridLayout(2,2));
        // 桌号和下拉框
        JLabel tableNoLable=new JLabel("请选择桌号：");
        JComboBox comboBox = new JComboBox();// 下拉框
        String[] tableNos = { "1号桌", "2号桌", "3号桌" };
        for (String item : tableNos){
            comboBox.addItem(item);
        }
        panel1.add(tableNoLable);
        panel1.add(comboBox);
        // 用餐人数和输入框
        JLabel numberLable=new JLabel("请输入用餐人数: ");
        numberField=new JTextField();
        panel1.add(numberLable);
        panel1.add(numberField);

        /* 已点菜式面板  BorderLayout布局 */
        panel2.setLayout(new BorderLayout(0,2));
        panel2.setMinimumSize(new Dimension(300, 400));
        // 已点菜单列表
        JLabel listLable=new JLabel("已点菜式列表: ");
        listLable.setMinimumSize(new Dimension(300,50));
        panel2.add(listLable,BorderLayout.NORTH);
        // 菜式表格
        String head[] = {"菜名", "单价", "数量"};
        Object[][] data = new Object[0][0];
        tableModel = new DefaultTableModel(data, head);
        JScrollPane jscrollPaneList = new JScrollPane(); // 滚动面板
        jTable=new JTable(tableModel);
        jscrollPaneList.setViewportView(jTable);
        jscrollPaneList.setMinimumSize(new Dimension(300,200));
        panel2.add(jscrollPaneList,BorderLayout.CENTER);

        /* 表格内容修改监听 */
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                totalPriceLable.setText("合计 : "+getTotalPrice()+"元");//更新合计价格
            }
        });


        /* 右面板的删除按钮*/
        JPanel panel3=new JPanel(new GridLayout(1,2));
        JButton deleteBtn=new JButton("删除");
        panel3.add(deleteBtn);
        double totalPrice=getTotalPrice();
        totalPriceLable=new JLabel("合计 : "+totalPrice+"元",JLabel.CENTER);
        panel3.add(totalPriceLable);
        rightPanel.add(panel3,BorderLayout.SOUTH);
        deleteBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tableModel.removeRow(jTable.getSelectedRow());//删除当前行
                    totalPriceLable.setText("合计 : "+getTotalPrice()+"元");
                } catch (ArrayIndexOutOfBoundsException e1){
                    JOptionPane.showMessageDialog(null, "请选择一行数据后再删除", "警告", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /* 删除按钮绑定事件 */
        /* 底部按钮 */
        String []btnName={"取消","下单","结账"};
        JButton bss[] = new JButton[3];
        for (int i = 0; i <4; i++) {
            bss[i]=new JButton(btnName[i]);
            bottomPanel.add(bss[i]);
        }
        //下单按钮绑定事件
        bss[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(numberField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "请输入用餐人数！", "警告", JOptionPane.ERROR_MESSAGE);
                }else if(jTable.getRowCount()==0){
                    JOptionPane.showMessageDialog(null, "您当前未点餐，请点餐后再下单", "警告", JOptionPane.ERROR_MESSAGE);
                } else{
                    JOptionPane.showMessageDialog(null, "下单成功！请耐心等待...", "提醒", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

    }

    /* 获取总价格 */
    public double getTotalPrice(){
        int row = jTable.getRowCount();       // 表格行数
        double totalPrice=0;
        // value数组存放表格中的所有数据
        Object[][] value = new Object[row][2];
        for(int i = 0; i < row; i++){
            double price = (double) jTable.getValueAt(i, 1);
            int number=Integer.parseInt(jTable.getValueAt(i,2).toString());
            totalPrice=totalPrice+price*number;
        }
        return totalPrice;
    }

    /*   获取 烤串面板 */
    public JPanel getSkewersPanel() throws SQLException {
        int SkewersNumber=getSkewersumber();
        Object [][]SkewersData=getSkerwersData();
        JPanel SkewersPanel=new JPanel();
        SkewersLabels=new JLabel[SkewersNumber];
        for (int i = 0; i < SkewersNumber; i++) {
            JLabel iconType = new JLabel();
            SkewersLabels[i]=iconType;
            int finalI = i;
            String dishName= (String) SkewersData[i][0];
            double price= (double) SkewersData[i][1];
            String picAdress= (String) SkewersData[i][2];
            iconType.setBorder (BorderFactory.createTitledBorder (
                    BorderFactory.createLineBorder (Color.CYAN,2),dishName+":"+price+"元",
                    TitledBorder.CENTER,TitledBorder.TOP,new Font("微软雅黑", Font.BOLD, 15)));

            ImageIcon image= null;
            try {
                image = new ImageIcon(new URL(getPicAdress(picAdress)));
                image.setImage(image.getImage().getScaledInstance(200,200,Image.SCALE_DEFAULT));
                iconType.setIcon(image);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            SkewersPanel.add(iconType);
            Object[] selectedArr={dishName,price,1};
            // 添加图片标签的监听
            SkewersLabels[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println(finalI);
                    currentSkewersLabelsI=finalI;
                    tableModel.addRow(selectedArr);
                    totalPriceLable.setText("合计 : "+getTotalPrice()+"元");//更新合计价格
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(new Cursor(12));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(new Cursor(0));
                }
            });
        }
        return SkewersPanel;
    }


    /* 获取饮品面板 */
    public JPanel getDrinkPanel() throws SQLException{
        JPanel SkewersPanel=new JPanel();
        int DrinkNumber=getDrinkNumber();
        Object [][]DrinksData=getDrinksData();
        DrinkLabels=new JLabel[DrinkNumber];
        for (int i = 0; i <DrinkNumber; i++) {
            JLabel iconType = new JLabel();
            DrinkLabels[i]=iconType;
            String dishName= (String) DrinksData[i][0];
            double price= (double) DrinksData[i][1];
            String picAdress= (String) DrinksData[i][2];
            iconType.setBorder (BorderFactory.createTitledBorder (
                    BorderFactory.createLineBorder (Color.CYAN,2),dishName+":"+price+"元",
                    TitledBorder.CENTER,TitledBorder.TOP,new Font("微软雅黑", Font.BOLD, 15)));
            ImageIcon image= null;
            try {
                image = new ImageIcon(new URL(getPicAdress(picAdress)));
                image.setImage(image.getImage().getScaledInstance(200,200,Image.SCALE_DEFAULT));
                iconType.setIcon(image);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            SkewersPanel.add(iconType);
            // 添加图片标签的监听
            int finalI=i;
            Object[] selectedArr={dishName,price,1};
            DrinkLabels[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println(finalI);
                    currentDrinkLabelsI=finalI;
                    tableModel.addRow(selectedArr);
                    totalPriceLable.setText("合计 : "+getTotalPrice()+"元");//更新合计价格
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(new Cursor(12));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(new Cursor(0));
                }
            });
        }
        return SkewersPanel;
    }

    /* 获取主食面板 */
    public JPanel getstaplefoodPanel() throws SQLException{
        JPanel staplefoodPanel=new JPanel();
        int staplefoodNumber=getstaplefoodNumber();
        Object [][]staplefoodData=getstaplefoodData();
        staplefoodLabels=new JLabel[staplefoodNumber];
        for (int i = 1; i <staplefoodNumber; i++) {
            JLabel iconType = new JLabel();
            staplefoodLabels[i]=iconType;
            String dishName= (String) staplefoodData[i][0];
            double price= (double) staplefoodData[i][1];
            String picAdress= (String) staplefoodData[i][2];
            iconType.setBorder (BorderFactory.createTitledBorder (
                    BorderFactory.createLineBorder (Color.CYAN,2),dishName+":"+price+"元",
                    TitledBorder.CENTER,TitledBorder.TOP,new Font("微软雅黑", Font.BOLD, 15)));
            ImageIcon image= null;
            try {
                image = new ImageIcon(new URL(getPicAdress(picAdress)));
                image.setImage(image.getImage().getScaledInstance(200,200,Image.SCALE_DEFAULT));
                iconType.setIcon(image);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            staplefoodPanel.add(iconType);
            // 添加图片标签的监听
            int finalI=i;
            Object[] selectedArr={dishName,price,1};
            staplefoodLabels[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println(finalI);
                    currentstaplefoodI=finalI;
                    tableModel.addRow(selectedArr);
                    totalPriceLable.setText("合计 : "+getTotalPrice()+"元"); // 更新合计价格
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(new Cursor(12));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(new Cursor(0));
                }
            });
        }
        return staplefoodPanel;
    }


    /*
        setCenterPanelHeight 设置中间面板的高度：能够根据图片数量，设置滑动面板的高度
            jscrollpaneWidth 中间菜式滚动面板 当前的宽度
            imgNumber 菜的图片数量
            imageHeight 每张图片的高度
    */
    public static int setCenterPanelHeight(int jscrollpaneWidth,int imgNumber,int imageHeight){
        int basicValue=640;
        int basicRowsNumber=2;
        int rowsNumber=basicRowsNumber;// 每行存放的图片数量
        int addedValue=5;// 递增值
        if(jscrollpaneWidth>=basicValue){
            int testIceil= (int) Math.ceil((double)(jscrollpaneWidth-basicValue)/210); // 取上整数
            int testIfloor= (int) Math.floor((double)(jscrollpaneWidth-basicValue)/210); // 取下整
            rowsNumber=testIceil+basicRowsNumber;
            if((jscrollpaneWidth-basicValue)-210*testIfloor<testIceil*addedValue){ // 特殊处理
                rowsNumber=rowsNumber-1;
            }
        }
        int columnNumber=imgNumber/rowsNumber;// 每列存放的图片数量
        if(imgNumber%rowsNumber!=0){ // 若图片数不是rowsNumber的倍数，先将其设为rowsNumber的倍数，再计算columnNumber
            columnNumber=(int) Math.ceil((imgNumber+rowsNumber-imgNumber%rowsNumber)/rowsNumber);
        }
        int panelHeight=imageHeight*columnNumber; // 最终要设置的面板高度
        return panelHeight+50;
    }

    @Override
    public List<Commodity> getAllCommodity() throws SQLException {
        return null;
    }

    @Override
    public int getSortNumber(String sortName) throws SQLException {
        return 0;
    }

    @Override
    public Object[][] getSortpicAdress(String sortName) throws SQLException {
        return new Object[0][];
    }
}
