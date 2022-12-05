package org.example;

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
 * @Author �����
 * @Date 2022/12/02
 * @Version 17.0.5
 * ��ͽ���
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
    DefaultTableModel tableModel;//���ģ��
    JTable jTable;
    JLabel totalPriceLable;// ��ż۸�ı�ǩ
    int dinersNumber;//�ò�����
    JTextField numberField;//����ò�����
    // ��������
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
        //�������ļ�
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
        // ָ��ǩ��URL����ʱ��Ϊ10���ӡ�
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 100 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.GET);
        req.setExpiration(expiration);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        return signedUrl.toString();
    };

    public int getDrinkNumber() throws SQLException{
        return commodityService.getSortNumber("��Ʒ");
    }
    public int getstaplefoodNumber() throws SQLException{
        return commodityService.getSortNumber("��ʳ");
    }
    public int getSkewersumber() throws SQLException{
        return commodityService.getSortNumber("����");
    }
    public Object[][] getDrinksData() throws SQLException{
        return commodityService.getSortpicAdress("��Ʒ");
    }
    public Object[][] getstaplefoodData() throws SQLException{
        return commodityService.getSortpicAdress("��ʳ");
    }
    public Object[][] getSkerwersData() throws SQLException{
        return commodityService.getSortpicAdress("����");
    }

    public OrderUiImpl() throws SQLException{
        OrderFood();
    }

    public void OrderFood() throws SQLException{
        /* ���ñ����ͼ�� */
        setTitle("���");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1305,680));
        setLocationRelativeTo(null);
        setVisible(true);

        /* ����������� BorderLayout���� */
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
            ��𵼺���� ��� GridLayout����
         */
        leftPanel.setLayout(new GridLayout(10, 1, 5, 5));
        leftPanel.setPreferredSize(new Dimension(100, 150));
        JButton skewersBtn = new JButton("����");
        JButton stapleFoodBtn = new JButton("��ʳ");
        JButton drinkBtn = new JButton("��Ʒ");
        drinkBtn.setBounds(new Rectangle(100, 50));
        leftPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.CYAN, 3), "�˵�����",
                TitledBorder.CENTER, TitledBorder.TOP, new Font("΢���ź�", Font.BOLD, 15)));
        leftPanel.add(skewersBtn);
        leftPanel.add(stapleFoodBtn);
        leftPanel.add(drinkBtn);

        /*
          ��ʽ��� �м� FlowLayout����
        */
        currentcenterPanel=new JPanel(new BorderLayout());
        currentcenterPanel.setSize(400,400);
        JLabel lable1=new JLabel("Welcome to the Barbecue Restaurant");
        JLabel lable2=new JLabel("Please click on the menu navigation if you want to order");
        lable1.setFont(new Font("΢���ź�",Font.BOLD,30));
        lable2.setFont(new Font("΢���ź�",Font.BOLD,20));
        currentcenterPanel.setBackground(Color.CYAN);
        currentcenterPanel.add(lable1,BorderLayout.CENTER);
        currentcenterPanel.add(lable2,BorderLayout.SOUTH);

        currentcenterPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 5));
        jscrollpane.setViewportView(currentcenterPanel);
        jscrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);  //���ô�ֱ�������ڴ�����
        jscrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // ������ˮƽ������

        /* ����ʽ�����ϵİ�ť���¼� */
        AtomicInteger currentNumber= new AtomicInteger(); // ��ǰͼƬ����
        // ������ť
        skewersBtn.addActionListener(
                e->{
                    try {
                        currentcenterPanel=getSkewersPanel();
                        jscrollpane.setViewportView(currentcenterPanel);
                        int jscrollpaneWidth=jscrollpane.getWidth();// ��ȡ0��ǰjscrollpane�Ŀ��
                        int imgHeight=230;
                        currentNumber.set(getSkewersumber());
                        int panelHeight=setCenterPanelHeight(jscrollpaneWidth,currentNumber.get(),imgHeight);
                        currentcenterPanel.setPreferredSize(new Dimension(jscrollpane.getWidth(), panelHeight));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
        );
        // ��ʳ��ť
        stapleFoodBtn.addActionListener(
                e->{
                    try {
                        currentcenterPanel=getstaplefoodPanel();
                        jscrollpane.setViewportView(currentcenterPanel);
                        int jscrollpaneWidth=jscrollpane.getWidth();// ��ȡ��ǰjscrollpane�Ŀ��
                        int imgHeight=230;
                        currentNumber.set(getstaplefoodNumber());
                        int panelHeight=setCenterPanelHeight(jscrollpaneWidth,currentNumber.get(),imgHeight);
                        currentcenterPanel.setPreferredSize(new Dimension(jscrollpane.getWidth(), panelHeight));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
        );
        // ��Ʒ��ť
        drinkBtn.addActionListener(
                e-> {
                    try {
                        currentcenterPanel = getDrinkPanel();
                        jscrollpane.setViewportView(currentcenterPanel);
                        int jscrollpaneWidth=jscrollpane.getWidth();// ��ȡ��ǰjscrollpane�Ŀ��
                        int imgHeight=230;
                        currentNumber.set(getDrinkNumber());
                        int panelHeight=setCenterPanelHeight(jscrollpaneWidth,currentNumber.get(),imgHeight);
                        currentcenterPanel.setPreferredSize(new Dimension(jscrollpane.getWidth(), panelHeight));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                });

        /* ���ڴ�С�ı� ���ü����¼� */
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int jscrollpaneWidth=jscrollpane.getWidth();// ��ȡ��ǰjscrollpane�Ŀ��
                int imgHeight=230;
                int panelHeight=setCenterPanelHeight(jscrollpaneWidth,currentNumber.get(),imgHeight);
                currentcenterPanel.setPreferredSize(new Dimension(jscrollpane.getWidth(), panelHeight));
            }
        });

        /*
            �ұ���� BorderLayout����
        */
        rightPanel.setPreferredSize(new Dimension(300, 700));
        rightPanel.setLayout(new BorderLayout(0,2));
        JPanel panel1=new JPanel();// �� ѡ���������� �����
        rightPanel.add(panel1,BorderLayout.NORTH);
        JPanel panel2=new JPanel();// �ѵ��ʽ��� �����
        rightPanel.add(panel2,BorderLayout.CENTER);

        /* ѡ��������������� GridLayout ���� */
        panel1.setPreferredSize(new Dimension(300, 100));
        panel1.setLayout(new GridLayout(2,2));
        // ���ź�������
        JLabel tableNoLable=new JLabel("��ѡ�����ţ�");
        JComboBox comboBox = new JComboBox();// ������
        String[] tableNos = { "1����", "2����", "3����" };
        for (String item : tableNos){
            comboBox.addItem(item);
        }
        panel1.add(tableNoLable);
        panel1.add(comboBox);
        // �ò������������
        JLabel numberLable=new JLabel("�������ò�����: ");
        numberField=new JTextField();
        panel1.add(numberLable);
        panel1.add(numberField);

        /* �ѵ��ʽ���  BorderLayout���� */
        panel2.setLayout(new BorderLayout(0,2));
        panel2.setMinimumSize(new Dimension(300, 400));
        // �ѵ�˵��б�
        JLabel listLable=new JLabel("�ѵ��ʽ�б�: ");
        listLable.setMinimumSize(new Dimension(300,50));
        panel2.add(listLable,BorderLayout.NORTH);
        // ��ʽ���
        String head[] = {"����", "����", "����"};
        Object[][] data = new Object[0][0];
        tableModel = new DefaultTableModel(data, head);
        JScrollPane jscrollPaneList = new JScrollPane(); // �������
        jTable=new JTable(tableModel);
        jscrollPaneList.setViewportView(jTable);
        jscrollPaneList.setMinimumSize(new Dimension(300,200));
        panel2.add(jscrollPaneList,BorderLayout.CENTER);

        /* ��������޸ļ��� */
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                totalPriceLable.setText("�ϼ� : "+getTotalPrice()+"Ԫ");//���ºϼƼ۸�
            }
        });


        /* ������ɾ����ť*/
        JPanel panel3=new JPanel(new GridLayout(1,2));
        JButton deleteBtn=new JButton("ɾ��");
        panel3.add(deleteBtn);
        double totalPrice=getTotalPrice();
        totalPriceLable=new JLabel("�ϼ� : "+totalPrice+"Ԫ",JLabel.CENTER);
        panel3.add(totalPriceLable);
        rightPanel.add(panel3,BorderLayout.SOUTH);
        deleteBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tableModel.removeRow(jTable.getSelectedRow());//ɾ����ǰ��
                    totalPriceLable.setText("�ϼ� : "+getTotalPrice()+"Ԫ");
                } catch (ArrayIndexOutOfBoundsException e1){
                    JOptionPane.showMessageDialog(null, "��ѡ��һ�����ݺ���ɾ��", "����", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /* ɾ����ť���¼� */
        /* �ײ���ť */
        String []btnName={"ȡ��","�µ�","�Ӳ�","����"};
        JButton bss[] = new JButton[4];
        for (int i = 0; i <4; i++) {
            bss[i]=new JButton(btnName[i]);
            bottomPanel.add(bss[i]);
        }
        //�µ���ť���¼�
        bss[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(numberField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "�������ò�������", "����", JOptionPane.ERROR_MESSAGE);
                }else if(jTable.getRowCount()==0){
                    JOptionPane.showMessageDialog(null, "����ǰδ��ͣ����ͺ����µ�", "����", JOptionPane.ERROR_MESSAGE);
                } else{
                    JOptionPane.showMessageDialog(null, "�µ��ɹ��������ĵȴ�...", "����", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

    }

    /* ��ȡ�ܼ۸� */
    public double getTotalPrice(){
        int row = jTable.getRowCount();       // �������
        double totalPrice=0;
        // value�����ű���е���������
        Object[][] value = new Object[row][2];
        for(int i = 0; i < row; i++){
            double price = (double) jTable.getValueAt(i, 1);
            int number=Integer.parseInt(jTable.getValueAt(i,2).toString());
            totalPrice=totalPrice+price*number;
        }
        return totalPrice;
    }

    /*   ��ȡ ������� */
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
                    BorderFactory.createLineBorder (Color.CYAN,2),dishName+":"+price+"Ԫ",
                    TitledBorder.CENTER,TitledBorder.TOP,new Font("΢���ź�", Font.BOLD, 15)));

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
            // ���ͼƬ��ǩ�ļ���
            SkewersLabels[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println(finalI);
                    currentSkewersLabelsI=finalI;
                    tableModel.addRow(selectedArr);
                    totalPriceLable.setText("�ϼ� : "+getTotalPrice()+"Ԫ");//���ºϼƼ۸�
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


    /* ��ȡ��Ʒ��� */
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
                    BorderFactory.createLineBorder (Color.CYAN,2),dishName+":"+price+"Ԫ",
                    TitledBorder.CENTER,TitledBorder.TOP,new Font("΢���ź�", Font.BOLD, 15)));
            ImageIcon image= null;
            try {
                image = new ImageIcon(new URL(getPicAdress(picAdress)));
                image.setImage(image.getImage().getScaledInstance(200,200,Image.SCALE_DEFAULT));
                iconType.setIcon(image);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            SkewersPanel.add(iconType);
            // ���ͼƬ��ǩ�ļ���
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
                    totalPriceLable.setText("�ϼ� : "+getTotalPrice()+"Ԫ");//���ºϼƼ۸�
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

    /* ��ȡ��ʳ��� */
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
                    BorderFactory.createLineBorder (Color.CYAN,2),dishName+":"+price+"Ԫ",
                    TitledBorder.CENTER,TitledBorder.TOP,new Font("΢���ź�", Font.BOLD, 15)));
            ImageIcon image= null;
            try {
                image = new ImageIcon(new URL(getPicAdress(picAdress)));
                image.setImage(image.getImage().getScaledInstance(200,200,Image.SCALE_DEFAULT));
                iconType.setIcon(image);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            staplefoodPanel.add(iconType);
            // ���ͼƬ��ǩ�ļ���
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
                    totalPriceLable.setText("�ϼ� : "+getTotalPrice()+"Ԫ"); // ���ºϼƼ۸�
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
        setCenterPanelHeight �����м����ĸ߶ȣ��ܹ�����ͼƬ���������û������ĸ߶�
            jscrollpaneWidth �м��ʽ������� ��ǰ�Ŀ��
            imgNumber �˵�ͼƬ����
            imageHeight ÿ��ͼƬ�ĸ߶�
    */
    public static int setCenterPanelHeight(int jscrollpaneWidth,int imgNumber,int imageHeight){
        int basicValue=640;
        int basicRowsNumber=2;
        int rowsNumber=basicRowsNumber;// ÿ�д�ŵ�ͼƬ����
        int addedValue=5;// ����ֵ
        if(jscrollpaneWidth>=basicValue){
            int testIceil= (int) Math.ceil((double)(jscrollpaneWidth-basicValue)/210); // ȡ������
            int testIfloor= (int) Math.floor((double)(jscrollpaneWidth-basicValue)/210); // ȡ����
            rowsNumber=testIceil+basicRowsNumber;
            if((jscrollpaneWidth-basicValue)-210*testIfloor<testIceil*addedValue){ // ���⴦��
                rowsNumber=rowsNumber-1;
            }
        }
        int columnNumber=imgNumber/rowsNumber;// ÿ�д�ŵ�ͼƬ����
        if(imgNumber%rowsNumber!=0){ // ��ͼƬ������rowsNumber�ı������Ƚ�����ΪrowsNumber�ı������ټ���columnNumber
            columnNumber=(int) Math.ceil((imgNumber+rowsNumber-imgNumber%rowsNumber)/rowsNumber);
        }
        int panelHeight=imageHeight*columnNumber; // ����Ҫ���õ����߶�
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
